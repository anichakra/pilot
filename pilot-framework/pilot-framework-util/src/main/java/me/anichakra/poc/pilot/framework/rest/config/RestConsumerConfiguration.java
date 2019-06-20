package me.anichakra.poc.pilot.framework.rest.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author anirbanchakraborty
 *
 * @param <K>
 * @param <V>
 */
@Configuration
@ConfigurationProperties(prefix = "rest")
@Validated
public class RestConsumerConfiguration<K, V> {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	RestConsumerBuilder<K, V> builder;

	private Class<? extends RestTemplate> implClass;
	
	@NestedConfigurationProperty
	private List<RestConsumerConfigurationProperties<V>> consumers;

	@Bean("consumers")
	public List<RestConsumerConfigurationProperties<V>> registerDynamicBeans() {		Optional.ofNullable(this.consumers).ifPresent(cs -> {
			if (applicationContext instanceof ConfigurableApplicationContext) {
				ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext) applicationContext)
						.getBeanFactory();
				Optional.ofNullable(builder.buildGetConsumerProperties(cs, implClass))
						.ifPresent(p -> p.forEach(c -> beanFactory.registerSingleton(c.getName(), c)));
				Optional.ofNullable(builder.buildPostConsumerProperties(cs, implClass))
						.ifPresent(p -> p.forEach(c -> beanFactory.registerSingleton(c.getName(), c)));
				Optional.ofNullable(builder.buildPutConsumerProperties(cs, implClass))
						.ifPresent(p -> p.forEach(c -> beanFactory.registerSingleton(c.getName(), c)));
				Optional.ofNullable(builder.buildDeleteConsumerProperties(cs, implClass))
						.ifPresent(p -> p.forEach(c -> beanFactory.registerSingleton(c.getName(), c)));
				Optional.ofNullable(builder.buildPatchConsumerProperties(cs, implClass))
						.ifPresent(p -> p.forEach(c -> beanFactory.registerSingleton(c.getName(), c)));
			}
		});
		return this.consumers;
	}

	public void setConsumers(List<RestConsumerConfigurationProperties<V>> consumers) {
		this.consumers = consumers;
	}

	/**
	 * @param implClass the implClass to set
	 */
	public void setImplClass(Class<? extends RestTemplate> implClass) {
		this.implClass = implClass;
	}

}
