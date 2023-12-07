<template>
  <v-tooltip bottom v-if="showTooltip && word.name === hoveredWord"
             activator="parent"
             location="top"
  >
    {{ matchedWord.definition }}
  </v-tooltip>
</template>

<script>
export default {
  name: "ToolTip"
}
</script>

<style scoped>

</style>
