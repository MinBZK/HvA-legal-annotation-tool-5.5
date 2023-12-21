// Utilities
import {defineStore} from 'pinia'
import axios from "axios";
import axiosInterceptor from "@/axios-request/axios-interceptor";

export const store = defineStore('app', {
  state: () => ({
    definitions: [],
    labels: [],
    loadedXMLIdentifier: "",
    user: {loggedIn: false, permissions: ""},
    tokenJWT: JSON.parse(localStorage.getItem('tokenJWT')) === undefined ? "" : JSON.parse(localStorage.getItem('tokenJWT')),
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

    async getLabels() {
      let response = await this.genericGetRequests("label");
      this.definitions = response.data;
    },

    async postDefinition(body) {
      this.responseCode = await this.genericPostRequest("define/addDefinition", body);
    },

    async postLabel(body) {
      this.responseCode = await this.genericPostRequest("label/addLabel", body);
    },

    async postNewXMLBron(body) {
      this.responseCode = await this.genericPostRequest("XMLBron/api/v1/", body);
    },

  },
});

