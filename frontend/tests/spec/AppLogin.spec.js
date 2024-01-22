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

test('submits login form when all fields are filled correctly', async () => {
  const vuetify = createVuetify();
  const wrapper = mount(LoginComponent, {
    global: {
      plugins: [vuetify, createPinia()],
    },
  });

  // Set valid input values for the login form
  await wrapper.setData({
    username: 'emre',
    password: 'emre',
  });

  // Trigger form submission
  await wrapper.find('form').trigger('submit.prevent');

  // Wait for asynchronous tasks to complete
  await wrapper.vm.$nextTick();

  // Wait for 3 seconds (adjust as needed)
  await new Promise(resolve => setTimeout(resolve, 3000));

  // Assert the expected outcomes after successful login
  expect(wrapper.html().replace(/\s/g, '')).toContain('Inloggen succesvol');
  expect(wrapper.vm.loginSuccess).toBe(true);
  expect(wrapper.vm.loginSuccessMessage).toBe('Inloggen succesvol')
});






