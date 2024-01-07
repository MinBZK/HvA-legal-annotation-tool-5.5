// Utilities
import {defineStore} from 'pinia'
import axios from "axios";
import axiosInterceptor from "@/axios-request/axios-interceptor";

export const store = defineStore('app', {
  state: () => ({
    definitions: [],
    xmlbronnen: [],
    labels: [],
    loadedXMLIdentifier: "",
    XMLBwbrCode: "",
    loadedXMLDate: "",
    user: {
      loggedIn: false,
      permissions: "",
      username: JSON.parse(localStorage.getItem('username'))
      === undefined ? "" : JSON.parse(localStorage.getItem('username'))
    },
    tokenJWT: JSON.parse(localStorage.getItem('tokenJWT'))
    === undefined ? "" : JSON.parse(localStorage.getItem('tokenJWT')),
  }),

  actions: {
    logout() {
      this.user.loggedIn = false;
      localStorage.setItem('isLoggedIn', JSON.stringify("false"));
      localStorage.setItem('tokenJWT', JSON.stringify(""));
      localStorage.setItem('username', JSON.stringify(""));
    },

    async genericGetRequests(url) {
      let responseData = "";

      await axiosInterceptor({
        method: 'get',
        url: url,
        headers: {'Authorization': `Bearer ${this.tokenJWT}`},
      })
        .then((response) => {
          if (process.env.NODE_ENV === 'development') {
            // console.log(`${url} :`, response);
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
            // console.log(`${url} :`, response);
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

    async getXMLBron(artikelNaam, xmlbronDate) {
      return await this.genericGetRequests(`XMLBron/byName/${artikelNaam}/${xmlbronDate}`);
    },

    async getXMLBronnenByNameTimeLine(artikelNaam){
      let response = await this.genericGetRequests(`XMLBron/api/v1/timelinedata/${artikelNaam}`);
      console.log(response)
      this.xmlbronnen = response.data
    },
    
    async getXMLbronnenByName(artikelNaam){
      let response = await this.genericGetRequests(`XMLBron/byName/${artikelNaam}`);
      console.log(response)
      this.xmlbronnen = response.data
    },
    
    async getDefinitions(xmlBronName, username, xmlbronDate) {
      let url = "define/getDefinitions";
      let response = await this.genericGetRequests(`${url}/${xmlBronName}/${username}/${xmlbronDate}`);
      this.definitions = response.data;
    },

    async getLabels() {
      let response = await this.genericGetRequests("label");
      this.labels = response.data;
    },

    async postDefinition(body, xmlBronName, username, xmlbronDate) {
      let url = "define/addDefinition";
      this.responseCode = await this.genericPostRequest(`${url}/${xmlBronName}/${username}/${xmlbronDate}`, body);
    },

    async postLabel(body) {
      this.responseCode = await this.genericPostRequest("label/addLabel", body);
    },

    async postNewXMLBron(body) {
      this.responseCode = await this.genericPostRequest("XMLBron/api/v1/", body);
    },

  },
});

