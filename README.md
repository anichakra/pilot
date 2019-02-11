# Introduction
PILOT is the name of the PoC application. This project demonstrate how to create a microservice using [spring-boot](https://projects.spring.io/spring-boot/). This microservice has ReST based controllers that provides basic CRUD operations and persists data into postgres. This project also shows how to write unit test cases and generate code coverage report.

# Prerequisite
Following are the softwares to be installed to run the project:
* Require JDK1.8 or higher
* Maven 3.5.3 or higher
* Postgresql 9.5 or higher (if postgres is not available change pilot-vehicle-service/pom.xml accordingly with other RDBMS or try with H2 database by removing postgres dependency and adding H2 as runtime dependency. Also remove the configuration section as mentioned in pilot-vehicle-service/src/main/resources/application.yml)

The [openl-tablet.org](openl-tablet.org) based business rules are kept in src/main/resources/vehicle.xls. Need to change the configuration in src/main/resources/application.yml

```
rule:
  template-path: file://${RULE_TEMPLATE_PATH:/Users/anirbanchakraborty/git/pilot/pilot-service/pilot-vehicle-service/src/main/resources}/vehicle-rules.xls
```

or pass the new path as part of command line.

# Instructions

Change directory inside the main project git repository (pilot). Compile and test using root pom.


## Build and Install
```
cd pilot
mvn clean install
```

## Run
```
cd pilot-service/pilot-vehicle-service
mvn spring-boot:run
```

or

```
cd pilot-service/pilot-vehicle-service/target
java -jar pilot-vehicle-service-0.0.1.BUILD-SNAPSHOT.jar
```

## Override configuration
If your postgres is not running in localhost, and is running in a different location. Create a directory called config where the spring-boot uber jar is kept. Keep the application.yml in the config and update it accordingly.


To run the application in a different HTTP port, say 9090 instead of default 8080:

``` 
java -jar pilot-vehicle-service-0.0.1.BUILD-SNAPSHOT.jar --PORT=9090
```

Or database configuration
``` 
java -jar pilot-vehicle-service-0.0.1.BUILD-SNAPSHOT.jar --POSTGRES_HOST=<remote-host/ip> --POSTGRES_USER=<database user> --POSTGRES_PASSWORD=<database password>
```

## Test
To test use Postman ReST client or curl command:


POST Method to create a vehicle:

```
 curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"manufacturer": "nissan", "year": 2017, "model": "micra"}' \
  http://localhost:8080/vehicle
 
```
Run the src/main/resources/vehicle.json to load the sample vehicles in database.

GET Method to search vehicles

```
curl -i -H "Accept: application/json" -H "Content-Type: application/json" \ http://localhost:8080/vehicle/search?manufacturer=Nissan
```

GET Method to retrieve a vehicle

```
 curl -i -H "Accept: application/json" -H "Content-Type: application/json" http://localhost:8080/vehicle/10
```

DELETE Method to delete a vehicle

```
curl -X DELETE -H "Accept: application/json" -H "Content-Type: application/json" \ http://localhost:8080/vehicle?id=10

```

Get Preference for a vehicle

```
 curl --header "Content-Type: application/json" \
  --request POST \
  --data '{
   "segment": "large",   "type": "suv"}' \
  http://localhost:8080/vehicle
 
```
Check the 3 rules in both the worksheets to understand.





