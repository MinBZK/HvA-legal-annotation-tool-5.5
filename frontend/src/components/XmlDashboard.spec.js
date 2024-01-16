import XmlDashboard from "./XmlDashboard.vue";
//
// describe('XmlDashboard', () => {
//   test('test', () => {
//     expect(true).toBe(true)
//   })
// })


import { mount } from '@vue/test-utils';
import {it} from "vuetify/locale";
// import XmlDashboard from '@/path/to/YourComponent.vue';

describe('XmlDashboard', () => {
    it('renders without errors', () => {
        // Mount the component
        const wrapper = mount(XmlDashboard);

        // Assert that the component is rendered
        expect(wrapper.exists()).toBe(true);

        // Optionally, you can add more specific assertions based on your component's behavior
        // For example, if your component has specific elements or props, you can test them here
    });
});
