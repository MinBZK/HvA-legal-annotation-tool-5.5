import { expect } from 'vitest';
import { mount } from '@vue/test-utils';
import LoginComponent from '@/components/AppLogin.vue';
import { createVuetify } from 'vuetify';

import { createPinia, setActivePinia } from 'pinia';


// Stub XMLSerializer to prevent interference with serialization
class StubbedXMLSerializer {
  serializeToString() {
    return '';
  }
}

global.XMLSerializer = StubbedXMLSerializer;

// Before each test
beforeEach(() => {
  const pinia = createPinia();
  setActivePinia(pinia);
});

const vuetify = createVuetify();

test('renders login component correctly', () => {

  const wrapper = mount(LoginComponent, {
    global: {
      plugins: [vuetify, createPinia()],
    },
  });

  expect(wrapper.html()).toMatchSnapshot();
});

test('initializes with correct data', () => {

  const wrapper = mount(LoginComponent, {
    global: {
      plugins: [vuetify, createPinia()],
    },
  });

  expect(wrapper.vm.username).toBe('');
  expect(wrapper.vm.password).toBe('');
  expect(wrapper.vm.loading).toBe(false);
  expect(wrapper.vm.errors.username).toEqual([]);
  expect(wrapper.vm.errors.password).toEqual([]);
  expect(wrapper.vm.loginError).toBe(false);
  expect(wrapper.vm.loginErrorMessage).toBe('');
  expect(wrapper.vm.loginSuccess).toBe(false);
  expect(wrapper.vm.loginSuccessMessage).toBe('');
});

test('validates form inputs correctly', async () => {

  const wrapper = mount(LoginComponent, {
    global: {
      plugins: [vuetify, createPinia()],
    },
  });

  await wrapper.setData({
    username: '',
    password: '',
  });

  await wrapper.find('form').trigger('submit.prevent');
  await wrapper.vm.$nextTick();

  expect(wrapper.vm.errors.username).toContain('Gebruikersnaam is verplicht.');
  expect(wrapper.vm.errors.password).toContain('Wachtwoord is verplicht.');
});

test('updates data on user input', async () => {

  const wrapper = mount(LoginComponent, {
    global: {
      plugins: [vuetify, createPinia()],
    },
  });

  // Simulate user input
  await wrapper.setData({
    username: 'testuser',
    password: 'testpassword',
  });

  // Assert that the component's data is updated
  expect(wrapper.vm.username).toBe('testuser');
  expect(wrapper.vm.password).toBe('testpassword');
});







