<template>
  <v-container>
    <v-card>
      <v-card-title class="headline">Annoteer tekst</v-card-title>

      <v-card-subtitle>{{ selectedText }}</v-card-subtitle>

      <v-tabs v-model="tab" background-color="primary">
        <v-tab value="one">Definitie</v-tab>
        <v-tab value="two">Relaties</v-tab>
      </v-tabs>

      <v-card-text>
        <v-window v-model="tab">

          <v-window-item value="one">
            <v-text-field label="Definitie" v-model="definition"></v-text-field>
          </v-window-item>

          <v-window-item value="two"></v-window-item>
        </v-window>
      </v-card-text>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn @click="saveDialog">Sla Annotation op</v-btn>
        <v-btn @click="closeDialog">Sluit scherm</v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import {store} from "@/store/app";

export default {
  name: "AnnotatieDialog",
  props: {
    isVisible: {
      type: Boolean,
      required: true,
    },
    selectedText: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      isVis: true,
      tab: null,
      definition: "",
    }
  },
  methods: {
    closeDialog() {
      this.$emit('close');
    },

    saveDialog() {
      this.saveDefinition();
      this.$emit('close');
    },

    checkMatchingDefinitions(text) {
      const matchingDefinition = store().definitions.find(definition => definition.text === text);

      if (matchingDefinition) {
        this.definition = matchingDefinition.definition;
      }
      console.log("HERE" + this.definition)
    },

    clearSelectedText() {
      // Clear the input fields or reset any necessary data properties
      this.selectedText = ''; //
      this.definition = ''; //
    },

    saveDefinition() {
      const selectedText = this.selectedText;

      if (selectedText) {
        const newDefinition = {
          text: selectedText,
          definition: this.definition,
        };

        store().definitions.push(newDefinition);

        //  this.clearSelectedText();
      }
    },

  },
  mounted() {
    this.checkMatchingDefinitions(this.selectedText);
  },

  watch: {
    selectedText(newValue) {
      this.checkMatchingDefinitions(newValue);
    },
  },
};
</script>

<style scoped>

</style>
