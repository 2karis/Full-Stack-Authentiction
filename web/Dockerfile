FROM node:24-slim

WORKDIR /web

COPY package.json package-lock.json ./

RUN npm install

RUN npm ci

COPY . .

RUN rm /web/src/environments/environment.development.ts

COPY config/environment.development.ts /web/src/environments


CMD ["npm", "start"]
