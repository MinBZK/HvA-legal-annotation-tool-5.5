<template>
  <div>
    <!-- Header -->
    <v-app-bar app color="primary" dense>
      <!-- Header content -->
      <v-app-bar-nav-icon @click.stop="drawer = !drawer" :class="{ 'is-active': drawer }"></v-app-bar-nav-icon>
      <v-toolbar-title>Dashboard</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn v-if="!isLoggedIn" text @click="login">Login</v-btn>
      <v-btn v-else text @click="logout">Logout</v-btn>
    </v-app-bar>

    <!-- Sidebar -->
    <v-navigation-drawer v-model="drawer" app temporary>
      <!-- Sidebar content -->
      <v-list dense>
        <!-- Menu items -->
        <v-list-item v-for="(item, index) in menuItems" :key="index" link @click="navigate(item)">
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
  </div>
</template>

<script>
import {store} from "@/store/app";
import router from "@/router";

export default {
  data() {
    return {
      isLoggedIn: store().user.loggedIn, // Flag for user login status
      drawer: false, // Flag to control sidebar display
      menuItems: [
        // Array to store menu items for the sidebar
        {title: 'Dashboard', path: "/login", icon: 'mdi-view-dashboard'},
        // Add more menu items as needed
      ],
    };
  },
  methods: {
    login() {
      // Login logic
    },
    async logout() {
      await store().logout();
      window.location.reload();
    },
    navigate(item) {
      router.push({ path: item.path});
      // router.push({name: item.title});
      console.log('Item title:', item.title);
      console.log('Router instance:', router);
    },
  },
};
</script>

<style>
.v-app-bar {
  box-shadow: 0 2px 4px -1px rgba(0, 0, 0, 0.1), 0 4px 5px 0 rgba(0, 0, 0, 0.08), 0 1px 10px 0 rgba(0, 0, 0, 0.06);
}

.v-app-bar-nav-icon {
  transition: transform 0.3s ease-in-out;
}

.v-app-bar-nav-icon.is-active {
  transform: rotate(90deg);
}
</style>

