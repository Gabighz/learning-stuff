# Specify a base image
FROM node:alpine

# Specify working directory inside container
# Any further command will be executed relative to this path
WORKDIR /usr/app

# Path to folder to copy from your machine relative to the build context
# ---> place to copy stuff to inside the container
# Currently just this in order to minimize cache busting and rebuilds
COPY ./package.json ./

# Install dependencies
RUN npm install

# Copy everything else
# We'll be rebuilding from here given changes to the app,
# For everything above this, we'll be using the cached intermediary containers
COPY ./ ./

# Default command for container startup
CMD ["npm", "start"]