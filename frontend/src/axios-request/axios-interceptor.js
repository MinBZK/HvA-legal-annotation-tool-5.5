import axios from "axios";
import axiosAuth from "@/axios-request/axios-auth";
import { store } from "@/store/app";

let isRefreshing = false; // Flag to track renewal request status
let refreshSubscribers = []; // Array to hold pending requests that need the new token

const instance = axios.create({
  baseURL: "http://localhost:8085/",
  headers: {
    withCredentials: true,
  },
});

instance.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    if (error.response.status === 401) {
      // Check if renewal request is already in progress
      if (!isRefreshing) {
        isRefreshing = true;

        try {
          console.log("Refreshing token...");
          let response = await axiosAuth({
            method: 'post',
            url: "http://localhost:8085/token/refresh",
            data: {
              expiredAccessToken: store().tokenJWT,
              refreshToken: store().refreshJWT,
            },
          });

          console.log("Token refresh response:", response);

          // Update the token and clear the flag
          store().tokenJWT = response.data.accessToken;
          localStorage.setItem("tokenJWT", JSON.stringify(store().tokenJWT));
          isRefreshing = false;
          console.log("Refreshed Token: " + response.data.accessToken);

          // Retry the original request with the new token
          error.config.headers.Authorization = `Bearer ${store().tokenJWT}`;
          return instance(error.config);
        } catch (renewalError) {
          console.error("Error refreshing token:", renewalError);
          isRefreshing = false;
          await store().logout();
          return Promise.reject(renewalError);
        }
      }

      // If renewal is in progress, add the request to subscribers
      return new Promise((resolve) => {
        refreshSubscribers.push(() => {
          error.config.headers.Authorization = `Bearer ${store().tokenJWT}`;
          resolve(instance(error.config));
        });
      });
    }

    // Handle other error scenarios
    return Promise.reject(error);
  }
);

instance.interceptors.request.use(
  (config) => {
    config.headers.common = {
      Authorization: `Bearer ${store().tokenJWT}`,
    };
    return config;
  },
  (error) => {
    // Do something with request error
    return Promise.reject(error);
  }
);

export default instance;

