## Tech Stack
Java 21, Maven, TestNG, Rest Assured, Allure, Log4j2, Apache POI

## How To Run Tests
mvn clean test

## How To Run With Environment Override
mvn clean test -Dbase_url=https://petstore.swagger.io/v2

## Generate Allure Report
allure serve target/allure-results

## Project Structure
config - configuration reader
clients - API clients and routes
models - request/response POJOs
tests - TestNG tests
utils - data, logging, reporting helpers
assertions - reusable API assertions