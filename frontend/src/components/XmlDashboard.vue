<!--<template>-->
<!--  <v-container fluid>-->
<!--    <v-row>-->
<!--      <v-col col="6">-->
<!--        <v-card>-->
<!--          <v-card-title>Load XML File</v-card-title>-->
<!--          <v-card-text>-->
<!--            <v-file-input-->
<!--              label="Select XML File"-->
<!--              accept=".xml"-->
<!--              @change="handleFileChange"-->
<!--            ></v-file-input>-->
<!--            <v-btn-->
<!--              color="primary"-->
<!--              @click="loadXML"-->
<!--              :disabled="!xmlFile"-->
<!--            >Load XML</v-btn>-->
<!--          </v-card-text>-->
<!--        </v-card>-->
<!--      </v-col>-->
<!--      <v-col col="6">-->
<!--        <v-card>-->
<!--          <v-card-title>XML Content</v-card-title>-->
<!--          <v-card-text v-if="formattedXml">-->
<!--            <v-scroll-area>-->
<!--              <code class="formatted-xml" v-html="formattedXml"></code>-->
<!--            </v-scroll-area>-->
<!--          </v-card-text>-->
<!--          <v-card-text v-else>-->
<!--            <v-alert-->
<!--              type="info"-->
<!--            >No XML file loaded.</v-alert>-->
<!--          </v-card-text>-->
<!--        </v-card>-->
<!--      </v-col>-->
<!--    </v-row>-->
<!--  </v-container>-->
<!--</template>-->

<!--<style scoped>-->
<!--.formatted-xml {-->
<!--  font-family: monospace;-->
<!--  font-size: 14px;-->
<!--  white-space: pre-wrap; /* Preserve line breaks and wrap long lines */-->
<!--  margin: 0; /* Remove default margin */-->
<!--  line-height: 1.5; /* Adjust line height for better readability */-->
<!--  padding: 10px;-->
<!--  border: 1px solid #ccc;-->
<!--  border-radius: 5px;-->
<!--  display: block;-->
<!--  text-align: left; /* Ensure text starts at the left side */-->
<!--}-->
<!--</style>-->

<!--<script>-->
<!--import vkbeautify from "vkbeautify";-->

<!--export default {-->
<!--  data() {-->
<!--    return {-->
<!--      xmlFile: null,-->
<!--      xmlContent: null,-->
<!--    };-->
<!--  },-->
<!--  computed: {-->
<!--    formattedXml() {-->
<!--      if (this.xmlContent) {-->
<!--        return vkbeautify.xml(this.xmlContent);-->
<!--      }-->
<!--      return '';-->
<!--    },-->
<!--  },-->
<!--  methods: {-->
<!--    handleFileChange(event) {-->
<!--      this.xmlFile = event.target.files[0];-->
<!--    },-->
<!--    loadXML() {-->
<!--      if (!this.xmlFile) {-->
<!--        return;-->
<!--      }-->

<!--      const reader = new FileReader();-->
<!--      reader.onload = (event) => {-->
<!--        this.xmlContent = event.target.result;-->
<!--      };-->
<!--      reader.readAsText(this.xmlFile);-->
<!--    },-->
<!--  },-->
<!--};-->
<!--</script>-->

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
            >Load XML
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col>
      <v-col col="6">
        <v-card>
          <v-card-title>XML Content</v-card-title>
          <v-card-text v-if="parsedData.articles.length > 0" @mouseup="handleSelection">
            <v-scroll-area>
              <div class="formatted-xml">
                <div v-for="article in parsedData.articles" :key="article.number">
                  <h3>{{ article.title }}</h3>
                  <ol>
                    <li v-for="(paragraph, index) in article.paragraphs" :key="index">{{ paragraph }}</li>
                  </ol>
                </div>
              </div>
            </v-scroll-area>
          </v-card-text>
          <v-card-text v-else>
            <v-alert
              type="info"
            >No XML file loaded.
            </v-alert>
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
  font-size: 18px;
  margin-bottom: 5px;
}

.formatted-xml ol {
  font-size: 14px;
  list-style-type: lower-alpha;
  margin-bottom: 10px;
}

.formatted-xml li {
  margin-bottom: 5px;
}
</style>

<script>
import vkbeautify from "vkbeautify";
import AnnotatieDialog from "@/components/Annotatie";
import {store} from "@/store/app";


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
        this.parsedData = this.parseXML(this.xmlContent);
      };
      reader.readAsText(this.xmlFile);
    },
    parseXML(xmlString) {
      const parser = new DOMParser();
      const xmlDoc = parser.parseFromString(xmlString, 'text/xml');
      const articles = xmlDoc.querySelectorAll('artikel');
      const parsedData = {articles: []};

      articles.forEach((article) => {
        const articleNumberNode = article.querySelector('kop');
        if (articleNumberNode) {
          const articleNumber = articleNumberNode.textContent.trim();
          const articleTitleNode = articleNumberNode.nextElementSibling;
          const articleTitle = articleTitleNode.textContent.trim();
          const paragraphs = [];
          const paragraphNodes = article.querySelectorAll('al');
          paragraphNodes.forEach((paragraphNode) => {
            const paragraphText = paragraphNode.textContent.trim();
            paragraphs.push(paragraphText);
          });

          parsedData.articles.push({number: articleNumber, title: articleTitle, paragraphs});
        }
      });

      return parsedData;
    },
  },
};
</script>


