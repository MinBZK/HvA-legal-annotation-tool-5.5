<template>
  <v-container fluid>
    <v-row>
      <v-dialog max-width="50%" max-height="80vh" v-model="isVisible" >
        <AnnotatieDialog :isVisible="isVisible" :selectedText="selectedText"
                         @close="isVisible=false">
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
            >Load XML</v-btn>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col col="6">
        <v-card>
          <v-card-title>XML Content</v-card-title>
          <v-card-text v-if="parsedData.articles.length > 0">
            <v-scroll-area>
              <div class="formatted-xml">
                <div v-for="article in parsedData.articles" :key="article.number">
                  <h3>{{ article.title }}</h3>
                  <ol>
                    <li v-for="(part, partIndex) in article.parts" :key="partIndex">
                      <div>
                        <!--                        <span>{{ part.number }}. {{ part.name }}</span>-->
                        <span> {{ part.name }}</span>

                        <ul>
                          <li v-for="(subPart, subPartIndex) in part.subParts" :key="subPartIndex">
                            <span>{{ subPart.number }} {{ subPart.name }}</span>
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

export default {
  components: {AnnotatieDialog},
  data() {
    return {
      isVisible: false,
      xmlFile: null,
      xmlContent: null,
      parsedData: {articles: []},
      selectedText: "",
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
    handleSelection() {
      this.selectedText = window.getSelection().toString().trim();
      if (this.selectedText) {
        this.isVisible = true;
      }
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
    },
    parseXML(xmlString) {
      const xmlObject = xml2js.xml2js(xmlString, { compact: true });
      this.handleParsedData(xmlObject.artikel);
    handleParsedData(articleNode) {
    },
      const parsedData = { articles: [] };

      if (articleNode) {
        const articleNumber = articleNode.kop?.nr?._text?.trim();
        const articleTitle = articleNode.kop?._text?.trim();

        const parts = (articleNode.lid || []).map((lidNode) => {
          const partNumber = lidNode.lidnr?._text?.trim();
          const partName = lidNode.al?._text?.trim();

          const subParts = (lidNode.lijst?.li || []).map((liNode, index) => {
            const subPartNumberNode = $(liNode).find('li.nr').first();
            const subPartNumber = String.fromCharCode(97 + index) + '.'; // Convert index to letter (a., b., c., ...)

            const subPartName = liNode.al?._text?.trim();

            return { number: subPartNumber, name: subPartName };
          });

          return { number: partNumber, name: partName, subParts };
        });

        parsedData.articles.push({ number: articleNumber, title: articleTitle, parts });
      }

      this.parsedData = parsedData;
    },
  },
};
</script>


