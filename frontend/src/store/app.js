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
      "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1yZSIsImV4cCI6MTcwMjI1MjE2NiwiaWF0IjoxNzAyMjUxMjY2LCJyb2xlcyI6IlVTRVIifQ.rl4bvqS2wMpeTLxb5kBeD0_Bbl5LhSzW_wsNOx8QVFJU58UCAv-TivE-zcqubbftdmnNpwgfFkFFrAStWF_5R9XttmAs88Rs6dTTusMR1vYNGAHs1RyMgajqKrZPKO2AgerEZrxUoyuiiviFVCya0KteR3BxoaluXs6BmW0ulgQOWunLrG7q7XmWa5TpizQg6a9yNwDm7iJOYMbzeBUKKim41KcoWrcXUrQ12OQviTuWqu-jb_CJaBu8udCuVRz8KRBQpyrU_rCj_k4DiXVCkCqkfgPpz8BxmOkv1YWKNuB6yqqdCWTPCgKqvz_VtTtSv87K4fuw9rxilpEGdIfIQQ"
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

