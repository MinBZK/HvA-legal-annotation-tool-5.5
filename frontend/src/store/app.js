// Utilities
import {defineStore} from 'pinia'
import axios from "axios";
import axiosInterceptor from "@/axios-request/axios-interceptor";

export const store = defineStore('app', {
  state: () => ({
    definitions: [],
    loadedXMLIdentifier: "",
    user: {logged: true, permissions: ""},
    tokenJWT: "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1yZSIsImV4cCI6MTcwMjI1MDE1MSwiaWF0IjoxNzAyMjQ5MjUxLCJyb2xlcyI6IlVTRVIifQ.Hpt1CLnt7fRXOFjhtzL8cHOhLErTav0LFZJzNXp1qt7lYOy5sJK0sOLOsLfkZA-X8g2Tw3EKcvI2R8MjB1gKrKZlxs-oGNGjKxmjwVUnL1TxV8QSjNvkLlcxXD2ushuHU2oQeF8hGg9pYKOikTzNLxMC7OhXKytMHx9XZRazg_eypavdpIct5OLlE4H_U6cMJppxaizp2rTMD1ezCgmgp9ccSs31HZS6VrI09Q_P0FdwcDLYlr3ymiJKGP2L4VnLd5IUxaUWetd_minsW2wYOrGzX4t37ZMlV0DSmHuwIsMeXKwXTgoLvpLIsnjovIxq9XVyW8JtL7QcfUO4G9VIcA"
  }),

  actions: {
    async genericGetRequests(url) {
      let responseData = "";

      await axiosInterceptor({
        method: 'get',
        url: url,
        headers: {'Authorization': `Bearer ${this.tokenJWT}`},
      })
        .then((response) => {
          if (process.env.NODE_ENV === 'development') {
            console.log(`${url} :`, response);
          }
          this.responseCode = response.status;
          responseData = response;
        })
        .catch((e) => {
          if (process.env.NODE_ENV === 'development') {
            console.log(e);
          }
          this.responseCode = e.response.status;
          responseData = e.response.status
          //throw e;
        });
      return responseData;
    },

    async genericPostRequest(url, body) {
      let responseData = "";

      await axiosInterceptor({
        method: 'post',
        url: url,
        data: body,
        headers: {'Authorization': `Bearer ${this.tokenJWT}`},
      })
        .then((response) => {
          if (process.env.NODE_ENV === 'development') {
            console.log(`${url} :`, response);
          }
          this.responseCode = response.status;
          responseData = response;
        })
        .catch((e) => {
          if (process.env.NODE_ENV === 'development') {
            console.log(e);
          }
          this.responseCode = e.response.status;
          responseData = e.response.status
          //throw e;
        });
      return responseData;
    },

    async deleteDefinition(definitionId) {
      try {
        await axios.delete(`http://localhost:8085/define/deleteDefinition/${definitionId}`);
        // Optionally update state or handle success
        console.log('Definition deleted successfully');
      } catch (error) {
        console.error('Error deleting definition:', error);
      }
    },

    async getXMLBron(artikelNaam) {
      return await this.genericGetRequests(`XMLBron/byName/${artikelNaam}`);
    },

    async getDefinitions() {
      let response = await this.genericGetRequests("define");
      this.definitions = response.data;
    },

    async postDefinition(body) {
      this.responseCode = await this.genericPostRequest("define/addDefinition", body);
    },

    async postNewXMLBron(body) {
      this.responseCode = await this.genericPostRequest("XMLBron/api/v1/", body);
    },

  },
});

