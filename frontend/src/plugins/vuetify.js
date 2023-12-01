// /**
//  * plugins/vuetify.js
//  *
//  * Framework documentation: https://vuetifyjs.com`
//  */
//
// // Styles
// import '@mdi/font/css/materialdesignicons.css'
// import 'vuetify/styles'
// import Vue from 'vue';
// import Vuetify from 'vuetify/lib/framework';
//
// Vue.use(Vuetify);
//
// // Composables
// import { createVuetify } from 'vuetify'
//
// // https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
// export default createVuetify({
//   theme: {
//     themes: {
//       light: {
//         colors: {
//           primary: '#1867C0',
//           secondary: '#5CBBF6',
//         },
//       },
//     },
//   },
// })
// plugins/vuetify.js

// plugins/vuetify.js

// Other imports...

// Change the import statement to use a named import
import { createApp } from 'vue';
import Vuetify from 'vuetify/lib/framework';

const app = createApp();
app.use(Vuetify);

// Other configurations...

export default createVuetify({
  theme: {
    themes: {
      light: {
        colors: {
          primary: '#1867C0',
          secondary: '#5CBBF6',
        },
      },
    },
  },
});
