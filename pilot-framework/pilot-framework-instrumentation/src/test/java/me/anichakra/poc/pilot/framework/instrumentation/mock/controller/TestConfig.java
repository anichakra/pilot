package me.anichakra.poc.pilot.framework.instrumentation.mock.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(basePackages = "me.anichakra.poc.pilot.framework")
@EnableConfigurationProperties
@EnableAutoConfiguration
@Configuration
public class TestConfig {

}
