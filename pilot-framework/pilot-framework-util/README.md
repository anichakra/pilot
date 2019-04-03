# Introduction
The util framework keeps all the optional components of the framework. A microservice project can keep this framework's dependency if any of the components is used.

# RuleService
## Interface
The RuleService interface abstracts any rule system.  The rule service wraps the rule template and returns the same. A rule service instance is associated with a rule engine and rule files which the framework loads and creates an instance of a rule template.

Presently the framework supports only one rule engine which is openl-tablets.org. But the framework is built in such a way that if another rule engine is added then that can be only configured.

## Configuration
The developer need to provide the following configuration in the application.yml file:

```
rule:
  file-path: ${RULE_FILE_PATH:rule/vehicle-rules.xml} # it can be file URL or classpath directory
  template-class: me.anichakra.poc.pilot.vehicle.rule.VehicleRuleTemplate
  engine: OPENL_TABLETS_ORG
```

file-path: It can be an URL (e.g. file://, http://, etc) or a classpath where all the rule files are kept. This can be a directory consists of many rule files or a single rule file. One can specify the directory or file name in the file-path. Above example shows that all rule files are in 'rule' folder that should be in classpath. We can also specify the file name like, 'rule/vehicle.xls' (if only one rule file), but that is not necessary.

template-class: The fully qualified class-name of the interface where all the rule methods are kept
 
engine: The rule engine type e.g. OPENL_TABLETS_ORG if its openl-tables.org
template-class: The fully qualified class-name of the interface where all the rule methods are kept.

If these configurations are not placed in the yml file then RuleService instance will not get created in the context.

## Use
The RuleService should be injected in either a @ApplicationService or a @QueryService annotated class.

```
	@Inject
	private RuleService<VehicleRuleTemplate> ruleService;
	private VehicleRuleTemplate getRuleTemplate() {
		VehicleRuleTemplate vehicleRuleTemplate = ruleService.getRuleTemplate(null, null);
		return vehicleRuleTemplate;
	}
    // Use this method if you have a context to be sent to the RuleService
	private VehicleRuleTemplate getRuleTemplate(String manufacturer) {
		VehicleRuleTemplate vehicleRuleTemplate = ruleService.getRuleTemplate(manufacturer, null);
		return vehicleRuleTemplate;
	}
```
The VehicleRuleTemplate instance may or may not be stateless. So always retrieve that through getRuleTemplate() method in the actual service method to fire any rule.

# RestConsumer
## Interface
The RestConsumer is an interface for calling ReST APIs. There are different HTTP method specific interfaces also:
GetConsumer: For calling Http Get method.
PostConsumer: For calling Http Post method.
PutConsumer: For calling Http Put method.
DeleteConsumer: For calling Http Delete method.
PatchConsumer: For calling Http Patch method.

## Configuration
We need to define the consumers in application.yml file:

```
rest:
  consumers:
    - name: vehicle-preference
      url: http://localhost:8080/pilot-vehicle-service/0.0/vehicle/preference
      method: POST      
      secured: false
      contentType: application/json
      responseType: me.anichakra.poc.pilot.driver.domain.Vehicle
      headers:
        clientId: myclient
        X-USER-ID: myuserid
    - name: vehicle-search
      url: http://localhost:8080/pilot-vehicle-service/0.0/vehicle/search/manufacturer={manufacturer}
      method: GET      
      secured: true
      contentType: application/xml
      accept: text/html
      responseType: me.anichakra.poc.pilot.driver.domain.VehicleAggregator
       
```
The name should be unique. The name, url and method are mandatory
## Use 

In the service classes should inject the consumer:

```
@QueryService
public class DefaultDriverQueryService implements DriverQueryService {

	@Inject
	@Named("vehicle-preference")
	private PostConsumer<Category, Vehicle> vehiclePreferenceConsumer;
```

And simply call the consume method from any service method only passing the request object:

```
@Override
public Vehicle assignVehicle(Driver driver) {
	return vehiclePreferenceConsumer.consume(driver.getCategory());
}
```

If dynamic headers and other URI variables are required to be passed then:

```
@Inject
@Named("vehicle-search")
private GetConsumer<VehicleAggregator> vehicleSearchConsumer;

@Override
public VehicleAggregator searchVehicle(String manufacturer) {
	return vehicleSearchConsumer.addHeader("name1", "value1").addHeader("name2","value2").setUriVariables(manufacturer).consume();
}

```


Do not forget to add the @EnableRestConsumer in @Microservice annotated class. 

```
@Microservice
@EnableRestConsumers
public class DriverServiceApplication {

	public static void main(String[] args) {
		MicroserviceApplication.start(args);
	}
}

```

## Test

To mock the rest consumers to run test cases properly use a @MicroserviceTestConfiguration annotated class and mock the consumer interface using Mockito:

```

@MicroserviceTestConfiguration
public class DriverServiceApplicationTestConfiguration {

	@MockInjectable(name="vehicle-preference")
	public PostConsumer<Category, Vehicle> getPostConsumerBean() {
		@SuppressWarnings("unchecked")
		PostConsumer<Category, Vehicle> p = mock(PostConsumer.class);
		Category c = new Category();
		c.setSegment("compact");
		c.setType("suv");
		Vehicle v = new Vehicle();
		v.setManufacturer("Honda");
		v.setYear(2017);
		v.setModel("Pilot");
		v.setPrice(13300);
		p = when(p.consume(c)).thenReturn(v).getMock();
		System.out.println(p.consume(c));
		return p;
	}
}

```

