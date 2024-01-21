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
    user: {
      loggedIn: false,
      permissions: "",
      username: JSON.parse(localStorage.getItem('username'))
      === undefined ? "" : JSON.parse(localStorage.getItem('username'))
    },
    tokenJWT: JSON.parse(localStorage.getItem('tokenJWT'))
    === undefined ? "" : JSON.parse(localStorage.getItem('tokenJWT')),
    refreshJWT: JSON.parse(localStorage.getItem('refreshJWT'))
    === undefined ? "" : JSON.parse(localStorage.getItem('refreshJWT')),
  }),

  actions: {
    addEigenaarToDefinitions(username) {
      this.definitions = this.definitions.map(item => {
        // Add the 'eigenaar' property to each object
        return {...item, eigenaar: username};
      })
    },

    addEigenaarToLabels(username) {
      this.labels = this.labels.map(item => {
        // Add the 'eigenaar' property to each object
        return {...item, eigenaar: username};
      })
    },

    async logout() {
      await this.RemoveTokens();

      this.user.loggedIn = false;

      localStorage.removeItem('isLoggedIn');
      localStorage.removeItem('tokenJWT');
      localStorage.removeItem('refreshJWT');
      localStorage.removeItem('username');
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

    async getXMLBronnenByNameTimeLine(artikelNaam) {
      let response = await this.genericGetRequests(`XMLBron/api/v1/timelinedata/${artikelNaam}`);
      this.xmlbronnen = response.data
    },

    async getXMLbronnenByName(artikelNaam) {
      let response = await this.genericGetRequests(`XMLBron/byName/${artikelNaam}`);
      this.xmlbronnen = response.data
    },

    async getDefinitions(xmlBronName, username, xmlbronDate) {
      let url = "define/getDefinitions";
      let response = await this.genericGetRequests(`${url}/${xmlBronName}/${username}/${xmlbronDate}`);
      this.definitions = response.data;
      this.addEigenaarToDefinitions(username);
    },

    async getDefinitionsForUser(xmlBronName, username, xmlbronDate) {
      let url = "define/getDefinitions";
      let response = await this.genericGetRequests(`${url}/${xmlBronName}/${username}/${xmlbronDate}`);
      this.definitions.push(response.data);
      this.addEigenaarToDefinitions(username);
    },

    async getLabels(xmlBronName, username, xmlbronDate) {
      let url = "label/getLabels";
      let response = await this.genericGetRequests(`${url}/${xmlBronName}/${username}/${xmlbronDate}`);

      this.labels = response.data;
      this.addEigenaarToLabels(username);
      return this.labels
    },

    async getLabelsForUser(xmlBronName, username, xmlbronDate) {
      let url = "label/getLabels";
      let response = await this.genericGetRequests(`${url}/${xmlBronName}/${username}/${xmlbronDate}`);

      this.labels.push(response.data);
      this.addEigenaarToLabels(username);
      return this.labels
    },

    async postDefinition(body, xmlBronName, username, xmlbronDate) {
      let url = "define/addDefinition";
      this.responseCode = await this.genericPostRequest(`${url}/${xmlBronName}/${username}/${xmlbronDate}`, body);
      await this.getDefinitions(xmlBronName, username, xmlbronDate);
    },

    async postLabel(body, xmlBronName, username, xmlbronDate) {
      let url = "label/addLabel";
      this.responseCode = await this.genericPostRequest(`${url}/${xmlBronName}/${username}/${xmlbronDate}`, body)
        .then((e) => this.getLabels(xmlBronName, username, xmlbronDate));
    },

    async postNewXMLBron(body) {
      this.responseCode = await this.genericPostRequest("XMLBron/api/v1/", body);
    },

    async login(body) {
      try {
        return await axios.post("http://localhost:8085/auth/login", body);
      } catch (error) {
        console.error("Error login in", error)
        throw error;
      }
    },

    async RemoveTokens() {
      try {
        const body = {
          accesToken: this.tokenJWT,
          refreshToken: this.refreshJWT,
        };

        await axios.post(`http://localhost:8085/auth/logout`, body);

      } catch (error) {
        console.error('Error removing tokens:', error);
      }
    },
  },
});

