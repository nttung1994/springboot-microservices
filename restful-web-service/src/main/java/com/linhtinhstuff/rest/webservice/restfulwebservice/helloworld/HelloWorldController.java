package com.linhtinhstuff.rest.webservice.restfulwebservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(path="/hello-world")
	public String helloWorld() {
		return "Hello world";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello world");
	}
	
	@GetMapping(path="/hello-world/path-variable/{name}")
	public String helloWorldPathVariable(@PathVariable String name) {
		return "Hello world, " + name;
	}
	
	@GetMapping(path="/hello-world-international")
	public String helloWorldInternational() {
		return messageSource.getMessage("goodMorning", null, LocaleContextHolder.getLocale());
	}

}
