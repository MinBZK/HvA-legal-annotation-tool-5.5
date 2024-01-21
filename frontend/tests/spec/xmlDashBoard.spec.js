import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

// XmlDashboard.test.js
import {expect} from 'vitest';
import {mount} from '@vue/test-utils';
import XmlDashboard from '@/components/XmlDashboard.vue';
import {createVuetify} from "vuetify/dist/vuetify";

import {createPinia, setActivePinia} from 'pinia';

// Before each test
beforeEach(() => {
  const pinia = createPinia();
  setActivePinia(pinia);
});

const vuetify = createVuetify({
  components,
  directives,
})

global.ResizeObserver = require('resize-observer-polyfill')

test('displays message', () => {
  const wrapper = mount(XmlDashboard, {
    global: {
      plugins: [vuetify, createPinia()],
    }
  });

  // Assert the rendered text of the component
  expect(wrapper.html()).toContain('XML-bestanden Beheren');
})
