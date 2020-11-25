package com.swrookie.bulletinboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig 
{
	@Bean
	public ResourceBundleMessageSource messageSource()
	{
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("validation");
		source.setDefaultEncoding("UTF-8");
		source.setCacheSeconds(5);
		
		return source;
	}
}
