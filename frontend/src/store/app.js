// Utilities
import {defineStore} from 'pinia'

export const store = defineStore('app', {
  state: () => ({
    definitions: [],
    user: {logged: "", permissions: ""},
  }),
})

