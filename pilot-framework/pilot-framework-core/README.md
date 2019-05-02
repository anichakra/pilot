# Introduction
The core framework has all the mandatory components that all microservice will require. The main components comprises of the [CQRS pattern](https://microservices.io/patterns/data/cqrs.html) design using custom annotations, common exception handler and runtime utilities. All microservice project should have a dependency on pilot-framework-core.

# MicroserviceApplication
All main class of a microservice project should call the MicroserviceApplication.start() to initialize the microservice. This class should be anaemic so that nothing else can be declared and will be considered to be a stereotypical component as part of all microservices.

```
@Microservice
public class DriverServiceApplication {

	public static void main(String[] args) {
		MicroserviceApplication.start(args);
	}
}

```
Here the node id of the microservice cluster is created.

# Annotations
## Field Injection
All the autowiring or inject based annotation are opinionated. Should only use these annotations in the controller, service and data access classes:

@Inject: Use this to inject any service or repository annotated class. This is a javax.inject package standard annoation, not dependent on any dependency injection framework.

## Class
@FrameworkService: Use this annotation to mark a service class which are provided by the framework.

@ApplicationService: Use this annotation to mark a service class which does not do any database operations but other operations like calling external systems or processing complex business logic. A Controller, a CommandService and a QueryService can inject an ApplicationService. An ApplicationService annotated class cannot inject CommandService, QueryService or Repository. It can only inject another ApplicationService or FrameworkService or both.

@CommandService: Use this annotation to mark a service class as a of type "Command". This is to enforce CQRS pattern implementation in service classes to separate out the command and query based operations. Refer to CQRS pattern. A command service does write operation with the data access and do not do query operations. All methods in a command service are transactional and non-readonly in nature. A CommandService annotated class can inject one or more CommandService, ApplicationService FrameworkService and Repository. A CommandService cannot inject a QueryService.

@QueryService: Use this annotation to mark a service class as a of type "Query". This is to enforce CQRS pattern implementation in service classes to separate out the command and query based operations. Refer to CQRS pattern. A query service does not do write operation with the data access and do only. A QueryService annotation class can inject another QueryService, one or more ApplicationService, FrameworkService or Repository, but cannot inject a CommandService. query or read operations.

## Validation
During startup of the microservices the following validation processing is done using the annotations to make the application abide by CQRS pattern properly:

@Microservice: Should not contain any declared field or method apart from main method.
@RestController: Should contain only one declared field that should be Injected with either of the following annotated class - ApplicationService, CommandService, QueryService. Any other kind of class injection will be invalidated and exception will be thrown.
@CommandService: Can inject ApplicationService, FrameworkService, Repository or another CommandService, but cannot contain a QueryService
@QueryService: Can inject ApplicationService, FrameworkService, Repository or another QueryService, but cannot contain a CommandService
@ApplicationService: Can Inject one or more ApplicationService, FrameworkService, but cannot inject any CommandService, QueryService or Repository

# Microservice Versioning
This WebserverCustomizer component customizes the context-path of the web application. The context-path is taken from the maven artifactId and version (only the major and minor, from the bug-fix part it is ignored). It also sets the "spring.application.name" to the artifactId. So if the artifactId is my-micro-service and version is 1.2.3.BUILD-SNAPSHOT, then the context-path will be: /my-micro-service/1.2. For e.g. for a API called vehicle/preference the entire POST URL will be http://mysite.com/my-micro-service/1.2/vehicle/preference.

To achieve this the following plugin goal configuration need to be done in pom.xml:

<plugin>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-maven-plugin</artifactId>
   <version>...</version> 
   <executions> 
     <execution>
       <id>build-info</id>
       <goals> 
         <goal>build-info</goal> 
         <goal>repackage</goal>
       </goals> 
     </execution> 
   </executions> 
 </plugin> 

# Validation
This package provides a framework for JSR303 messaging and exception handling. Validation is classified in two ways: input validation and business validation. Any unsuccessful validation results in exception which is converted into ErrorInfo and returned as part of the ReST response. 

All the message templates can exception handling details can be configured in application.yml

```
message:
  validations:
    getVehicle.id: Vehicle Id should be more than ${value}.
    searchVehicle.manufacturer.Size: Manufacturer name must be between ${min} and ${max}. Provided number ${invalidValue} is invalid.
    searchVehicle.manufacturer.Pattern: At least 4 digit/alphabetic chars is required
    vehicle.manufacturer.Size: Manufacturer name must be between ${min} and ${max}. Provided number ${invalidValue} is invalid.
    vehicle.manufacturer.Empty: Manufacturer cannot be empty
    vehicle.year.Min: Vehicle year should start from at least ${value}
  exceptions:
   - name: VehicleNotFoundException
     code: getVehicle.notFound
     http-status: BAD_REQUEST
     template: Vehicle ${id} is not found
   - name: java.lang.Exception
     code: exception.unknown
     http-status: INTERNAL_SERVER_ERROR
     template: Unknown System Exception 
```

## Input Validation
This is implemented using JSR303 with hibernate validation framework. This framework helps to configure the message templates in a generalized and contextual manner. Explanations are given below with some examples:

```
    getVehicle.id: Vehicle Id should be more than ${value}.
```
getVehicle.id is the error code. It can be either configured in the message attribute of the JSR303 annotations or if not mentioned then framework generates it from the path automatically. The ${value} is the value mentioned in the annotation's value attribute.

```
	public Vehicle getVehicle(@PathVariable("id") @Min(1) Long id) throws VehicleNotFoundException {
	}
```

Similarly for the below configuration:

```
searchVehicle.manufacturer.Size: Manufacturer name must be between ${min} and ${max}. Provided number ${invalidValue} is invalid.
```
The min and max are the attribute of annotation @Size while input whereas invalidValue is the input value for which this this validation failed.

In the other example, vehicle.manufacturer.Size is a path for the annotation in the domain object itself. Without specifying any code in the message attribute in the validation annotation, the message template can be changed like this.
## Business Validation
Business validation can be handled by throwing an exception, e.g.:

```
return vehicleQueryService.getVehicle(id).orElseThrow(() -> new VehicleNotFoundException(id));
```
In that case the message template configuration can be stated like:

```
exception:
   - name: VehicleNotFoundException
     code: getVehicle.notFound
     http-status: BAD_REQUEST
     template: Vehicle ${id} is not found
```
where name can be the exception's class simple class name, or full class name with package. A code is any unique value. Unlike input validation where http-status is always BAD_REQUEST, here http-status need to be provided. The template can contain any property of the exception instance.

The exception handler will try to find recursively which super-class is configured if not found direct mapping available for a raised exception. For example if a system exception is thrown it will retrieve the template from java.lang.Exception mapping as configured.

If the framework defined ErrorInfo required to be tranformed to a contextual error response, then create an implementation of ExceptionMapper to map the ErrorInfo to a custom bean. Annotate that class with @MappedClass annotation specifying the exception classes to be mapped for this transformation. If one common mapper is sufficient for all cases then just specify java.lang.Exception as the mapped classes.

```
@ClassMapper(classes= {Exception.class})
public class VehicleExceptionMapper implements ExceptionMapper<VehicleErrorInfo>{

	@Override
	public VehicleErrorInfo map(ErrorInfo errorInfo) {
		VehicleErrorInfo vehicleErrorInfo = new VehicleErrorInfo();
		vehicleErrorInfo.setCode(errorInfo.getCode() + "." +"vehicle");
		return vehicleErrorInfo;
	}

}

```

