import { test, expect } from 'vitest';
import { mount } from '@vue/test-utils';
import { createVuetify } from 'vuetify';
import { createPinia } from 'pinia';
import XmlDownloader from '@/components/XmlDownloader.vue'; // Update the path accordingly

// Stub XMLSerializer to prevent interference with serialization
class StubbedXMLSerializer {
  serializeToString() {
    return '';
  }
}

global.XMLSerializer = StubbedXMLSerializer;

const vuetify = createVuetify();

const mountXmlDownloader = () => {
  return mount(XmlDownloader, {
    global: {
      plugins: [vuetify, createPinia()],
    },
    props: {
      xmlContent: '<sampleXML></sampleXML>',
      defaultFileName: 'default.xml',
      xmlFile: { name: 'sample.xml' },
    },
  });
};

test('renders XmlDownloader component correctly', () => {
  const wrapper = mountXmlDownloader();
  expect(wrapper.html()).toMatchSnapshot();
});

describe('XmlDownloader component', () => {
  test('initializes with correct data', () => {
    const wrapper = mountXmlDownloader();
    expect(wrapper.vm.enteredFileName).toBe('default.xml');
  });

  test('updates enteredFileName when xmlFile changes', async () => {
    const wrapper = mountXmlDownloader();

    // Simulate xmlFile change
    await wrapper.setProps({
      xmlFile: { name: 'updated.xml' },
    });

    // Assert that enteredFileName is updated
    expect(wrapper.vm.enteredFileName).toBe('updated.xml');
  });

});
