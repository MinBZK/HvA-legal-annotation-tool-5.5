// Utilities
import {defineStore} from 'pinia'
import axios from "axios";
import axiosInterceptor from "@/axios-request/axios-interceptor";

export const store = defineStore('app', {
    state: () => ({
      definitions: [],
      user: {logged: true, permissions: ""},
      tokenJWT: "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1yZXNzIiwiaWF0IjoxNzAyMTE5OTU2LCJyb2xlcyI6IlVTRVIifQ.RLnatQ4Ss-Z1wUCRSgomXtXR2SPoo5sNNcpQicEpjyA3k9neyagzdtQNkqonOH-D1vMSr5o0XmpcjaSLcebDQyiqPWq5txWZTjxZ98oFC8xosHf0GIF2cF3n0NhyXN6IAxvtvi6K1K8AycwCd5I7IlH0DlxLxJDyJTQohhfpLjwLQJIDpVeEtwtYUDOMtFhNtinyZLQ4kA7ykJTrPVDDkTbndYVJl9XKiSA90YEXvm7d501-C7Aej7kChIAs1AvoMcO_u0OCx4Ill561GrVR7B_nKcp69twWfzruM7wSf75t_b2wbyS9_cat7mt_cxNTVesHpyp2aUu44Ie1o5CWFg",
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

      async getDefinitions() {
        const response = await this.genericGetRequests("define");
        return response.data;
      },

      async postDefinition(body) {
        this.responseCode = await this.genericPostRequest("define/addDefinition", body);
      },

    },
  })
;

