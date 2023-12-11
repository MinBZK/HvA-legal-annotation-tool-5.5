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
              <v-progress-circular
                v-else
                :size="24"
                :width="2"
                color="primary"
                indeterminate
              ></v-progress-circular>
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
    loginUser() {
      this.usernameErrors = [];
      this.passwordErrors = [];
      this.loginError = false;

      if (!this.username) {
        this.usernameErrors.push('Gebruikersnaam is verplicht.');
      }

      if (!this.password) {
        this.passwordErrors.push('Wachtwoord is verplicht.');
      }

      if (!this.username || !this.password) {
        return;
      }

      this.loading = true;
      const userData = {
        username: this.username,
        password: this.password
      };

      axios.post('http://localhost:8085/auth/login', userData)
        .then(response => {
          this.loading = false;
          console.log('Login successful', response.data);
          this.loginSuccess = true; // Update loginSuccess na een succesvolle inlogpoging
          this.loginSuccesMessage = 'Inloggen succesvol!';
          this.clearSuccessMessage();
          // Je kunt de gebruiker doorsturen of andere acties uitvoeren bij succesvol inloggen
        })
        .catch(error => {
          this.loading = false;
          this.loginError = true;
          this.loginErrorMessage = 'Ongeldige gebruikersnaam of wachtwoord';
          console.error('Login failed', error);
          // Afhandeling van inlogfouten (bericht aan de gebruiker tonen, enz.)
        });
    },
    clearSuccessMessage() {
      setTimeout(() => {
        this.loginSuccess = false;
        this.loginSuccesMessage = '';
      }, 5000); // Clear after 5 seconds (adjust as needed)
    },

    recoverPassword() {
      // Voer hier de logica uit voor wachtwoordherstel
      // bijvoorbeeld: redirect naar wachtwoordherstelpagina of toon een modaal venster voor wachtwoordherstel
    },
    goToRegisterPage() {
      // Voer hier de logica uit om naar de registratiepagina te navigeren
      // bijvoorbeeld: gebruik router.push() of window.location.href = '/registreren'
    }
  }
};
</script>
