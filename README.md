# Url-Shortener


### About

Code task to write a URL-shortener. The projects use Spring Boot as Web framework and MongoDB as database.

### Requirements

- Maven
- Docker
- Java 17

### How to

Clone: https://github.com/KimAskebris/cabonline.git

Go to project and run in terminal: docker-compose -f docker-compose-mongo-only.yml up

Run the post curl and then copy the returned shorturl into browser


### Curl examples

```
curl -d "longUrl=https://www.dn.se" -X POST http://localhost:8080/url-shortener

```