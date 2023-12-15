<template>
  <v-container fluid>
    <v-row justify="center">
      <v-col cols="12" sm="8" md="4">
        <v-card class="elevation-12">
          <v-card-title class="text-center">
            <h2>Inloggen</h2>
          </v-card-title>
          <v-card-text>
            <v-form @submit.prevent="loginUser">
              <v-text-field v-model="username" label="Gebruikersnaam" outlined required :error-messages="usernameErrors" class="mb-4"></v-text-field>
              <v-text-field v-model="password" label="Wachtwoord" type="password" outlined required :error-messages="passwordErrors" class="mb-4"></v-text-field>
              <v-btn type="submit" color="primary" class="white--text" block v-if="!loading">Inloggen</v-btn>
              <v-progress-circular v-else :size="24" :width="2" color="primary" indeterminate></v-progress-circular>
              <v-row class="mt-4">
                <!-- Wachtwoord vergeten en Registreren knoppen -->
              </v-row>
            </v-form>
            <v-alert v-if="loginError" type="error" outlined class="mt-4" dense>
              {{ loginErrorMessage }}
            </v-alert>
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
import axios from 'axios';
import { store } from "@/store/app";

export default {
  data() {
    return {
      username: '',
      password: '',
      loading: false,
      usernameErrors: [],
      passwordErrors: [],
      loginError: false,
      loginErrorMessage: '',
      loginSuccess: false,
      loginSuccesMessage: '',
    };
  },
  methods: {
    validateForm() {
      this.usernameErrors = !this.username ? ['Gebruikersnaam is verplicht.'] : [];
      this.passwordErrors = !this.password ? ['Wachtwoord is verplicht.'] : [];
      return !this.username || !this.password;
    },

    loginUser() {
      this.loginError = false;

      if (this.validateForm()) {
        return;
      }

      this.loading = true;
      const userData = { username: this.username, password: this.password };

      axios.post('http://localhost:8085/auth/login', userData)
        .then(response => {
          this.loading = false;
          this.loginSuccess = true;
          this.loginSuccesMessage = 'Inloggen succesvol!';
          this.clearSuccessMessage();
          this.handleLoginSuccess(response.data);
        })
        .catch(error => {
          this.loading = false;
          this.loginError = true;
          this.loginErrorMessage = 'Ongeldige gebruikersnaam of wachtwoord';
          console.error('Login failed', error);
        });
    },

    handleLoginSuccess(data) {
      store().tokenJWT = data.jwt;
      store().user.loggedIn = true;
      localStorage.setItem("isLoggedIn", JSON.stringify(true));
      this.$router.push({ path: '/dashboard' });
    },

    clearSuccessMessage() {
      setTimeout(() => {
        this.loginSuccess = false;
        this.loginSuccesMessage = '';
      }, 5000);
    },

    recoverPassword() {
      // Handle password recovery logic
    },

    goToRegisterPage() {
      // Handle registration page navigation logic
    }
  }
};
</script>
