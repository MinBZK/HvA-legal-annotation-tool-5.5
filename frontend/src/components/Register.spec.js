import { render, fireEvent } from '@testing-library/vue';
import RegisterForm from '@/path-to-your-component/RegisterForm.vue';
import axios from 'axios';
import MockAdapter from 'jest-axios-mock';

describe('RegisterForm.vue', () => {
    let mock;

    beforeEach(() => {
        mock = new MockAdapter(axios);
    });

    afterEach(() => {
        mock.restore();
    });

    test('registers user successfully', async () => {
        // Mock the successful response from the server
        mock.onPost('http://localhost:8085/auth/register').reply(200, {});

        // Render the component
        const { getByLabelText, getByText } = render(RegisterForm);

        // Fill out the form
        await fireEvent.update(getByLabelText('Voornaam'), 'John');
        await fireEvent.update(getByLabelText('Achternaam'), 'Doe');
        await fireEvent.update(getByLabelText('Gebruikersnaam'), 'johndoe');
        await fireEvent.update(getByLabelText('Email'), 'john.doe@example.com');
        await fireEvent.update(getByLabelText('Wachtwoord'), 'securepassword');

        // Submit the form
        await fireEvent.click(getByText('Registreer'));

        // Check if the success message is displayed
        expect(getByText('Registratie is gelukt')).toBeInTheDocument();
    });

    test('handles registration error', async () => {
        // Mock an error response from the server
        mock.onPost('http://localhost:8085/auth/register').reply(400, {});

        // Render the component
        const { getByLabelText, getByText } = render(RegisterForm);

        // Fill out the form
        await fireEvent.update(getByLabelText('Voornaam'), 'John');
        await fireEvent.update(getByLabelText('Achternaam'), 'Doe');
        await fireEvent.update(getByLabelText('Gebruikersnaam'), 'johndoe');
        await fireEvent.update(getByLabelText('Email'), 'john.doe@example.com');
        await fireEvent.update(getByLabelText('Wachtwoord'), 'insecurepassword');

        // Submit the form
        await fireEvent.click(getByText('Registreer'));

        // Check if the error message is displayed
        expect(getByText('Email of gebruikersnaam al in gebruik')).toBeInTheDocument();
    });
});
