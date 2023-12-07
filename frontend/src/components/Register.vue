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
      username: ''
    };
  },
  methods: {
    registerUser() {
      // Send the registration data to the backend API
      const userData = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        password: this.password,
        username: this.username
      };

      console.log(userData);

      // Replace 'YOUR_BACKEND_API_URL' with the actual URL of your backend API
      axios.post('YOUR_BACKEND_API_URL/register', userData)
        .then(response => {
          console.log('Registration successful', response.data);
          // You can redirect the user or perform any other actions on successful registration
        })
        .catch(error => {
          console.error('Registration failed', error);
          // Handle registration error (show a message to the user, etc.)
        });
    }
  }
};
</script>

<style scoped>
</style>
