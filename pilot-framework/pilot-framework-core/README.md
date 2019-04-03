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



