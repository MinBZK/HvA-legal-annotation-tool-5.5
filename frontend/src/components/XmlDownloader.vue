<template>
  <div>
    <!-- Card for XML Download -->
    <v-card class="mt-3">
      <v-card-title>XML Downloaden</v-card-title>
      <v-card-text>
        <!-- Input field for file name -->
        <v-text-field v-model="enteredFileName" label="Invoeren bestandsnaam" outlined dense></v-text-field>
      </v-card-text>
      <v-card-actions>
        <!-- Button for XML download -->
        <v-btn
          color="primary"
          variant="elevated"
          @click="downloadXMLWithFileName"
          :disabled="!xmlContent">XML downloaden
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import {store} from "@/store/app";

export default {
  name: "XmlDownloader",
  props: {
    xmlContent: {
      type: String,
      required: true,
    },
  },
  data() {
    return {
      enteredFileName: '',
    };
  },
  methods: {
    downloadXMLWithFileName() {
      // Trim the entered file name or set a default name
      let fileName = this.enteredFileName.trim() || 'your_xml_file.xml';

      // Parse the XML content
      const parser = new DOMParser();
      const xmlDoc = parser.parseFromString(this.xmlContent, 'text/xml');

      // Get the root element of the XML document
      const rootElement = xmlDoc.documentElement;

      // Check if the username attribute already exists
      const existingUsername = rootElement.getAttribute('username');

      if (existingUsername) {
        // If a username already exists, replace it with the new one
        rootElement.setAttribute('username', store().user.username);
      } else {
        // If no username exists, add a new username attribute
        rootElement.setAttribute('username', store().user.username);
      }

      // Serialize the modified XML document back to a string
      const modifiedXML = new XMLSerializer().serializeToString(xmlDoc);

      // Create a Blob from the modified XML content
      const blob = new Blob([modifiedXML], {type: 'text/xml'});

      // Create a temporary URL for the Blob
      const url = URL.createObjectURL(blob);

      // Use window.open to trigger the download with the specified file name
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', fileName);
      document.body.appendChild(link);
      link.click();

      // Clean up by revoking the object URL
      URL.revokeObjectURL(url);

      // Reset filename after download
      this.enteredFileName = '';
    },
  },
};
</script>

<style>
.mt-3 {
  margin-top: 1rem;
}
</style>
