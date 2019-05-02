# Introduction
The instrumentation framework keeps all cross cutting concerns separately. This project can be plugged as dependency to a spring boot project and there is no as-such compilation dependency. The instrumentation framework instruments any request to web by handling a client request to introspects the same and do necessary actions like logging, notification, messaging, monitoring (new-relic) etc in an opinionated and configurable way.

#Design
## Components
This framework is based on an event driven architecture, where any invocation is captured and an event is created for the same. The event is then handled and a proper action is taken. A invocation is a single call to the web tier, basically a ReST API call. The invocation can start from a web filter, then proceed to a RestController, a service class and then a repository. Each invocation creates several invocation metric during its traversal through different component. Each method call inside any of the following class is introspected using aspect oriented programming:
1) RestController
2) CommandService
3) QueryService
4) ApplicationService
5) FrameworkService
6) Repository

The result is an InvocationEvent that contains one or more InvocationMetric. Each InvocationEvent corresponds to one web invocation and each InvocationMetric corresponds to each method call from classes listed above.

## InvocationBus
Each invocation event generated is published in a invocation bus as part of this observer design pattern implementation. Any number of handlers to this event can be registered in the bus. On publishing an event all the registered handlers will be notified. Each handlers has some objective to fulfil, like writing events to log in a structured way, or publish a message to a distributed messaging engine.

## Configuration
As this instrumentation framework is using Spring AOP internally, in application.yml one need to mention the following:

```
spring:
  aop:
    auto: true
    proxy-target-class: true
```

The general instrumentation configuration is:

```
instrumentation: 
  filter:
    enabled: true 
  aspect:
    enabled: true
    ignore-duration-in-millis : 20
```
We can enable and disable the filter and aspect for introspection from here. ignore-duration-in-millis makes sure that invocations that are taking very small amount of time is to be ignored.

# Handlers
## Log
This logs each event. The log line although can be customized but it captures all metrics. Following is an example of a configuration

```
instrumentation:
  handlers:
    log:
      layers:
      - FILTER
      - CONTROLLER
      - SERVICE
      - REPOSITORY
      event-names: all
      ignore-parameters: false
      ignore-outcome: true
      ignore-exception-stack: true
      limited: false
```

Here we can control lot of things. Logging can be enabled or disabled, based on layers. The event-names: all means for all methods this logging will be considered.

The example of one user transaction log is given below:

```
[INFO ] 2019-05-02 14:12:27.950 [http-nio-8080-exec-1] INSTRUMENTATION_LOG - 63492bed-81de-449f-a4f2-dcbf8731931f;b81f52b5-f431-408d-a465-3fe5d81f3956;null;null;null;0:0:0:0:0:0:0:1:8080 ;0:0:0:0:0:0:0:1;[POST]/pilot-vehicle-service/0.0/vehicle;me.anichakra.poc.pilot.framework.instrumentation.aspect.InstrumentationFilter.doFilter();;S;[]
[INFO ] 2019-05-02 14:12:28.110 [http-nio-8080-exec-1] INSTRUMENTATION_LOG - 63492bed-81de-449f-a4f2-dcbf8731931f;b81f52b5-f431-408d-a465-3fe5d81f3956;null;null;null;0:0:0:0:0:0:0:1:8080 ;0:0:0:0:0:0:0:1;[POST]/pilot-vehicle-service/0.0/vehicle;public me.anichakra.poc.pilot.vehicle.domain.Vehicle me.anichakra.poc.pilot.vehicle.web.VehicleCommandController.saveVehicle(me.anichakra.poc.pilot.vehicle.domain.Vehicle);;S;[{"id":null,"manufacturer":"Honda","year":2019,"model":"Amaze","price":null}]
[INFO ] 2019-05-02 14:12:28.141 [http-nio-8080-exec-1] INSTRUMENTATION_LOG - 63492bed-81de-449f-a4f2-dcbf8731931f;b81f52b5-f431-408d-a465-3fe5d81f3956;null;null;null;0:0:0:0:0:0:0:1:8080 ;0:0:0:0:0:0:0:1;[POST]/pilot-vehicle-service/0.0/vehicle;public me.anichakra.poc.pilot.vehicle.domain.Vehicle me.anichakra.poc.pilot.vehicle.service.impl.DefaultVehicleCommandService.saveVehicle(me.anichakra.poc.pilot.vehicle.domain.Vehicle);;S;[{"id":null,"manufacturer":"Honda","year":2019,"model":"Amaze","price":null}]
[INFO ] 2019-05-02 14:12:28.195 [http-nio-8080-exec-1] INSTRUMENTATION_LOG - 63492bed-81de-449f-a4f2-dcbf8731931f;b81f52b5-f431-408d-a465-3fe5d81f3956;null;null;null;0:0:0:0:0:0:0:1:8080 ;0:0:0:0:0:0:0:1;[POST]/pilot-vehicle-service/0.0/vehicle;public me.anichakra.poc.pilot.vehicle.domain.Vehicle me.anichakra.poc.pilot.vehicle.service.impl.DefaultVehicleCommandService.saveVehicle(me.anichakra.poc.pilot.vehicle.domain.Vehicle);;C;56
[INFO ] 2019-05-02 14:12:28.266 [http-nio-8080-exec-1] INSTRUMENTATION_LOG - 63492bed-81de-449f-a4f2-dcbf8731931f;b81f52b5-f431-408d-a465-3fe5d81f3956;null;null;null;0:0:0:0:0:0:0:1:8080 ;0:0:0:0:0:0:0:1;[POST]/pilot-vehicle-service/0.0/vehicle;public me.anichakra.poc.pilot.vehicle.domain.Vehicle me.anichakra.poc.pilot.vehicle.web.VehicleCommandController.saveVehicle(me.anichakra.poc.pilot.vehicle.domain.Vehicle);;C;158
[INFO ] 2019-05-02 14:12:28.297 [http-nio-8080-exec-1] INSTRUMENTATION_LOG - 63492bed-81de-449f-a4f2-dcbf8731931f;b81f52b5-f431-408d-a465-3fe5d81f3956;null;null;null;0:0:0:0:0:0:0:1:8080 ;0:0:0:0:0:0:0:1;[POST]/pilot-vehicle-service/0.0/vehicle;me.anichakra.poc.pilot.framework.instrumentation.aspect.InstrumentationFilter.doFilter();;C;348
```
The above instrumentation log is for one invocation to web. It shows the complete navigation of the call from filter, to controller, service and repository. Following is the structure illustrated.
When the invocation enters any method the Start log with status S is written:

```
REQUESTED_SESSION_ID;GENERATED_EVENT_ID;CORRELATION_ID;LEVEL;URI[METHOD];LOCAL_ADDRESS;REMOTE_ADDRESS;USER_ID;URI_PARAMETER;METHOD_SIGNATURE;S;ARGUMENTS_VALUE
```
When the invocation leaves a method the completion log line with status as C is written:

```
REQUESTED_SESSION_ID;GENERATED_EVENT_ID;CORRELATION_ID;LEVEL;URI[METHOD];LOCAL_ADDRESS;REMOTE_ADDRESS;USER_ID;URI_PARAMETER;METHOD_SIGNATURE;C;DURATION_IN_MILLIS;RETURN_VALUE
```
When the invocation fails due to some exception, failure log is written with status as F:

``` 
REQUESTED_SESSION_ID;GENERATED_EVENT_ID;CORRELATION_ID;LEVEL;URI[METHOD];LOCAL_ADDRESS;REMOTE_ADDRESS;USER_ID;URI_PARAMETER;METHOD_SIGNATURE;F;DURATION_IN_MILLIS;EXCEPTION_STACK
```
The completion and failure log line can be trimmed using configuration 'trim: true' to only provide mandatory items:

```
REQUESTED_SESSION_ID;GENERATED_EVENT_ID;C;DURATION_IN_MILLIS;RETURN_VALUE
REQUESTED_SESSION_ID;GENERATED_EVENT_ID;F;DURATION_IN_MILLIS;EXCEPTION_STACK
```
Using these logs one can easily find the performance bottleneck, debug program easily and understand a particular execution flow.


## AWS-SNS
This handler will publish a message to SNS configured topic. This can be used as event-sourcing. Any service method can be annotated with @Event annotation, example given below:

```
@Event(name="sourcing", object=EventObject.RESPONSE)
public Vehicle saveVehicle(Vehicle vehicle) {
}
```

This method will be treated as an event and its argument and return value will be intervene. Event can use either the argument (EventObject.REQUEST) or the return value (EventObject.RESPONSE), which can be indicated in the annotation. Default is REQUEST. Following is the configuration  for this handler:

```
instrumentation:
  handlers:
    aws-sns:
      event-names: sourcing
```

We can add as many event-names here using comma separation. 
