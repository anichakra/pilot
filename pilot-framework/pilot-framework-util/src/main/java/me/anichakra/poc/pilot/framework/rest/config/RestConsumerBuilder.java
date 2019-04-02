package me.anichakra.poc.pilot.framework.rest.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.rest.api.DeleteConsumer;
import me.anichakra.poc.pilot.framework.rest.api.GetConsumer;
import me.anichakra.poc.pilot.framework.rest.api.PatchConsumer;
import me.anichakra.poc.pilot.framework.rest.api.PostConsumer;
import me.anichakra.poc.pilot.framework.rest.api.PutConsumer;
import me.anichakra.poc.pilot.framework.rest.api.RestConsumer;
import me.anichakra.poc.pilot.framework.rest.impl.DefaultDeleteConsumer;
import me.anichakra.poc.pilot.framework.rest.impl.DefaultGetConsumer;
import me.anichakra.poc.pilot.framework.rest.impl.DefaultPatchConsumer;
import me.anichakra.poc.pilot.framework.rest.impl.DefaultPostConsumer;
import me.anichakra.poc.pilot.framework.rest.impl.DefaultPutConsumer;

@Component
public class RestConsumerBuilder<K,V> {
	List<GetConsumer<V>> buildGetConsumerProperties(List<RestConsumerConfigurationProperties<V>> consumers) {
		return consumers.stream().filter(c -> c.getMethod().equals(HttpMethod.GET)).map(c -> {
			GetConsumer<V> consumer = new DefaultGetConsumer<>(c.getName(), c.getUrl(), c.getSecured(),
					 c.getResponseType());
			populateNullableProperties(c, consumer);
			return consumer;
		}).collect(Collectors.toList());
	}

	List<PostConsumer<K,V>> buildPostConsumerProperties(List<RestConsumerConfigurationProperties<V>> consumers) {
		return consumers.stream().filter(c -> c.getMethod().equals(HttpMethod.POST)).map(c -> {
			DefaultPostConsumer<K,V> consumer = new DefaultPostConsumer<K,V>(c.getName(), c.getUrl(), c.getSecured(),
					 c.getResponseType());
			populateNullableProperties(c, consumer);  
			return consumer;
		}).collect(Collectors.toList());
	}

	List<PutConsumer<K>> buildPutConsumerProperties(List<RestConsumerConfigurationProperties<V>> consumers) {
		return consumers.stream().filter(c -> c.getMethod().equals(HttpMethod.PUT)).map(c -> {
			DefaultPutConsumer<K> consumer = new DefaultPutConsumer<>(c.getName(), c.getUrl(), c.getSecured());
			populateNullableProperties(c, consumer);
			return consumer;
		}).collect(Collectors.toList());
	}

	List<DeleteConsumer> buildDeleteConsumerProperties(List<RestConsumerConfigurationProperties<V>> consumers) {
		return consumers.stream().filter(c -> c.getMethod().equals(HttpMethod.DELETE)).map(c -> {
			DefaultDeleteConsumer consumer = new DefaultDeleteConsumer(c.getName(), c.getUrl(), c.getSecured());
			populateNullableProperties(c, consumer);
			return consumer;
		}).collect(Collectors.toList());
	}

	List<PatchConsumer<K,V>> buildPatchConsumerProperties(List<RestConsumerConfigurationProperties<V>> consumers) {
		return consumers.stream().filter(c -> c.getMethod().equals(HttpMethod.PATCH)).map(c -> {
			DefaultPatchConsumer<K,V> consumer = new DefaultPatchConsumer<K,V>(c.getName(), c.getUrl(),
					c.getSecured(), c.getResponseType());
			populateNullableProperties(c, consumer);
			return consumer;
		}).collect(Collectors.toList());
	}

	void populateNullableProperties(RestConsumerConfigurationProperties<V> c, final RestConsumer consumer) {
		consumer.setAccept(c.getAccept());
		consumer.setContentType(c.getContentType());
		consumer.setStatusCode(c.getStatusCode());
		c.getHeaders().entrySet().stream().forEach(e->consumer.addHeader(e.getKey(), e.getValue()));
	}
}