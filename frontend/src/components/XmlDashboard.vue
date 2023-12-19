<template>
  <!-- Main container using Vuetify layout -->
  <v-container fluid>
    <v-row>
      <!-- Dialog for annotations -->
      <v-dialog max-width="50%" max-height="80vh" v-model="isVisible">
        <!-- AnnotatieDialog component -->
        <AnnotatieDialog
          :isVisible="isVisible"
          :selectedText="selectedText"
          :allWordsInXML="allWordsInXML"
          :hoveredWordObject="this.hoveredWordObject"
          @close="isVisible=false"
          @annotation-saved="applyAnnotation"
        ></AnnotatieDialog>
      </v-dialog>
      <!-- Left column for loading XML file -->
      <v-col col="6">
        <v-card>
          <v-card-title>Load XML File</v-card-title>
          <v-card-text>
            <!-- File input for selecting XML file -->
            <v-file-input
              label="Select XML File"
              accept=".xml"
              @change="handleFileChange"
            ></v-file-input>
            <!-- Button to trigger XML file loading -->
            <v-btn
              color="primary"
              @click="loadXML"
              :disabled="!xmlFile"
            >Load XML
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
      <!-- Right column for displaying XML content -->
      <v-col col="6">
        <v-card>
          <v-card-title>XML Content</v-card-title>
          <v-card-text v-if="parsedData.articles.length > 0">
            <v-scroll-area @mouseup="handleSelection()">
              <!-- Container for formatted XML content -->
              <div class="formatted-xml">
                <!-- Loop through articles in parsed data -->
                <div v-for="article in parsedData.articles" :key="article.number">
                  <h3>{{ article.title }}</h3>
                  <ol>
                    <!-- Loop through parts of each article -->
                    <li v-for="(part, partIndex) in article.parts" :key="partIndex">
                      <!-- Container for individual words in a part -->
                      <div>
                        <!-- Loop through words in the part -->
                        <span @mouseleave="hideTooltip"
                              v-for="(word, wordIndex) in part.partWords"
                              :key="wordIndex"
                              @mouseover="handleWordHover(word)"
                        >
                          {{ word.name }}
                          <span v-if="wordIndex < part.partWords.length - 1"> </span>
                          <!-- Tooltip for displaying word definition -->
                          <v-tooltip bottom v-if="showTooltip" activator="parent" location="top">
                            {{ matchedWord.definitie }}
                          </v-tooltip>
                        </span>
                        <ul>
                          <!-- Loop through subparts in each part -->
                          <li v-for="(subPart, subPartIndex) in part.subParts" :key="subPartIndex">
                            <span>{{ subPart.number }}</span>
                            <!-- Loop through words in the subpart -->
                            <span v-for="(word, wordIndex) in subPart.subPartWords" :key="wordIndex"
                                  @mouseleave="hideTooltip" @mouseover="handleWordHover(word)"
                            >
                              {{ word.name }}
                              <span v-if="wordIndex < subPart.subPartWords.length - 1"> </span>
                              <!-- Tooltip for displaying word definition -->
                               <v-tooltip bottom v-if="showTooltip" activator="parent" location="top">
                                 {{ matchedWord.definitie }}
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
            <!-- Display message if no XML file loaded -->
            <v-alert type="info">No XML file loaded.</v-alert>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>
/* Scoped styles for formatted XML display */
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
// Import necessary libraries and components
import vkbeautify from "vkbeautify";
import $ from "jquery";
import xml2js from "xml-js";
import AnnotatieDialog from "./Annotatie";
import { store } from "@/store/app";
import { isProxy, toRaw } from 'vue';

export default {
  components: { AnnotatieDialog },
  data() {
    return {
      // State variables
      isVisible: false,
      selectedText: "",
      xmlFile: null,
      xmlContent: null,
      parsedData: { articles: [] },
      showTooltip: false,
      hoveredWord: "",
      matchedWord: "",
      articleTitle: "",
      hoveredWordObject: "",
      allWordsInXML: "",
    };
  },
  computed: {
    // Computed property for formatting XML content
    formattedXml() {
      if (this.xmlContent) {
        return vkbeautify.xml(this.xmlContent);
      }
      return '';
    },
  },
  methods: {
    // Method for loading definitions from the server
    async loadDefinitions() {
      let response = await store().getXMLBron(this.articleTitle);

      let xmlBron = {
        artikel_naam: this.articleTitle,
        link: "http://example.com/xml",
        definities: []
      }

      if (response === 404) {
        await store().postNewXMLBron(xmlBron);
      }

      try {
        const definitions = await store().getDefinitions();
        console.log('Definitions:', definitions);

      } catch (definitionsError) {
        console.error('Error getting definitions:', definitionsError);
      }
    },

    // Method for handling text selection in the XML content
    handleSelection() {
      this.selectedText = window.getSelection().toString().trim();
      if (this.selectedText) {
        this.isVisible = true;
      }
    },

    // Method for handling word hover in the XML content
    handleWordHover(word) {
      this.hoveredWordObject = word;
      this.hoveredWord = this.removeDotsAndSymbols(word.name);
      this.matchedWord = this.findMatchingIndexes(word.number);
      if (this.matchedWord !== undefined) {
        this.showTooltip = true;
      }
    },

    // Method for finding matching indexes in the definitions
    findMatchingIndexes(number) {
      let definitions = this.convertProxyObjects(store().definitions);

      return definitions.find(definition =>
        (number >= definition.positie_start && number <= definition.positie_end)
      );
    },

    // Method for hiding the tooltip
    hideTooltip() {
      this.showTooltip = false;
    },

    // Method for converting proxy objects to raw objects
    convertProxyObjects(objects) {
      if (isProxy(objects)) {
        objects = toRaw(objects);
      }
      return objects;
    },

    // Method for applying an annotation to the parsed data
    applyAnnotation(annotation) {
      console.log("applyAnnotation", annotation);
      let { text, color } = annotation;
      const regex = new RegExp(this.RegExp(text), 'g');
      const replacement = `<span style="background-color: ${color};">${text}</span>`;

      this.parsedData.articles.forEach(article => {
        article.parts.forEach(part => {
          if (part.name) {
            console.log("replace part", part);
            part.name = part.name.replace(regex, replacement);
          }
          part.subParts.forEach(subPart => {
            if (subPart.name) {
              console.log("replace subpart", subPart);
              subPart.name = subPart.name.replace(regex, replacement);
            }
          });
        });
      });
    },

    // Method for escaping regular expression characters
    RegExp(string) {
      return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
    },

    // Method for handling file change event (selecting XML file)
    handleFileChange(event) {
      this.xmlFile = event.target.files[0];
    },

    // Method for loading XML file
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
    },

    // Method for parsing XML content
    parseXML(xmlString) {
      const xmlObject = xml2js.xml2js(xmlString, { compact: true });
      this.handleParsedData(xmlObject.artikel);
    },

    // Method for handling parsed data
    handleParsedData(articleNode) {
      const parsedData = { articles: [] };
      let wordIndex = -1; // Internal counter for word index
      let allWords = []; // Array to store all words

      if (articleNode) {
        const { articleNumber, articleTitle } = this.extractArticleInfo(articleNode);
        const parts = (articleNode.lid || []).map((lidNode) => {
          const { partNumber, partName, partNameWordsElements } = this.extractPartInfo(lidNode);

          // Add partNameWordsElements to the allWords array
          allWords = allWords.concat(partNameWordsElements);

          const subParts = (lidNode.lijst?.li || []).map((liNode, index) => {
            const { subPartNumber, subPartName, subPartWordElements } = this.extractSubPartInfo(liNode, index);

            // Add subPartWordElements to the allWords array
            allWords = allWords.concat(subPartWordElements);

            return { number: subPartNumber, name: subPartName, subPartWords: subPartWordElements };
          });

          return { number: partNumber, name: partName, partWords: partNameWordsElements, subParts };
        });

        parsedData.articles.push({ number: articleNumber, title: articleTitle, parts });
      }
      this.allWordsInXML = allWords;
      this.parsedData = parsedData;
      this.loadDefinitions();
    },

    // Method for extracting article information
    extractArticleInfo(articleNode) {
      return {
        articleNumber: articleNode.kop?.nr?._text?.trim(),
        articleTitle: articleNode.kop?._text?.trim(),
      };
    },

    // Method for extracting part information
    extractPartInfo(lidNode) {
      let wordIndex = -1;
      const partNumber = lidNode.lidnr?._text?.trim();
      const partName = lidNode.al?._text?.trim();
      const partNameWords = partName.split(/\s+/);
      const partNameWordsElements = partNameWords.map((word) => ({
        number: ++wordIndex,
        name: word.trim(),
      }));

      return { partNumber, partName, partNameWordsElements };
    },

    // Method for extracting subpart information
    extractSubPartInfo(liNode, index) {
      let wordIndex = -1;
      const subPartNumber = String.fromCharCode(97 + index) + '.';
      const subPartName = liNode.al?._text?.trim();
      const subPartWords = subPartName.split(/\s+/);
      const subPartWordElements = subPartWords.map((word) => ({
        number: ++wordIndex,
        name: word.trim(),
      }));

      return { subPartNumber, subPartName, subPartWordElements };
    },

    // Method for removing dots and symbols from a word
    removeDotsAndSymbols(word) {
      // Remove special symbols
      return word.replace(/[.,]/gi, '');
    }
  },
};
</script>






