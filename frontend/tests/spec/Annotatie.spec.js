import * as components from 'vuetify/lib/components/index.mjs'
import * as directives from 'vuetify/lib/directives/index.mjs'

// XmlDashboard.test.js
import {expect} from 'vitest';
import {mount} from '@vue/test-utils';
import Annotatie from '@/components/Annotatie.vue';
import {createVuetify} from "vuetify/dist/vuetify";

import {createPinia} from 'pinia';

const vuetify = createVuetify({
  components,
  directives,
})

global.ResizeObserver = require('resize-observer-polyfill')

describe('AnnotatieDialog', () => {
  it('updates tab on tab change', async () => {
    const wrapper = mount(Annotatie, {
      props: {
        isVisible: true,
        olderLabelsList: "",
        selectedText: "gemeenteraad",
        allWordsInXML: [
          {number: 0, name: "De"},
          {number: 1, name: "gemeenteraad"}
        ],
        hoveredWordObject: {
          number: 1,
          name: "gemeenteraad"
        }
      },
      global: {
        plugins: [vuetify, createPinia()],
      },
    });

    // Trigger tab change
    await wrapper.findAllComponents({name: 'v-tab'})[1].trigger('click');

    // Assertion
    expect(wrapper.vm.tab).toBe('two');
  });
});

