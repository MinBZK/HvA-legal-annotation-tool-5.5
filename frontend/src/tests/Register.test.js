import { mount, createLocalVue } from '@vue/test-utils';
import Vuetify from 'vuetify';
import YourComponent from '@/components/YourComponent.vue';
import Register from "@/components/Register.vue"; // Adjust the import path based on your project structure

const localVue = createLocalVue();
localVue.use(Vuetify);

describe('YourComponent.vue', () => {
  let vuetify;

  beforeEach(() => {
    vuetify = new Vuetify();
  });

  it('renders the component correctly', () => {
    const wrapper = mount(Register, {
      localVue,
      vuetify,
    });

    // Assert that the component is rendered
    expect(wrapper.exists()).toBe(true);

    // You can add more specific assertions based on your component's behavior
    // For example, you can test the presence of form fields, buttons, etc.
    expect(wrapper.find('v-text-field[name="firstName"]').exists()).toBe(true);
    expect(wrapper.find('v-text-field[name="lastName"]').exists()).toBe(true);
    expect(wrapper.find('v-text-field[name="username"]').exists()).toBe(true);
    expect(wrapper.find('v-text-field[name="email"]').exists()).toBe(true);
    expect(wrapper.find('v-text-field[name="password"]').exists()).toBe(true);
    expect(wrapper.find('v-btn').exists()).toBe(true);
  });

  it('registers a user when the form is submitted', async () => {
    const wrapper = mount(YourComponent, {
      localVue,
      vuetify,
    });

    // Mock the axios post method
    const mockPost = jest.fn(() => Promise.resolve({ data: 'Registration successful' }));
    wrapper.vm.$axios = { post: mockPost };

    // Set data for the form fields
    wrapper.setData({
      firstName: 'John',
      lastName: 'Doe',
      username: 'johndoe',
      email: 'john.doe@example.com',
      password: 'password123',
    });

    // Trigger form submission
    await wrapper.find('form').trigger('submit.prevent');

    // Assert that the axios post method was called with the correct data
    expect(mockPost).toHaveBeenCalledWith('YOUR_BACKEND_API_URL/register', {
      firstName: 'John',
      lastName: 'Doe',
      username: 'johndoe',
      email: 'john.doe@example.com',
      password: 'password123',
    });
  });
});
