## Key Features
- Reusable API clients
- Centralized configuration
- Environment override using Maven system properties
- Data-driven testing using Excel
- Random test data using Faker
- Reusable assertions
- Allure reporting
- Log4j2 logging
- TestNG groups and suite execution
- JSON schema validation

## Run Tests
mvn clean test | 
mvn clean test -DsuiteXmlFile=testng-smoke.xml |
mvn clean test -DsuiteXmlFile=testng-regression.xml |

## Override Base URL
mvn clean test -Dbase_url=https://petstore.swagger.io/v2

## View Allure Report
allure serve target/allure-results

## Current Limitations
- Demo uses public Swagger Petstore API
- Public API data may be unstable
- Some tests depend on create/get/delete flow
- Excel is suitable for demo but JSON/DB-driven data may be better for large systems

## Future Improvements
- CI pipeline with GitHub Actions or Jenkins
- Retry analyzer for transient failures
- Environment-specific config files
- Better test data cleanup strategy
- Contract testing
- Authentication support