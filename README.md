# Stocks service
 
## Introduction 
This application is used to showcase my back end development skills for any given company.
The application is an REST API meant for storing and retrieving stock prices.

## Assignment

The given assigment that made me create this application is shown in the  [description fo the assignment](assignment.md) 


## Endpoints
Please refer to the following swagger document to find the details about endpoints exposed by this service. 
 - [Swagger - API Stocks Service](http://localhost:8080/)
## Application status and code quality
d

Technical dependencies
 - 
 - Maven
 - Spring boot
 - Testing - JUnit 5 is used for unit testing and Cucumber is used for integration testing.
 
## Build and Test
 - Use mvn clean package to build the application and run unit tests.
 - Use mvn clean install -Pcucumber to run the cucumber integration tests.

 ## Assumptions.
 To create the application I had to make some assumptions regarding the application requested. 
 
 1. Stock prices are depicted as 3 numbers after the decimal e.g. €3,000
 2. Stock prices traded only in the Euro currency.
 3. Dutch formatting of currency should be used (optional for the possible frontend) e.g. € 32.230,23
 3. The assignment states that the list of stocks should be loaded into memory after startup. I assumed that this means that I have to use an inmemory database.
 4. The PUT command to specify an new entry to a Stock is not changing previous entries. In a proper REST api this would be defined as a POST rather than a PUT. See link 6.
 5. Id is defined as a number and this is a must have requirement. I would personally rather use UUID's. I also assumed that the db/ORM should generate the id.
 ## Sources
 
 An list of documentation I used to build this application.
  1. https://www.beurs.nl/koersen/amx
  2. https://lemnik.wordpress.com/2011/03/25/bigdecimal-and-your-money/
  3. https://stackoverflow.com/questions/285680/representing-monetary-values-in-java
  4. https://restfulapi.net/rest-put-vs-post/
  5. https://jsao.io/2018/04/creating-a-rest-api-handling-post-put-and-delete-requests/
  6. https://stackoverflow.com/questions/19732423/why-isnt-http-put-allowed-to-do-partial-updates-in-a-rest-api