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
            <v-row>
              <v-col>
                <v-text-field label="Voeg een nieuwe definitie toe" v-model="definition"
                              @keyup.enter="saveDialog"></v-text-field>
              </v-col>
              <v-col>
                <v-select v-if="olderDefinitions.length" :items="olderDefinitions"
                          label="Zie oudere definities"
                          :item-title="getDefinitionTextFields"
                          :item-value="'definitie'"
                >
                </v-select>
              </v-col>
            </v-row>
          </v-window-item>

          <v-window-item value="two">
            <v-row>
              <v-col>
                <v-select @keyup.enter="saveDialog" label="Label" :items="colourOptions" item-title="label"
                          item-value="color"
                          v-model="selectedColour">
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
              </v-col>
              <v-col>
                <v-select v-if="olderLabels.length" :items="olderLabels"
                          label="Zie oudere labels"
                          :item-title="getLabelTextFields"
                          :item-value="'label'"
                >
                </v-select>
              </v-col>
            </v-row>
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
      definitionCopy: "",
      labelCopy: "",
      olderDefinitions: [],
      olderLabels: [],
      selectedColour: "",
      label: "",
      matchedWordsWithIndexes: [],
      colourOptions: [
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
      startMatch: undefined,
      endMatch: undefined,
    }
  },
  methods: {
    getDefinitionTextFields(item) {
      return `${item.definitie} - ${this.formatDate(item.date)} - ${item.username}`
    },

    getLabelTextFields(item) {
      return `${item.label} - ${this.formatDate(item.datum)} - ${item.username}`
    },

    formatDate(dateString) {
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      return `${year}/${month}/${day}`;
    },

    closeDialog() {
      this.$emit('close');
    },

    saveDialog() {
      if (this.definition && this.definition !== this.definitionCopy) {
        this.saveDefinition();
      }

      if (this.selectedColour !== "" || this.selectedColour !== this.labelCopy) {
        this.saveLabel();
      }
      this.$emit('annotation-saved');
      this.$emit('close');
    },

    checkMatchingDefinitions(words) {
      this.handleSelectedWord();

      if (this.isValueUndefinedOrEmpty(store().definitions) === false) {
        return;
      }

      let matchingDefinition = store().definitions.filter(definition => definition.woord === words);
      this.olderDefinitions = matchingDefinition;
      matchingDefinition = matchingDefinition[matchingDefinition.length - 1];

      if (this.isValueUndefinedOrEmpty(matchingDefinition) === false) {
        return;
      }

      this.compareLabelPositionWithMatchedWords(matchingDefinition);

      if (this.startMatch && this.endMatch) {
        this.definition = matchingDefinition.definitie;
      }
    },

    isValueUndefinedOrEmpty(variable) {
      return !(variable === undefined || variable.length === 0);
    },

    checkMatchingLabels(selectedWords) {
      this.handleSelectedWord();

      // Check if any labels are defined in the store
      if (this.isValueUndefinedOrEmpty(store().labels)) {
        return;
      }

      // Find labels in the store that match the selected words
      let matchingLabels = store().labels.filter(label => label.woord === selectedWords);

      // Check if any matching labels are found
      if (this.isValueUndefinedOrEmpty(matchingLabels)) {
        return;
      }

      // Assign older labels for reference and select the most recent label
      this.olderLabelsList = matchingLabels;
      let latestMatchingLabel = matchingLabels[matchingLabels.length - 1];

      // Check if the positions of the latest label align with the matched words
      this.compareLabelPositionWithMatchedWords(latestMatchingLabel);

      // If positions match, set the label and its corresponding color
      if (this.isStartPositionMatch && this.isEndPositionMatch) {
        this.currentLabel = latestMatchingLabel.label;
        this.selectedLabelColor = this.colourOptions.find(option => option.label === this.currentLabel).color;
      }
    },

    compareLabelPositionWithMatchedWords(label) {
      // Compare the start and end positions of the label with the positions in matchedWordsWithIndexes
      this.isStartPositionMatch = label.positie_start === this.matchedWordsWithIndexes[0].number;
      this.isEndPositionMatch = label.positie_end === this.matchedWordsWithIndexes[this.matchedWordsWithIndexes.length - 1].number;
    },


    getFormattedDate() {
      let date = new Date();
      return date.toISOString();
    },

    saveDefinition() {
      // Processed selectedText should be a computed property or handled in a watcher
      if (!this.selectedText || this.definition === "") {
        return;
      }

      let { positionStart, positionEnd } = this.calculatePositionIndexes();

      let definition = {
        definitie: this.definition,
        positie_start: positionStart,
        positie_end: positionEnd,
        woord: this.selectedText,
        date: this.getFormattedDate(),
      };

      // Ensure XML identifier is present
      let xmlBronId = store().loadedXMLIdentifier;
      if (!xmlBronId) {
        console.error('XML identifier is missing');
        return;
      }

      // Fetch username from a Vuex store or a computed property instead of directly from localStorage
      let username = this.getUsernameFromStore(); // This method should handle the JSON parsing and error checking

      try {
        this.saveAndFetchDefinitions(definition, xmlBronId, username);
      } catch (error) {
        console.error('Error saving definition:', error);
      }
    },

    getUsernameFromStore() {
      try {
        return JSON.parse(localStorage.getItem('username'));
      } catch (error) {
        console.error('Error parsing username from localStorage:', error);
        return null;
      }
    },

    /**
     * Calculates the start and end positions based on matched words with indexes.
     * @returns {Object} An object with positie_start and positie_end properties.
     */
    calculatePositionIndexes() {
      if (this.matchedWordsWithIndexes.length === 0) {
        this.handleSelectedWord()
      }

      let positie_start = this.matchedWordsWithIndexes[0].number;
      let positie_end = this.matchedWordsWithIndexes[this.matchedWordsWithIndexes.length - 1].number;
      return {positie_start, positie_end};
    },

    /**
     * Saves the definition, posts it, and fetches updated definitions.
     * @param {Object} definition - The definition to be saved.
     * @param {string} xmlBronName - The XML identifier.
     * @param {string} username - The username.
     */
    async saveAndFetchDefinitions(definition, xmlBronName, username) {
      let xmlbronDate = store().loadedXMLDate;
      await store().postDefinition(definition, xmlBronName, username, xmlbronDate);
    },

    saveLabel() {
      // Find the label object based on the selectedColor
      const selectedText = this.removeDotsAndSymbols(this.selectedText);
      const selectedLabelObject = this.colourOptions.find(option => option.color === this.selectedColour);

      if (selectedLabelObject) {
        // Create a span element to wrap the selected text with the chosen color
        const span = document.createElement('span');
        span.style.backgroundColor = this.selectedColour;
        span.textContent = selectedText;

        // Replace the selected text with the highlighted span
        const selection = window.getSelection();
        const range = selection.getRangeAt(0);
        range.deleteContents();
        range.insertNode(span);

        let positie_start = this.matchedWordsWithIndexes[0].number;
        let positie_end = this.matchedWordsWithIndexes[this.matchedWordsWithIndexes.length - 1].number;

        let label = {
          label: selectedLabelObject.label,
          positie_start: positie_start,
          positie_end: positie_end,
          woord: selectedText,
          date: this.getFormattedDate()
        }

        let xmlBronId = store().loadedXMLIdentifier;
        let username = JSON.parse(localStorage.getItem('username'));

        this.saveAndFetchLabels(label, xmlBronId, username);
      } else {
        console.warn('Label not found for the selected color:', this.selectedColour);
      }
    },

    async saveAndFetchLabels(label, xmlBronName, username) {
      let xmlbronDate = store().loadedXMLDate;
      await store().postLabel(label, xmlBronName, username, xmlbronDate);
    },

    removeDotsAndSymbols(word) {
      // Remove special symbols
      const cleanedWord = word.replace(/[.,]/gi, '');
      // Remove leading and trailing spaces
      return cleanedWord.trim();
    },

    findNumbersForTextStartingFrom(wordsArray, targetText, hoveredWord) {
      // Split the target text into a Set of unique words for efficient lookup
      let targetWords = new Set(targetText.split(/\s+/));

      // Initialize an array to store the result data
      let resultData = [];

      // Get the starting number from the hovered word
      let startNumber = hoveredWord.number;

      // Calculate the size of the target words set
      let targetWordsSize = targetWords.size;

      // Find the index in the array where the hovered word is located
      let startIndex = wordsArray.findIndex(word => word.number === startNumber);

      // Handle case where the hovered word is not found
      if (startIndex === -1) {
        console.error(`Word with number ${startNumber} not found in the array.`);
        return resultData;
      }

      // Loop through the words array, checking words around the hovered word
      for (let i = 0; i < targetWordsSize; i++) {
        // Check and add the word before the hovered word, if it matches
        this.addWordIfMatches(wordsArray, startIndex - i, targetWords, resultData);

        // Check and add the word after the hovered word, if it matches
        this.addWordIfMatches(wordsArray, startIndex + i, targetWords, resultData);
      }

      // Return the array of words that match the target text
      return resultData;
    },

    addWordIfMatches(wordsArray, index, targetWords, resultData) {
      // Check if the index is within the bounds of the words array
      if (index >= 0 && index < wordsArray.length) {
        // Remove any dots and symbols from the word for accurate comparison
        let word = this.removeDotsAndSymbols(wordsArray[index].name);

        // If the word is part of the target words, add it to the result data
        if (targetWords.has(word)) {
          resultData.push(wordsArray[index]);
        }
      }
    },

    handleSelectedWord() {
      this.matchedWordsWithIndexes = this.findNumbersForTextStartingFrom(this.allWordsInXML, this.selectedText, this.hoveredWordObject);
    }
  },

  mounted() {
    this.checkMatchingDefinitions(this.selectedText);
    this.checkMatchingLabels(this.selectedText);
    this.handleSelectedWord();
    this.definitionCopy = this.definition;
    this.labelCopy = this.label.label;
  },

  watch: {
    selectedText(newValue) {
      this.checkMatchingDefinitions(newValue);
      this.checkMatchingLabels(newValue);
    },
  },
}
;
</script>

<style scoped>

</style>
