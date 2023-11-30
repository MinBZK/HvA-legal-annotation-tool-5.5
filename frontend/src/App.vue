<!--<template>-->
<!--  <router-view />-->
<!--</template>-->

<!--<script setup>-->
<!--  //-->
<!--</script>-->

<template>
  <div>
<!--    <xml-loader />-->
    <xml-dashboard />
  </div>
</template>
<script>

import XmlLoader from "./components/XmlLoader.vue";
import XmlDashboard from "@/components/XmlDashboard";
export default {
  components: {XmlLoader, XmlDashboard}
}
</script>
