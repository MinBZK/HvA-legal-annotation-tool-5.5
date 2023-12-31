import { defineStore } from "pinia";


export const xmlBronStore = defineStore('xmlbron', {
    state: () => ({
        xmlbronnen: [
            {id: 1, name: "Mock data title 1", date: '2023-01-01'},
            {id: 2, name: "Mock data title 2", date: '2023-01-02'},
            {id: 3, name: "Mock data title 3", date: '2023-01-02'},
        ],
        loaded: false,
    }),
    actions: {
        async fetchDataFromApi(){
            await new Promise(resolve => setTimeout(resolve, 1000));
        }
    }
})