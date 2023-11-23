<template>
  <v-container>
    <v-card title="Dialog">
      <v-card-text>
        Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
        magna aliqua.
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn
          text="Close Dialog"
          @click="closeDialog"
        ></v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "AnnotatieDialog",
  props: {
    isVisible: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      isVis: true
    }
  },
  methods: {
    closeDialog() {
      this.$emit('close');
    },
  },
  mounted() {
    console.log("AnnotatieDialog mounted. isVisible:", this.isVisible);
  },

  watch: {
    isVisible(newVal) {
      console.log("isVisible prop changed to:", newVal);
    },
  },
};
</script>

<style scoped>

</style>
