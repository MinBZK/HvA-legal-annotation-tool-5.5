<template>
  <v-container fluid>
    <v-row>
      <v-dialog max-width="50%" max-height="80vh" v-model="isVisible">
        <AnnotatieDialog :isVisible="isVisible" :selectedText="selectedText"
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
            <v-scroll-area @mouseup="handleSelection">
              <div class="formatted-xml">
                <div v-for="article in parsedData.articles" :key="article.number">
                  <h3>{{ article.title }}</h3>
                  <ol>
                    <li v-for="(part, partIndex) in article.parts" :key="partIndex">
                      <div>
                        <span @mouseleave="hideTooltip"
                              v-for="(word, wordIndex) in part.partWords"
                              :key="wordIndex"
                              @mouseover="handleWordHover(word.name)"
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
                                  @mouseleave="hideTooltip" @mouseover="handleWordHover(word.name)"
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
      console.log(import.meta.env)
      store().getDefinitions();
    },

    handleSelection() {
      this.selectedText = window.getSelection().toString().trim();
      if (this.selectedText) {
        const wordNumber = this.findWordNumber(this.selectedText);
        console.log(`Selected Text: ${this.selectedText}, Word Number: ${wordNumber}`);
        this.isVisible = true;
      }


      if (this.selectedText) {
        this.isVisible = true;
      }
    },

    findWordNumber(selectedText) {
      let foundNumber = null;

      this.parsedData.articles.forEach((article) => {
        article.parts.forEach((part) => {
          part.partWords.forEach((word) => {
            if (word.name === selectedText) {
              foundNumber = word.number;
            }
          });

          part.subParts.forEach((subPart) => {
            subPart.subPartWords.forEach((word) => {
              if (word.name === selectedText) {
                foundNumber = word.number;
              }
            });
          });
        });
      });

      return foundNumber;
    },

    handleWordHover(word) {
      this.hoveredWord = this.removeDotsAndSymbols(word);
      this.matchedWord = this.findMatchingDefinition(this.hoveredWord);

      if (this.matchedWord !== undefined) {
        this.showTooltip = true;
      }
    },

    findMatchingDefinition(hoveredText) {
      return store().definitions.find(
        definition => definition.text === hoveredText);
    },

    hideTooltip() {
      this.showTooltip = false;
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

      let wordIndex = 0; // Internal counter for word index

      if (articleNode) {
        const articleNumber = articleNode.kop?.nr?._text?.trim();
        const articleTitle = articleNode.kop?._text?.trim();
        this.articleTitle = articleTitle;

        const parts = (articleNode.lid || []).map((lidNode) => {
          const partNumber = lidNode.lidnr?._text?.trim();
          const partName = lidNode.al?._text?.trim();

          const partNameWords = partName.split(/\s+/); // Split by whitespace to get individual words
          const partNameWordsElements = partNameWords.map((word) => ({
            number: ++wordIndex, // Increment wordIndex for each word
            name: word.trim(),
          }));

          const subParts = (lidNode.lijst?.li || []).map((liNode, index) => {
            $(liNode).find('li.nr').first();
            const subPartNumber = String.fromCharCode(97 + index) + '.'; // Convert index to letter (a., b., c., ...)

            const subPartName = liNode.al?._text?.trim();
            const subPartWords = subPartName.split(/\s+/); // Split by whitespace to get individual words
            const subPartWordElements = subPartWords.map((word) => ({
              number: ++wordIndex, // Increment wordIndex for each word
              name: word.trim(),
            }));

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
      console.log(parsedData)
      this.parsedData = parsedData;
    }
    ,


    removeDotsAndSymbols(word) {
      // Remove special symbols
      return word.replace(/[.,]/gi, '');
    }
  },

};
</script>




