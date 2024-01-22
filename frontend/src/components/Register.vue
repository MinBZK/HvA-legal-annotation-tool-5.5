<template>
  <!-- Container for registration form -->
  <v-container>
    <!-- Card for the registration form -->
    <v-card>
      <!-- Card title -->
      <v-card-title class="headline">Registreren</v-card-title>

      <!-- Card text (contains the form and registration status) -->
      <v-card-text>
        <!-- Form to capture user registration details -->
        <v-form @submit.prevent="registerUser">
          <!-- Row for user's first and last name -->
          <v-row>
            <v-col cols="12" md="6">
              <v-text-field v-model="firstName" label="Voornaam" required></v-text-field>
            </v-col>
            <v-col cols="12" md="6">
              <v-text-field v-model="lastName" label="Achternaam" required></v-text-field>
            </v-col>
          </v-row>

          <!-- Row for user's username -->
          <v-row>
            <v-col cols="12">
              <v-text-field v-model="username" label="Gebruikersnaam" required></v-text-field>
            </v-col>
          </v-row>

          <!-- Row for user's email address -->
          <v-row>
            <v-col cols="12">
              <v-text-field v-model="email" label="Email" type="email" required></v-text-field>
            </v-col>
          </v-row>

          <!-- Row for user's password -->
          <v-row>
            <v-col cols="12">
              <v-text-field v-model="password" label="Wachtwoord" type="password" required></v-text-field>
            </v-col>
          </v-row>

          <!-- Row for the registration button -->
          <v-row>
            <v-col cols="12">
              <v-btn type="submit" color="primary">Registreer</v-btn>
            </v-col>
          </v-row>
        </v-form>

        <!-- Display registration status and message -->
        <br>
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
            // User registration data
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            username: '',

            // Registration status and message
            registrationStatus: '', // 'success' or 'error'
            registrationMessage: ''
        };
    },
    computed: {
        // Computed property to determine the color of the registration status
        registrationStatusColor() {
            return this.registrationStatus === 'success' ? 'success' : 'error';
        }
    },
    methods: {
        // Method to register a new user
        registerUser() {
            // Check if any of the required fields is empty
            if (!this.firstName || !this.lastName || !this.email || !this.password || !this.username) {
                this.registrationStatus = 'error';
                this.registrationMessage = 'Vul alle gegevens in';
                return;
            }

            // User data to be sent to the server
            const userData = {
                firstname: this.firstName,
                lastname: this.lastName,
                email: this.email,
                password: this.password,
                username: this.username
            };

            // Send a POST request to the registration endpoint
            axios.post('http://localhost:8085/auth/register', userData)
                .then(response => {
                    // Handle successful registration
                    this.registrationStatus = 'success';
                    this.registrationMessage = 'Registratie is gelukt';
                    console.log('Registration successful', response.data);
                    // You can redirect the user or perform any other actions on successful registration
                })
                .catch(error => {
                    // Handle registration error
                    this.registrationStatus = 'error';
                    this.registrationMessage = 'Email of gebruikersnaam al in gebruik';
                    console.error('Registration failed', error);
                    // You can show a message to the user or perform other actions on registration error
                });
            console.log("registrationMessage", this.registrationMessage);
        }
    }
};
</script>

<style scoped>
</style>
