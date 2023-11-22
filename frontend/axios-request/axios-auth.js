import axios from 'axios';

const instance = axios.create({
    baseURL: import.meta.env.VITE_LOGIN_API_BASE_URL,
    headers: {
        Authorization: import.meta.env.VITE_API_KEY,
        'Relay-Authorization-Type': 'ldap',
        withCredentials: true,
    },
});

export default instance;

