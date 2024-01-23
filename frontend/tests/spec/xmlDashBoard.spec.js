import * as components from 'vuetify/lib/components/index.mjs'
import * as directives from 'vuetify/lib/directives/index.mjs'

// XmlDashboard.test.js
import {expect} from 'vitest';
import {mount} from '@vue/test-utils';
import XmlDashboard from '@/components/XmlDashboard.vue';
import {createVuetify} from "vuetify/dist/vuetify";

import {createPinia} from 'pinia';

// XML content as a string
const xmlContent = `<artikel xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" id="bwbr0015703/2022-04-01/1/wet/hoofdstuk1/paragraaf1.2/artikel8">
  <kop label="Artikel" nr="8" titel="Verordeningen uitkeringen">Artikel 8 Verordeningen uitkeringen</kop>
  <lid>
    <lidnr>1</lidnr>
    <al>De gemeenteraad stelt bij verordening regels met betrekking tot:</al>
    <lijst>
      <li>
        <li.nr>a.</li.nr>
        <al>het verlagen van de bijstand, bedoeld in artikel 18, tweede lid en de periode van de verlaging van de bijstand, bedoeld in artikel 18, vijfde en zesde lid;</al>
      </li>
      <li>
        <li.nr>b.</li.nr>
        <al>het verlenen van een individuele inkomenstoeslag als bedoeld in artikel 36;</al>
      </li>
      <li>
        <li.nr>c.</li.nr>
        <al>het verlagen van de bijstand, bedoeld in artikel 9a, twaalfde lid.</al>
      </li>
    </lijst>
  </lid>
  <lid>
    <lidnr>2</lidnr>
    <al>De regels, bedoeld in het eerste lid, hebben voor zover het gaat om het eerste lid, onderdeel b, in ieder geval betrekking op de hoogte van de individuele inkomenstoeslag en de wijze waarop invulling wordt gegeven aan de begrippen langdurig en laag inkomen.</al>
  </lid>
</artikel>`;

// Create a Blob from the XML content
const blob = new Blob([xmlContent], {type: 'text/xml'});

// Convert the Blob to a File
const xmlBlobFile = new File([blob], 'PW - artikel8 1.xml', {type: 'text/xml'});

// Check the file content
const reader = new FileReader();
reader.onload = function (e) {
  //console.log('File content:', e.target.result); // This should log your XML content
};
reader.readAsText(xmlBlobFile);

const vuetify = createVuetify({
  components,
  directives,
})

global.ResizeObserver = require('resize-observer-polyfill')

// Load in XML-File and check if text is generated
describe('XmlDashboard', () => {

  it('loads XML file', async () => {
    const wrapper = mount(XmlDashboard, {
      global: {
        plugins: [vuetify, createPinia()],
      },
    });

    // Reads xmlFile in
    function readFileContent(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = (event) => resolve(event.target.result);
        reader.onerror = (error) => reject(error);
        reader.readAsText(file);
      });
    }

    // Read the XML file content
    const fileContent = await readFileContent(xmlBlobFile);

    // invoke method that handles xmlFileContent
    await wrapper.vm.processXMLContent(fileContent);

    // Wait for any asynchronous updates
    await wrapper.vm.$nextTick();

    // Check if the specific text is present in the rendered output
    const textPresent = wrapper.text().includes('De gemeenteraad stelt bij verordening regels met betrekking tot:');
    expect(textPresent).toBe(true);

    const textNotPresent = wrapper.text().includes('Geen XML bestand geladen');
    expect(textNotPresent).toBe(false);
  });

  it('shows definition tooltip on hover', async () => {
    // Setup your component with necessary data and state
    const wrapper = mount(XmlDashboard, {
      props: {
        hoveredWordObject: {
          number: 1,
          name: "gemeenteraad"
        },
        definitions: [
          {
            id: 2902,
            woord: "gemeenteraad",
            definitie: "test",
            positie_start: 1,
            positie_end: 1,
            date: "2024-01-22T12:36:32.406",
            username: "emre"
          },
        ]
      },
      global: {
        plugins: [vuetify, createPinia()],
      },
    });

    function readFileContent(file) {
      return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.onload = (event) => resolve(event.target.result);
        reader.onerror = (error) => reject(error);
        reader.readAsText(file);
      });
    }

    // Read the XML file content
    const fileContent = await readFileContent(xmlBlobFile);

    // invoke method that handles xmlFileContent
    await wrapper.vm.processXMLContent(fileContent);

    // Wait for the component to update
    await wrapper.vm.$nextTick();

    // Find all span elements
    const spans = wrapper.findAll('span');

    // Filter to find the span that contains the word "gemeenteraad"
    const gemeenteraadSpan = spans.filter(span => span.text().includes("gemeenteraad")).at(0);

    await gemeenteraadSpan.trigger('mouseover');

    // simulate mouseleave and check if the tooltip should be hidden
    await gemeenteraadSpan.trigger('mouseleave');
    await wrapper.vm.$nextTick();
    expect(wrapper.vm.showTooltip).toBe(false);
  })
});

// Test if page is properly loaded
test('displays message', () => {
  const wrapper = mount(XmlDashboard, {
    global: {
      plugins: [vuetify, createPinia()],
    }
  });

  // Assert the rendered text of the component
  expect(wrapper.html()).toContain('XML-bestanden Beheren');
})


