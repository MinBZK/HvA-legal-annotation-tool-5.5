<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-card-title class="text-center">
            <h2>Inloggen</h2>
          </v-card-title>
          <v-card-text>
            <!-- Form for user login -->
            <v-form @submit.prevent="loginUser">
              <v-text-field v-model="username" label="Gebruikersnaam" outlined required :error-messages="usernameErrors"
                            class="mb-4"></v-text-field>
              <v-text-field v-model="password" label="Wachtwoord" type="password" outlined required
                            :error-messages="passwordErrors" class="mb-4"></v-text-field>
              <!-- Button for login submission -->
              <v-btn type="submit" color="primary" class="white--text" block v-if="!loading">Inloggen</v-btn>
              <!-- Loading indicator while processing login -->
              <v-progress-circular v-else :size="24" :width="2" color="primary" indeterminate></v-progress-circular>
              <v-row class="mt-4">
                <!-- Other content -->
              </v-row>
            </v-form>
            <!-- Display error messages -->
            <v-alert v-if="loginError" type="error" outlined class="mt-4" dense>
              {{ loginErrorMessage }}
            </v-alert>
            <!-- Display success message -->
            <v-alert v-if="loginSuccess" type="success" outlined class="mt-4" dense>
              {{ loginSuccesMessage }}
            </v-alert>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import {store} from "@/store/app";

export default {
  data() {
    return {
      username: '',
      password: '',
      loading: false,
      errors: {
        username: [],
        password: [],
      },
      loginError: false,
      loginErrorMessage: "",
      loginSuccess: false,
      loginSuccessMessage: "",
    };
  },
  methods: {
    // Method to validate form inputs
    async loginUser() {
      // Reset login error
      this.loginError = false;

      // Validate form inputs
      if (this.validateForm()) {
        return;
      }

      // Begin loading state for login
      this.loading = true;
      try {
        // Prepare user data for login
        const userData = {username: this.username, password: this.password};
        // Call login API asynchronously
        const response = await store().login(userData);
        // Process successful login response
        console.log(response.data)
        this.handleLoginSuccess(response.data);
      } catch (error) {
        // Handle login error
        this.loading = false;
        this.loginError = true;
        this.loginErrorMessage = "Ongeldige gebruikersnaam of wachtwoord";
        console.error("Login failed", error);
      }
    },

    // Method to handle successful login response
    handleLoginSuccess(data) {
      // Update user data and navigate to dashboard
      store().tokenJWT = data.accesToken;
      store().user.loggedIn = true;
      store().user.username = data.username;
      localStorage.setItem("isLoggedIn", JSON.stringify(true));
      localStorage.setItem("tokenJWT", JSON.stringify(store().tokenJWT));
      this.$router.push({path: "/dashboard"});
      // Show success message for a few seconds
      this.showLoginSuccessMessage();
    },

    // Method to validate form inputs and set errors
    validateForm() {
      this.errors.username = !this.username ? ["Gebruikersnaam is verplicht."] : [];
      this.errors.password = !this.password ? ["Wachtwoord is verplicht."] : [];
      return !this.username || !this.password;
    },

    // Method to display success message temporarily
    showLoginSuccessMessage() {
      this.loginSuccess = true;
      this.loginSuccessMessage = "Inloggen succesvol!";
      setTimeout(() => {
        this.loginSuccess = false;
        this.loginSuccessMessage = "";
      }, 5000);
    },
  }
};
</script>
