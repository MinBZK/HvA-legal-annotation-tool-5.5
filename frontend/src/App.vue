<!--<template>-->
<!--  <router-view />-->
<!--</template>-->

<!--<script setup>-->
<!--  //-->
<!--</script>-->

<template>
  <div>

    <register />
  </div>
</template>
<script>

import XmlLoader from "./components/XmlLoader.vue";
import XmlDashboard from "@/components/XmlDashboard";
import Register from "@/components/Register.vue";
export default {
  components: {XmlLoader, XmlDashboard, Register}
}
</script>
