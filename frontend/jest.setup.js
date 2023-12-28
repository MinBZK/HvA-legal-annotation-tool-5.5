// jest.setup.js
Object.defineProperty(process, 'mainModule', {
    value: {
        filename: __filename,
    },
});
