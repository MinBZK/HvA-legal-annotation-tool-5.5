<!--<template>-->
<!--  <router-view />-->
<!--</template>-->

<!--<script setup>-->
<!--  //-->
<!--</script>-->

<template>
  <div>
    <router-view></router-view>
  </div>
</template>
<script>

import XmlLoader from "./components/XmlLoader.vue";
import XmlDashboard from "@/components/XmlDashboard";

export default {
  components: {XmlLoader, XmlDashboard},
  data() {
    return {};
  },
  methods: {}
}

</script>
