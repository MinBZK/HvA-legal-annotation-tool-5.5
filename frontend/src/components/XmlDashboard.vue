<template>
  <v-app>
    <!-- Header & Sidebar content -->
    <app-header-sidebar></app-header-sidebar>

    <!-- Anotatie Dialog content -->
    <v-dialog max-width="50%" max-height="80vh" v-model="isVisible">
      <AnnotatieDialog :isVisible="isVisible" :selectedText="selectedText" :allWordsInXML="allWordsInXML"
                       :hoveredWordObject="this.hoveredWordObject"
                       @close="isVisible=false"
                       @annotation-saved="applyAnnotation">
      </AnnotatieDialog>
    </v-dialog>

    <!-- Main content -->
    <v-main>
      <v-container fluid>
        <!-- Widgets -->
        <v-row>
          <!-- Import/export widget -->
          <v-col cols="12" md="4">
            <v-card class="elevation-3">
              <v-card-title class="headline">XML-bestanden Beheren</v-card-title>
              <!-- Import/export section -->
              <v-card-text>
                <v-card>
                  <v-card-title>XML Laden</v-card-title>
                  <v-card-text>
                    <v-file-input
                      label="Selecteer XML-bestand"
                      accept=".xml"
                      @change="handleFileChange"
                    ></v-file-input>
                    <v-btn
                      color="primary"
                      variant="elevated"
                      @click="loadXML"
                      :disabled="!xmlFile"
                    >XML Laden
                    </v-btn>

                    <!-- Error Alert -->
                    <v-alert
                      v-if="errorMessage"
                      type="error"
                      dense
                    >
                      {{ errorMessage }}
                    </v-alert>

                  </v-card-text>
                </v-card>
                <!-- Xml Export content -->
                <XmlDownloader v-if="xmlFile"
                               :xmlContent="xmlContent"
                               :defaultFileName="xmlFile.name"
                               :xml-file="xmlFile"
                ></XmlDownloader>
              </v-card-text>
            </v-card>
          </v-col>
          <v-col col="6">
            <XMLbronTimeLine :timelineData="timelineDataLive"></XMLbronTimeLine>
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
                              :style="{ backgroundColor: wordColours[word.number] }"
                              @mouseover="handleWordHover(word)"
                              :id="'word-' + id"
                        >
                          {{ word.name }}
                          <span v-if="wordIndex < part.partWords.length - 1"> </span>
                           <v-tooltip bottom v-if="showTooltip"
                                      activator="parent"
                                      location="top"
                           >
                                     {{ matchedWord.definitie }}
                        </v-tooltip>
                        </span>
                            <ul>
                              <li v-for="(subPart, subPartIndex) in part.subParts" :key="subPartIndex"
                              >
                                <span>{{ subPart.number }}</span>
                                <span v-for="(word, wordIndex) in subPart.subPartWords" :key="wordIndex"
                                      :style="{ backgroundColor: wordColours[word.number] }"
                                      @mouseleave="hideTooltip" @mouseover="handleWordHover(word)"
                                      :id="'word-' + id"
                                >
                              {{ word.name }}
                              <span v-if="wordIndex < subPart.subPartWords.length - 1"> </span>
                               <v-tooltip bottom v-if="showTooltip"
                                          activator="parent"
                                          location="top"
                               >
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
                <v-alert type="info">Geen XML bestand geladen.</v-alert>
              </v-card-text>

              <v-btn v-if="showButton" @click="openCard" color="primary">Samenvatting</v-btn>

              <v-card v-if="showCard" id="print">
                <v-card-title>
                  Samenvatting van annotaties:
                </v-card-title>
                <v-card-text>
                  <!-- Display mergedData in the card -->
                  <div v-for="item in mergedData" :key="item.word">
                    <p>
                      <strong>Word:</strong> {{ item.word }},
                      <strong>Definition:</strong> {{ item.definition }},
                      <strong>Label:</strong> {{ item.label }}
                    </p>
                    <br>
                  </div>
                </v-card-text>
                <v-card-actions>
                  <!-- Print button -->
<!--                  <v-btn @click="printCardContent" color="primary">Print</v-btn>-->
                </v-card-actions>
              </v-card>
              <v-btn v-if="showCard" @click="printCardContent" color="primary">Print samenvatting</v-btn>

            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<style scoped>

/* Add custom margin classes */
.mb-3 {
  margin-bottom: 1rem; /* You can adjust this value as needed */
}

.mt-3 {
  margin-top: 1rem; /* You can adjust this value as needed */
}

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
import Annotatie from "@/components/Annotatie.vue";
import XMLbronTimeLine from "@/components/XMLbronTimeLine.vue";
import {store} from "@/store/app";
import AppHeaderSidebar from "@/components/AppHeaderSidebar.vue";
import XmlDownloader from "@/components/XmlDownloader.vue";

export default {
  components: {AnnotatieDialog, Annotatie, XMLbronTimeLine, AppHeaderSidebar, XmlDownloader},
  data() {
    return {
      timelineData: [
        {id: 1, name: "Mock data title 1", date: '2023-01-01'},
        {id: 2, name: "Mock data title 2", date: '2023-01-02'},
        {id: 3, name: "Mock data title 3", date: '2023-01-02'},
        {id: 4, name: "Mock data title 3", date: '2023-01-02'},
      ],
      showButton: false,
      showPrintButton: false,
      showCard: false,
      filteredData: {},
      mergedData: [],
      timelineDataLive: [],
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
      idCounter: 0,

      wordIdMap: {}, // Map to store generated IDs for words
      id: null,
      numberOfWords: 0,
      wordColours: [],
      labels: {},
      errorMessage: '',
      colourOptions: [
        {label: 'Rechtssubject', colour: 'rgb(194, 231, 255)'},
        {label: 'Rechtsbetrekking', colour: 'rgb(112, 164, 255)'},
        {label: 'Rechtsobject', colour: 'rgb(152, 190, 241)'},
        {label: 'Rechtsfeit', colour: 'rgb(151, 214, 254)'},
        {label: 'Voorwaarde', colour: 'rgb(145, 232, 211)'},
        {label: 'Afleidingsregel', colour: 'rgb(255, 122, 122)'},
        {label: 'Variabele', colour: 'rgb(255, 217, 93)'},
        {label: 'Variabelewaarde', colour: 'rgb(255, 243, 128)'},
        {label: 'Parameter', colour: 'rgb(255, 180, 180)'},
        {label: 'Parameterwaarde', colour: 'rgb(255, 216, 239)'},
        {label: 'Operator', colour: 'rgb(193, 235, 225)'},
        {label: 'Tijdsaanduiding', colour: 'rgb(216, 176, 249)'},
        {label: 'Plaatsaanduiding', colour: 'rgb(239, 202, 246)'},
        {label: 'Delegatiebevoegdheid', colour: 'rgb(206, 206, 206)'},
        {label: 'Delegatieinvulling', colour: 'rgb(226, 226, 226)'},
        {label: 'Brondefinitie', colour: 'rgb(246, 246, 246)'},
      ],
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
    async loadDefinitions() {
      try {
        let xmlBronId = store().loadedXMLIdentifier;
        let username = JSON.parse(localStorage.getItem('username'));
        let xmlbronDate = store().loadedXMLDate;

        await store().getDefinitions(xmlBronId, username, xmlbronDate);

      } catch (definitionsError) {
        console.error('Error getting definitions:', definitionsError);
      }
    },

    async loadDefinitionsForUser(username) {
      try {
        let xmlBronId = store().loadedXMLIdentifier;
        let xmlbronDate = store().loadedXMLDate;

        await store().getDefinitions(xmlBronId, username, xmlbronDate);

      } catch (definitionsError) {
        console.error('Error getting definitions:', definitionsError);
      }
    },

    insertLabelColours(labels) {
      labels.forEach(label => {
        let startPosition = label.positie_start;
        let textLength = (label.positie_end - label.positie_start) + 1;
        let matchinglabel = this.colourOptions.find(option => option.label === label.label);

        for (let i = 0; i < textLength; i++) {
          this.wordColours[startPosition + i] = matchinglabel.colour;
        }
      })
    },

    async loadLabelsForArticle() {
      try {
        let xmlBronId = store().loadedXMLIdentifier;
        let username = JSON.parse(localStorage.getItem('username'));
        let xmlbronDate = store().loadedXMLDate;

        await store().getLabels(xmlBronId, username, xmlbronDate);
        this.insertLabelColours(store().labels)
        this.$forceUpdate(); // Force the component to re-render
      } catch (labelsError) {
        console.error('Error getting labels:', labelsError);
      }
    },

    async loadLabelsForArticleForUser(username) {
      try {
        let xmlBronId = store().loadedXMLIdentifier;
        let xmlbronDate = store().loadedXMLDate;

        await store().getLabels(xmlBronId, username, xmlbronDate);

      } catch (labelsError) {
        console.error('Error getting labels:', labelsError);
      }
      this.insertLabelColours(store().labels)
    },

    handleSelection() {
      this.selectedText = window.getSelection().toString().trim();
      if (this.selectedText) {
        this.isVisible = true;
      }
    },

    async handleWordHover(word) {
      this.hoveredWordObject = word;
      this.hoveredWord = this.removeDotsAndSymbols(word.name);
      this.matchedWord = await this.findMatchingIndexes(word.number);

      if (this.matchedWord !== undefined) {
        this.showTooltip = true;
      }
    },

    async findMatchingIndexes(number) {
      let definitions = await store().definitions;

      if (definitions === undefined || definitions.length === 0) {
        return
      }

      definitions = definitions.filter(
        definition =>
          (number >= definition.positie_start && number <= definition.positie_end)
      );

      return definitions[definitions.length - 1];
    },

    hideTooltip() {
      this.showTooltip = false;
    },

    applyAnnotation() {
      this.loadXML();
      this.loadAssociatedData();
    },

    escapeSpecialCharacters(string) {
      return string.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
    },

    handleFileChange(event) {
      this.xmlFile = event.target.files[0];
      this.getUserFromXML(this.xmlFile);
    },

    loadXML() {
      if (!this.xmlFile) {
        return;
      }

      const reader = new FileReader();
      reader.onload = (event) => {
        this.xmlContent = event.target.result;
        const username = this.getUserFromXML(this.xmlContent); // Extract the username

        this.parseXML(this.xmlContent);
      };
      reader.readAsText(this.xmlFile);
      this.showButton = true;
    },

    parseXML(xmlString) {
      const xmlObject = xml2js.xml2js(xmlString, {compact: true});
      this.extractMetaDataXML(xmlObject);
      this.handleParsedData(xmlObject.artikel);
    },

    extractMetaDataXML(xmlObject) {
      const datePattern = /\b\d{4}-\d{2}-\d{2}\b/;
      this.errorMessage = "";

      try {
        let {id} = xmlObject.artikel._attributes;
        let dateMatch = id.match(datePattern);

        if (dateMatch) {
          dateMatch = dateMatch[0];
          store().XMLBwbrCode = id;
          store().loadedXMLDate = dateMatch;
        } else {
          console.error("Date not found in the provided XML id:", id);
        }
      } catch (e) {
        this.errorMessage = "Fout met file inladen, format niet compatibel."
      }
    },

    // TODO Method should be split up in separate smaller methods
    async handleParsedData(articleNode) {
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

      if (allWords.length !== 0) {
        await this.checkIfXMLBronExists();
      }

      await store().getXMLBronnenByNameTimeLine(this.articleTitle)
      this.timelineDataLive = store().xmlbronnen;
    },

    async checkIfXMLBronExists() {
      let xmlbronDate = store().loadedXMLDate;

      let response = await store().getXMLBron(this.articleTitle, xmlbronDate);
      let baseUrl = "https://wetten.overheid.nl";
      let urlBWBR = store().XMLBwbrCode

      if (response === 404) {
        let xmlBron = {
          artikelNaam: this.articleTitle,
          link: `${baseUrl}/${urlBWBR}`,
          definities: [],
          xmlbron_date: xmlbronDate,
        }

        await store().postNewXMLBron(xmlBron);
      } else {
        this.loadAssociatedData();
      }
    },

    loadAssociatedData() {
      this.loadDefinitions();
      this.loadLabelsForArticle();
      this.getUserFromXML(this.xmlContent)
    },

    removeDotsAndSymbols(word) {
      // Remove special symbols
      return word.replace(/[.,]/gi, '');
    },

    getUserFromXML(xmlContent) {
      const parser = new DOMParser();
      const xmlDoc = parser.parseFromString(xmlContent, 'text/xml');

      // Get the root element of the XML document
      const rootElement = xmlDoc.documentElement;

      // Get the username attribute from the root element
      const username = rootElement.getAttribute('username');

      if (username) {
        this.loadDefinitionsForUser(username);
        this.loadLabelsForArticleForUser(username);
      }

      // Return the username value or null if the attribute doesn't exist
      return username ? username : null;
    },

    getCurrentAnnotations() {
      let allDefinitions = store().definitions;
      let allLabels = store().labels;

      const resultDefinitions = allDefinitions.reduce((acc, obj) => {
        if (!acc[obj.woord] || new Date(obj.date) > new Date(acc[obj.woord].date)) {
          acc[obj.woord] = obj;
        }
        return acc;
      }, {});

      const resultLabels = allLabels.reduce((acc, obj) => {
        if (!acc[obj.woord] || new Date(obj.datum) > new Date(acc[obj.woord].datum)) {
          acc[obj.woord] = obj;
        }
        return acc;
      }, {});

      const filteredDataDefinitions = Object.values(resultDefinitions);
      const filteredDataLabels = Object.values(resultLabels);

      // Merge the two arrays and create a new array with word, definition, and label
      const mergedData = filteredDataDefinitions.map(definition => {
        const matchingLabel = filteredDataLabels.find(label => label.woord === definition.woord);
        return {
          word: definition.woord,
          definition: definition.definitie,
          label: matchingLabel ? matchingLabel.label : 'leeg',
        };
      });

      console.log(mergedData);

      return mergedData;
    },


    openCard() {
      // Set filteredData based on your logic
      this.mergedData = this.getCurrentAnnotations();

      console.log("mergedData", this.filteredData);
      this.showCard = true;
    },
    printCardContent() {


      setTimeout(() => {
        // Your code after the delay
        console.log('Waited for 1 second!');
      }, 1000);

      // Get HTML to print from element
      const prtHtml = document.getElementById('print').innerHTML;
      console.log(prtHtml);

      // Get all stylesheets HTML
      let stylesHtml = '';
      for (const node of [...document.querySelectorAll('link[rel="stylesheet"], style')]) {
        stylesHtml += node.outerHTML;
      }

      // Open the print window
      const WinPrint = window.open('', '', 'left=0,top=0,width=800,height=900,toolbar=0,scrollbars=0,status=0');

      WinPrint.document.write(`<!DOCTYPE html>
<html>
  <head>
    ${stylesHtml}
  </head>
  <body>
    ${prtHtml}
  </body>
</html>`);

      WinPrint.document.close();
      WinPrint.focus();
      WinPrint.print();
      WinPrint.close();


    },
  }
};
</script>
