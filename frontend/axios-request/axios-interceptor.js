import axios from "axios";
import {userStore} from "@/store";
import axiosAuth from "@/axios-request/axios-auth";
import VueCookies from "vue-cookies";

let isRefreshing = false; // Flag to track renewal request status
let refreshSubscribers = []; // Array to hold pending requests that need the new token

const instance = axios.create({
    baseURL: import.meta.env.VITE_API_BASE_URL,
    headers: {
        withCredentials: true,
    },
});

instance.interceptors.response.use((response) => {
        return response;
    }, async (error) => {
        if (error.response.status === 401) {
            // Check if renewal request is already in progress
            if (!isRefreshing) {
                isRefreshing = true;

                try {
                    let response = await axiosAuth(
                        {
                            method: 'post',
                            url: '/token/renew',
                            headers: {'Authorization': `Bearer ${userStore().tokenJWT}`},
                        });

// Update the token and clear the flag
                    VueCookies.set('Access-Token', response.data, "7d");
                    isRefreshing = false;

                    refreshSubscribers.forEach((subscriber) => subscriber());
                    refreshSubscribers = [];

                } catch (renewalError) {
                    isRefreshing = false;
                    userStore().logout();
                }
            }

// If renewal is in progress, add the request to subscribers
            return new Promise((resolve) => {
                refreshSubscribers.push(() => {
                    resolve(instance(error.config));
                });
            });
        }
// Handle error scenarios
        return Promise.reject(error);
    }
);


instance.interceptors.request.use(
    config => {
        config.headers.common = {
            Authorization: `Bearer ${userStore().tokenJWT}`,
        };
        return config;
    }, error => {
        // Do something with request error
        return Promise.reject(error);
    });

export default instance;

