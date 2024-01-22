import { test, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import XmlDownloader from '@/components/XmlDownloader.vue'; // Update the path accordingly
import { createVuetify } from 'vuetify';
import { createPinia } from 'pinia';

// Stub XMLSerializer to prevent interference with serialization
class StubbedXMLSerializer {
  serializeToString() {
    return '';
  }
}

global.XMLSerializer = StubbedXMLSerializer;


const vuetify = createVuetify();

test('renders XmlDownloader component correctly', () => {
  const wrapper = mount(XmlDownloader, {
    global: {
      plugins: [vuetify, createPinia()],
    },
    props: {
      xmlContent: '<sampleXML></sampleXML>',
      defaultFileName: 'default.xml',
      xmlFile: { name: 'sample.xml' },
    },
  });

  expect(wrapper.html()).toMatchSnapshot();
});

test('initializes with correct data', () => {
  const wrapper = mount(XmlDownloader, {
    global: {
      plugins: [vuetify, createPinia()],
    },
    props: {
      xmlContent: '<sampleXML></sampleXML>',
      defaultFileName: 'default.xml',
      xmlFile: { name: 'sample.xml' },
    },
  });

  expect(wrapper.vm.enteredFileName).toBe('default.xml');
});

test('updates enteredFileName when xmlFile changes', async () => {
  const wrapper = mount(XmlDownloader, {
    global: {
      plugins: [vuetify, createPinia()],
    },
    props: {
      xmlContent: '<sampleXML></sampleXML>',
      defaultFileName: 'default.xml',
      xmlFile: { name: 'sample.xml' },
    },
  });

  // Simulate xmlFile change
  await wrapper.setProps({
    xmlFile: { name: 'updated.xml' },
  });

  // Assert that enteredFileName is updated
  expect(wrapper.vm.enteredFileName).toBe('updated.xml');
});

test('downloads XML with entered file name', async () => {
  const wrapper = mount(XmlDownloader, {
    global: {
      plugins: [vuetify, createPinia()],
    },
    props: {
      xmlContent: '<sampleXML></sampleXML>',
      defaultFileName: 'default.xml',
      xmlFile: { name: 'sample.xml' },
    },
  });

  // Simulate user input
  await wrapper.setData({
    enteredFileName: 'custom.xml',
  });

  // Trigger XML download
  await wrapper.vm.downloadXMLWithFileName();

  // Assert that the download method is called with the correct file name

});
