<template>

  <div class="container">
    <div class="row">
      <div class="col">
        <h2>XML Loader</h2>
<!--        <input type="file" @change="handleFileChange" />-->
        <v-file-input label="File input" @change="handleFileChange"></v-file-input>
      </div>
      <div class="col">
        <div v-if="xmlData">
          <p v-html="formattedXmlData"></p>
        </div>
      </div>

    </div>
  </div>
  <div>
<!--    <h2>XML Loader</h2>-->
<!--    <input type="file" @change="handleFileChange" />-->
<!--    <div v-if="xmlData">-->
<!--      &lt;!&ndash;      <pre v-html="formattedXmlData"></pre>&ndash;&gt;-->
<!--      <p v-html="formattedXmlData"></p>-->
<!--    </div>-->
  </div>
</template>

<script>
import Prism from 'prismjs';
import beautify from 'js-beautify';

export default {
  data() {
    return {
      xmlData: null,
      formattedXmlData: null,
    };
  },
  mounted() {
    // Initialize Prism
    Prism.highlightAll();
  },
  methods: {
    async handleFileChange(event) {
      const file = event.target.files[0];
      if (!file) return;

      const reader = new FileReader();
      reader.onload = async (event) => {
        const parsedXml = new DOMParser().parseFromString(event.target.result, "text/xml");

        // Use the beautify function directly
        this.xmlData = await beautify.html_beautify(parsedXml.documentElement.outerHTML, {
          indent_size: 2,
          indent_char: " ",
          preserve_newlines: true,
        });

        // Optionally, set the formattedXmlData for displaying in the <pre> tag
        this.formattedXmlData = this.xmlData;
      };
      reader.readAsText(file);
    },
  },
};
</script>

<style>
/* This is a valid CSS comment */
pre {
  background-color: #f8f8f8;
  padding: 10px;
  border: 1px solid #ccc;
  font-family: monospace;
  font-size: 14px;
}
</style>
