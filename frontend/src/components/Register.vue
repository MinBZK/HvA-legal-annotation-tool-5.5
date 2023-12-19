<template>
  <v-container>
    <v-card>
      <v-card-title class="headline">Registreren</v-card-title>
      <v-card-text>
        <v-form @submit.prevent="registerUser">
          <v-row>
            <v-col cols="12" md="6">
              <v-text-field v-model="firstName" label="Voornaam" required></v-text-field>
            </v-col>
            <v-col cols="12" md="6">
              <v-text-field v-model="lastName" label="Achternaam" required></v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="12">
              <v-text-field v-model="username" label="Gebruikersnaam" required></v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="12">
              <v-text-field v-model="email" label="Email" type="email" required></v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="12">
              <v-text-field v-model="password" label="Wachtwoord" type="password" required></v-text-field>
            </v-col>
          </v-row>

          <v-row>
            <v-col cols="12">
              <v-btn type="submit" color="primary">Registreer</v-btn>
            </v-col>
          </v-row>
        </v-form>

        <br>

        <!-- Display registration status and message -->
        <v-alert v-if="registrationStatus" :type="registrationStatusColor" outlined>
          {{ registrationMessage }}
        </v-alert>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      username: '',
      registrationStatus: '', // 'success' or 'error'
      registrationMessage: ''
    };
  },
  computed: {
    registrationStatusColor() {
      return this.registrationStatus === 'success' ? 'success' : 'error';
    }
  },
  methods: {
    registerUser() {
      const userData = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        password: this.password,
        username: this.username
      };

      axios.post('http://localhost:8085/auth/register', userData)
        .then(response => {
          this.registrationStatus = 'success';
          this.registrationMessage = 'Registratie is gelukt';
          console.log('Registration successful', response.data);
          // You can redirect the user or perform any other actions on successful registration
        })
        .catch(error => {
          this.registrationStatus = 'error';
          this.registrationMessage = 'Email of gebruikersnaam al in gebruik';
          console.error('Registration failed', error);
          // Handle registration error (show a message to the user, etc.)
        });
    }
  }
};
</script>

<style scoped>
</style>
