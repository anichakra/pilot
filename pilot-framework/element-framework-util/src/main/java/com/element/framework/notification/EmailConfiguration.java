package me.anichakra.poc.pilot.framework.notification;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.utility.XmlEscape;

@Configuration
public class EmailConfiguration {
	
	@Bean(name ="freemarkerConfig") 
	public FreeMarkerConfigurer getFreemarkerConfig() {
	       FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
	       configurer.setTemplateLoaderPath("classpath:mail/");
	       Map<String, Object> map = new HashMap<String, Object>();
	       map.put("xml_escape", new XmlEscape());
	       configurer.setFreemarkerVariables(map);
	  return configurer;
	}

}
