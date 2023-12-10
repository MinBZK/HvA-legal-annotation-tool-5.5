<template>
  <v-container>
    <v-card>
      <v-card-title class="headline">Annoteer tekst</v-card-title>

      <v-card-subtitle>{{ selectedText }}</v-card-subtitle>

      <v-tabs v-model="tab" background-color="primary">
        <v-tab value="one">Definitie</v-tab>
        <v-tab value="two">Labels</v-tab>
        <v-tab value="three">Relaties</v-tab>

      </v-tabs>

      <v-card-text>
        <v-window v-model="tab">

          <v-window-item value="one">
            <v-text-field label="Definitie" @keyup.enter="saveDialog" v-model="definition"></v-text-field>
          </v-window-item>

          <v-window-item value="two">
            <v-select label="Label" :items="colorOptions" item-title="label" item-value="color" v-model="selectedColor">
              <template v-slot:item="{ props, item }">
                <v-list-item v-bind="props">
                  <v-chip
                    variant="flat"
                    text-color="white"
                    :color="item.raw.color"
                    size="small">
                  </v-chip>
                </v-list-item>
              </template>
            </v-select>

          </v-window-item>

          <v-window-item value="three"></v-window-item>
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
    },

    allWordsInXML: {
      type: Object,
      required: true
    },

    hoveredWordObject: {
      type: Object,
      required: true
    }

  },
  data() {
    return {
      isVis: true,
      tab: null,
      definition: "",
      selectedColor: "",
      colorOptions: [
        {label: 'Rechtssubject', color: 'rgb(194, 231, 255)'},
        {label: 'Rechtsbetrekking', color: 'rgb(112, 164, 255)'},
        {label: 'Rechtsobject', color: 'rgb(152, 190, 241)'},
        {label: 'Rechtsfeit', color: 'rgb(151, 214, 254)'},
        {label: 'Voorwaarde', color: 'rgb(145, 232, 211)'},
        {label: 'Afleidingsregel', color: 'rgb(255, 122, 122)'},
        {label: 'Variabele', color: 'rgb(255, 217, 93)'},
        {label: 'Variabelewaarde', color: 'rgb(255, 243, 128)'},
        {label: 'Parameter', color: 'rgb(255, 180, 180)'},
        {label: 'Parameterwaarde', color: 'rgb(255, 216, 239)'},
        {label: 'Operator', color: 'rgb(193, 235, 225)'},
        {label: 'Tijdsaanduiding', color: 'rgb(216, 176, 249)'},
        {label: 'Plaatsaanduiding', color: 'rgb(239, 202, 246)'},
        {label: 'Delegatiebevoegdheid', color: 'rgb(206, 206, 206)'},
        {label: 'Delegatieinvulling', color: 'rgb(226, 226, 226)'},
        {label: 'Brondefinitie', color: 'rgb(246, 246, 246)'},
      ],
      matchedWordsWithIndexes: "",
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
        this.selectedColor = matchingDefinition.selectedColor;
      }
    },

    saveDefinition() {
      const selectedText = this.removeDotsAndSymbols(this.selectedText);

      if (selectedText) {
        const newDefinition = {
          text: selectedText,
          definition: this.definition,
          selectedColor: this.selectedColor,
        };

        store().definitions.push(newDefinition);

        let positie_start = this.matchedWordsWithIndexes[0].number;
        let positie_end = this.matchedWordsWithIndexes[this.matchedWordsWithIndexes.length - 1].number;

        let definition = {
          definitie: newDefinition.definition,
          positie_start: positie_start,
          positie_end: positie_end,
          woord: newDefinition.text
        }

        store().postDefinition(definition);

        this.$emit('annotation-saved', {
          text: selectedText,
          color: this.selectedColor
        });
      }
    },

    removeDotsAndSymbols(word) {
      // Remove special symbols
      const cleanedWord = word.replace(/[.,]/gi, '');
      // Remove leading and trailing spaces
      return cleanedWord.trim();
    },

    findNumbersForTextStartingFrom(wordsArray, targetText, hoveredWord) {
      // Split the target text into an array of target words
      let targetWords = targetText.split(/\s+/);

      // Initialize an array to store the result data
      let resultData = [];

      // Get the starting number from the hovered word
      let startNumber = hoveredWord.number;

      // Calculate the target words size
      let targetWordsSize = targetWords.length;

      // Find the index in the array to start the search
      let startIndex = wordsArray.findIndex((word) => word.number === startNumber);

      // Check if the starting number is not found in the array
      if (startIndex === -1) {
        console.log(`Word with number ${startNumber} not found in the array.`);
        return resultData;
      }

      // Iterate up the array
      for (let i = startIndex; i >= Math.max(0, startIndex - targetWordsSize + 1); i--) {
        // Check if the name of the word is in the target words
        if (targetWords.includes(wordsArray[i].name)) {
          // Add the word to the result data at the beginning
          resultData.unshift(wordsArray[i]);
        }
        // Check if enough words are found, and if so, break out of the loop
        if (resultData.length === targetWordsSize) {
          break;
        }
      }

      if (resultData.length === targetWordsSize) {
        return resultData;
      }

      // Iterate down the array
      for (let i = startIndex; i < Math.min(wordsArray.length, startIndex + targetWordsSize); i++) {
        // Check if the name of the word is in the target words
        if (targetWords.includes(wordsArray[i].name)) {
          // Add the word to the result data at the end
          resultData.push(wordsArray[i]);
        }
        // Check if enough words are found, and if so, break out of the loop
        if (resultData.length === targetWordsSize * 2) {
          break;
        }
      }

      // Return the result data containing the matching words
      return resultData;
    },
  },

  mounted() {
    this.checkMatchingDefinitions(this.selectedText);
    this.matchedWordsWithIndexes = this.findNumbersForTextStartingFrom(this.allWordsInXML, this.selectedText, this.hoveredWordObject);
  },

  watch: {
    selectedText(newValue) {
      this.checkMatchingDefinitions(newValue);
    },
  },
}
;
</script>

<style scoped>

</style>
