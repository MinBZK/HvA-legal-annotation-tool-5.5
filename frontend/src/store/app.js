// Utilities
import {defineStore} from 'pinia'
import axios from "axios";
import axiosInterceptor from "@/axios-request/axios-interceptor";

export const store = defineStore('app', {
  state: () => ({
    definitions: [],
    loadedXMLIdentifier: "",
    user: {logged: true, permissions: ""},
    tokenJWT:
      "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1yZSIsImV4cCI6MTcwMjI4OTM4NCwiaWF0IjoxNzAyMjg4NDg0LCJyb2xlcyI6IlVTRVIifQ.f_hnGE1YgidAF66u2iwTNpU-ZZG0-WmbMOJoOiSdaHtuWibWQikxuyZYkQsrBMa98t-wwhJEp0qJuNTmkEbCWKBrXcRqTgo8NK8rZJuEO8LX8sHmSjev9lfJH07it5Nrkm15eqZlY43nPqd7scZ-UysgpkPADmGEHI45veg_L0bRgpPTEs7pMJZtl3uQNw76H-Wp7IcZ9W9w1RPV_Eo0ARL1e74fTEjMS8X9ArNXxm0UALmlHwEmnKjgg7bt6WWFj-ue4Jk3E7wk6xJMpzOwHwqhmOPn4ocKe751KB6E-QDd3UVCQ5YjqicRssPbVRvpW47rdT3pP0-CPi4x-THxXA"

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

