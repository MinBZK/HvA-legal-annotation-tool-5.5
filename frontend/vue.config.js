// vue.config.js
module.exports = {
    // Configuration options...

    // Example: Set the publicPath for production
    publicPath: process.env.NODE_ENV === 'production' ? '/legal-annotation-tool-hva/' : '/',


    // Example: Configure devServer
    devServer: {
        allowedHosts: "all",
        host: '0.0.0.0', // Allow connections from outside the container
        port: 3000
    },


};
