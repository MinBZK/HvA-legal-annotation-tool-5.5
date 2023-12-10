// Utilities
import {defineStore} from 'pinia'
import axios from "axios";
import axiosInterceptor from "@/axios-request/axios-interceptor";

export const store = defineStore('app', {
    state: () => ({
      definitions: [],
      loadedXMLIdentifier: "",
      user: {logged: true, permissions: ""},
      tokenJWT: "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJzZWxmIiwic3ViIjoiZW1yZSIsImlhdCI6MTcwMjIzNjgwNCwicm9sZXMiOiJVU0VSIn0.xNgOmjPVcD_6iP1IpFE4kG-YI4mJefdDCIkOT7EIKt1ga01XgQS6NuGXQDvgtVzR-2nrOoD-0DBCtdBAwKlXKXDwt6dQ4qIAY5K84aBT-cC6sBNTQUYL03U84RH4OvVmlGAFXZUJIERLMoHlfV5yWGAyxTVKT15AXfjKa5wUr_IbQs-DSsdcFOqsWhFxTE8m618N2YG52faz_tX-41WRCcEfS6UjD2CzGJfeCSIH8crnF1K0ItqGTl0l53uCZp3tCYuMh3XqeRlS4UcyetGPYonJcQAR5lhmACGCdyWl2xucIop-i_FVgkeuMRPUyqD90cWB2b_wxPLJSRpPs29tmQ"
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

      async postNewXML(body) {
        this.responseCode = await this.genericPostRequest("bronDefinitie/add", body);
      },

    },
  })
;

