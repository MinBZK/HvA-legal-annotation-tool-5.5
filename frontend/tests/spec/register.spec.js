import { expect } from 'vitest';
import { mount } from '@vue/test-utils';
import Register from '@/components/Register.vue';
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


test('fails to register with empty fields', async () => {
    const wrapper = mount(Register, {
        global: {
            plugins: [vuetify, createPinia()],
        },
    });

    // Set input values with one or more empty fields
    await wrapper.setData({
        firstName: 'John',
        lastName: 'Doe',
        username: 'johndoe',
        email: '',  // Set email field to be empty
        password: 'password123',
    });

    // Trigger form submission
    await wrapper.find('form').trigger('submit.prevent');

    // Wait for asynchronous tasks to complete
    await wrapper.vm.$nextTick();

    // Assert that the registration failed due to empty fields
    expect(wrapper.html()).toContain('Vul alle gegevens in');
    expect(wrapper.vm.registrationStatus).toEqual('error');
    expect(wrapper.vm.registrationMessage).toEqual('Vul alle gegevens in');
});

test('displays registration form with proper labels and fields', () => {
    const wrapper = mount(Register, {
        global: {
            plugins: [vuetify, createPinia()],
        },
    });

    // Assert the rendered text of the component
    expect(wrapper.html()).toContain('Voornaam');
    expect(wrapper.html()).toContain('Achternaam');
    expect(wrapper.html()).toContain('Gebruikersnaam');
    expect(wrapper.html()).toContain('Email');
    expect(wrapper.html()).toContain('Wachtwoord');
    expect(wrapper.html()).toContain('Registreer');
});

test('binds form fields to component data', async () => {
    const wrapper = mount(Register, {
        global: {
            plugins: [vuetify, createPinia()],
        },
    });

    // Set input values
    await wrapper.setData({
        firstName: 'John',
        lastName: 'Doe',
        username: 'johndoe',
        email: 'john.doe@example.com',
        password: 'password123',
    });

    // Assert the values are correctly bound to the component's data
    expect(wrapper.vm.firstName).toEqual('John');
    expect(wrapper.vm.lastName).toEqual('Doe');
    expect(wrapper.vm.username).toEqual('johndoe');
    expect(wrapper.vm.email).toEqual('john.doe@example.com');
    expect(wrapper.vm.password).toEqual('password123');
});






test('submits form when all fields are filled correctly', async () => {
    const wrapper = mount(Register, {
        global: {
            plugins: [vuetify, createPinia()],
        },
    });

    // Generate a random unique username and email
    const randomUsername = `user_${Math.floor(Math.random() * 1000000)}`;
    const randomEmail = `user_${Math.floor(Math.random() * 1000000)}@example.com`;

    // Set valid input values with the random username and email
    await wrapper.setData({
        firstName: 'test',
        lastName: 'test',
        username: randomUsername,
        email: randomEmail,
        password: 'password123',
    });

    // Trigger form submission
    await wrapper.find('form').trigger('submit.prevent');

    // Wait for asynchronous tasks to complete
    await wrapper.vm.$nextTick();

    // Assert that the registration method was called


    // Wait for 3 seconds (adjust as needed)
    await new Promise(resolve => setTimeout(resolve, 3000));

    expect(wrapper.html()).toContain('Registratie is gelukt');
    expect(wrapper.vm.registrationStatus).to.equal('success');
    expect(wrapper.vm.registrationMessage).to.equal('Registratie is gelukt');
});

test('fails to register with already used username', async () => {
    const wrapper = mount(Register, {
        global: {
            plugins: [vuetify, createPinia()],
        },
    });

    // Set input values with an already used username
    await wrapper.setData({
        firstName: 'John',
        lastName: 'Doe',
        username: 'admin', // Assuming 'admin' is already in use
        email: 'john.doe@example.com',
        password: 'password123',
    });

    // Trigger form submission
    await wrapper.find('form').trigger('submit.prevent');

    // Wait for asynchronous tasks to complete
    await wrapper.vm.$nextTick();

    // Wait for 3 seconds (adjust as needed)
    await new Promise(resolve => setTimeout(resolve, 3000));

    // Assert that the registration failed due to the already used username
    expect(wrapper.html()).toContain('Email of gebruikersnaam al in gebruik');
    expect(wrapper.vm.registrationStatus).toEqual('error');
    expect(wrapper.vm.registrationMessage).toEqual('Email of gebruikersnaam al in gebruik');
});






