<template>
  <v-container fluid>
    <v-row>
      <v-dialog max-width="50%" max-height="80vh" v-model="isVisible">
        <AnnotatieDialog :isVisible="isVisible" :selectedText="selectedText" :allWordsInXML="allWordsInXML"
                         :hoveredWordObject="this.hoveredWordObject"
                         @close="isVisible=false"
                         @annotation-saved="applyAnnotation">
        </AnnotatieDialog>
      </v-dialog>
      <v-col col="6">
        <v-card>
          <v-card-title>Load XML File</v-card-title>
          <v-card-text>
            <v-file-input
              label="Select XML File"
              accept=".xml"
              @change="handleFileChange"
            ></v-file-input>
            <v-btn
              color="primary"
              @click="loadXML"
              :disabled="!xmlFile"
            >Load XML
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col col="6">
        <v-card>
          <v-card-title>XML Content</v-card-title>
          <v-card-text v-if="parsedData.articles.length > 0">
            <v-scroll-area @mouseup="handleSelection()">
              <div class="formatted-xml">
                <div v-for="article in parsedData.articles" :key="article.number">
                  <h3>{{ article.title }}</h3>
                  <ol>
                    <li v-for="(part, partIndex) in article.parts" :key="partIndex">
                      <div>
                        <span @mouseleave="hideTooltip"
                              v-for="(word, wordIndex) in part.partWords"
                              :key="wordIndex"
                              @mouseover="handleWordHover(word)"
                        >
                          {{ word.name }}
                          <span v-if="wordIndex < part.partWords.length - 1"> </span>

                           <v-tooltip bottom v-if="showTooltip"
                                      activator="parent"
                                      location="top"
                           >
                                     {{ matchedWord.definition }}
                        </v-tooltip>

                        </span>
                        <ul>
                          <li v-for="(subPart, subPartIndex) in part.subParts" :key="subPartIndex">
                            <span>{{ subPart.number }}</span>
                            <span v-for="(word, wordIndex) in subPart.subPartWords" :key="wordIndex"
                                  @mouseleave="hideTooltip" @mouseover="handleWordHover(word)"
                            >
                              {{ word.name }}

                              <span v-if="wordIndex < subPart.subPartWords.length - 1"> </span>

                               <v-tooltip bottom v-if="showTooltip"
                                          activator="parent"
                                          location="top"
                               >
                                     {{ matchedWord.definition }}
                        </v-tooltip>

                            </span>
                          </li>
                        </ul>
                      </div>
                    </li>
                  </ol>
                </div>
              </div>
            </v-scroll-area>
          </v-card-text>
          <v-card-text v-else>
            <v-alert type="info">No XML file loaded.</v-alert>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>


<style scoped>
.formatted-xml {
  margin: 0; /* Remove default margin */
  line-height: 1.5; /* Adjust line height for better readability */
}

.formatted-xml h3 {
  font-size: 20px;
  margin-bottom: 5px;
}

.formatted-xml ol {
  font-size: 15px;
  list-style-type: decimal;
  margin-bottom: 10px;
}

.formatted-xml li {
  margin-bottom: 5px;
}

.formatted-xml ul li {
  /* Remove the asterisk for subparts */
  list-style-type: none;
}
</style>


<script>
import vkbeautify from "vkbeautify";
import $ from "jquery";
import xml2js from "xml-js";
import AnnotatieDialog from "@/components/Annotatie";
import {store} from "@/store/app";
import {isProxy, toRaw} from 'vue';

export default {
  components: {AnnotatieDialog},
  data() {
    return {
      isVisible: false,
      selectedText: "",
      xmlFile: null,
      xmlContent: null,
      parsedData: {articles: []},
      showTooltip: false,
      hoveredWord: "",
      matchedWord: "",
      articleTitle: "",
      hoveredWordObject: "",
      allWordsInXML: "",
    };
  },
  computed: {
    formattedXml() {
      if (this.xmlContent) {
        return vkbeautify.xml(this.xmlContent);
      }
      return '';
    },
  },
  methods: {
    loadDefinitions() {
      store().getDefinitions();
    },

    handleSelection() {
      this.selectedText = window.getSelection().toString().trim();
      if (this.selectedText) {
        this.isVisible = true;
      }
    },

    handleWordHover(word) {
      this.hoveredWordObject = word;
      this.hoveredWord = this.removeDotsAndSymbols(word.name);
      this.matchedWord = this.findMatchingDefinition(this.hoveredWord);

      if (this.matchedWord !== undefined) {
        this.showTooltip = true;
      }
    },

    findMatchingDefinition(hoveredText) {
      let definitions = this.convertProxyObjects(store().definitions);

      return definitions.find(
        definition => definition.woord === hoveredText
      );
    },

    hideTooltip() {
      this.showTooltip = false;
    },

    convertProxyObjects(objects) {
      if (isProxy(objects)) {
        objects = toRaw(objects)
      }
      return objects
    },

    applyAnnotation(annotation) {
      let {text, color} = annotation;
      const regex = new RegExp(this.RegExp(text), 'g');
      const replacement = `<span style="background-color: ${color};">${text}</span>`;

      this.parsedData.articles.forEach(article => {
        article.parts.forEach(part => {
          if (part.name) {
            part.name = part.name.replace(regex, replacement);
          }
          part.subParts.forEach(subPart => {
            if (subPart.name) {
              subPart.name = subPart.name.replace(regex, replacement);
            }
          });
        });
      });
    },

    RegExp(string) {
      return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
    },

    handleFileChange(event) {
      this.xmlFile = event.target.files[0];
    },

    loadXML() {
      if (!this.xmlFile) {
        return;
      }

      const reader = new FileReader();
      reader.onload = (event) => {
        this.xmlContent = event.target.result;
        this.parseXML(this.xmlContent);
      };
      reader.readAsText(this.xmlFile);
      this.loadDefinitions();
    },

    parseXML(xmlString) {
      const xmlObject = xml2js.xml2js(xmlString, {compact: true});
      this.handleParsedData(xmlObject.artikel);
    },

    handleParsedData(articleNode) {
      const parsedData = {articles: []};
      let wordIndex = -1; // Internal counter for word index
      let allWords = []; // Array to store all words

      if (articleNode) {
        const articleNumber = articleNode.kop?.nr?._text?.trim();
        const articleTitle = articleNode.kop?._text?.trim();
        this.articleTitle = articleTitle;
        store().loadedXMLIdentifier = articleTitle;

        const parts = (articleNode.lid || []).map((lidNode) => {
          const partNumber = lidNode.lidnr?._text?.trim();
          const partName = lidNode.al?._text?.trim();

          const partNameWords = partName.split(/\s+/); // Split by whitespace to get individual words
          const partNameWordsElements = partNameWords.map((word) => ({
            number: ++wordIndex, // Increment wordIndex for each word
            name: word.trim(),
          }));

          // Add partNameWordsElements to the allWords array
          allWords = allWords.concat(partNameWordsElements);

          const subParts = (lidNode.lijst?.li || []).map((liNode, index) => {
            $(liNode).find('li.nr').first();
            const subPartNumber = String.fromCharCode(97 + index) + '.'; // Convert index to letter (a., b., c., ...)

            const subPartName = liNode.al?._text?.trim();
            const subPartWords = subPartName.split(/\s+/); // Split by whitespace to get individual words
            const subPartWordElements = subPartWords.map((word) => ({
              number: ++wordIndex, // Increment wordIndex for each word
              name: word.trim(),
            }));

            // Add subPartWordElements to the allWords array
            allWords = allWords.concat(subPartWordElements);

            return {
              number: subPartNumber,
              name: subPartName,
              subPartWords: subPartWordElements,
            };
          });

          return {number: partNumber, name: partName, partWords: partNameWordsElements, subParts};
        });

        parsedData.articles.push({number: articleNumber, title: articleTitle, parts});
      }
      this.allWordsInXML = allWords;
      this.parsedData = parsedData;
    },

    removeDotsAndSymbols(word) {
      // Remove special symbols
      return word.replace(/[.,]/gi, '');
    }
  },

};
</script>




