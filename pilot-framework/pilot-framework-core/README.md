# Annotations
## Field Injection
All the autowiring or inject based annotation are opinionated. Should only use these annotations in the controller, service and data access classes:

@Injectable: Use this to return a component or injectable bean from a method of a configuration class.
@InjectService: Inject a service instance in a controller or service class.
@InjectDataAccess: Inject a repository or DAO instance in a service class

## Class
@ApplicationService: This annotation should be used to mark a service as application service. An application service is one which orchestrate other services for a complex API where the business process has multiple steps and not only read or write to database. An application service can call another command service or query service.

@CommandService: Use this annotation to mark a service class as a of type "Command". This is to enforce CQRS pattern implementation in service classes to separate out the command and query based operations. Refer to CQRS pattern. A command service does write operation with the data access and do not do query operations. All methods in a command service are transactional in nature.

@QueryService: Use this annotation to mark a service class as a of type "Query". This is to enforce CQRS pattern implementation in service classes to separate out the command and query based operations. Refer to CQRS pattern. A query service does not do write operation with the data access and do only query or read operations.

@DataAccess: Use this annotation to mark a component, i.e. a class or an interface as data access. All components that access a database should be marked with this annotation.






