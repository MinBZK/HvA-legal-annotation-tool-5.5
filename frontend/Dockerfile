#FROM node:lts-alpine
#WORKDIR /app
#COPY package.json ./
#RUN  npm install
#EXPOSE 3001
#CMD ["npm", "run", "serve"]
#
#
## docker-composer up # to start the compilation process
## to install npm package run
## docker-compose exec web npm i bootstrap

# Use a node image that includes Python
#FROM node:14-alpine
#
## Set the working directory
#WORKDIR /app
#
## Copy package.json to the working directory
#COPY package.json ./
#
## Install required system dependencies (Python, make, g++)
#RUN apk add --no-cache python3 make g++
#
## Set npm configuration to use Python 3
#RUN npm config set python python3
#
## Install npm packages
#RUN npm install
#
## Expose the port used by your application
#EXPOSE 8000
#
## Specify the default command to run on container startup
#CMD ["npm", "run", "dev"]
#
#FROM node:14-alpine
#
## Set the working directory
#WORKDIR /app
#
## Copy package.json to the working directory
#COPY package.json ./
#
## Install required system dependencies (Python, make, g++)
#RUN apk add --no-cache python3 make g++
#
## Set npm configuration to use Python 3
#RUN npm config set python python3
#
## Install npm packages
#RUN npm install
#
## Expose the port used by your application
#EXPOSE 8000
#
## Specify the default command to run on container startup
#CMD ["npm", "run", "dev"]
#

FROM node:14

# Set the working directory
WORKDIR /app

# Copy package.json to the working directory
COPY package.json ./

# Install required system dependencies (Python, make, g++)
RUN apt-get update && apt-get install -y python3 make g++

# Set npm configuration to use Python 3
RUN npm config set python python3

# Install npm packages
RUN npm install

# Expose the port used by your application
EXPOSE 8000

# Specify the default command to run on container startup
CMD ["npm", "run", "dev"]
