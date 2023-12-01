// vue.config.js
module.exports = {
    // Configuration options...

    // Example: Set the publicPath for production
    publicPath: process.env.NODE_ENV === 'production' ? '/legal-annotation-tool-hva/' : '/',


    // Example: Configure devServer
    devServer: {
        disableHostCheck: true,
    },


};
