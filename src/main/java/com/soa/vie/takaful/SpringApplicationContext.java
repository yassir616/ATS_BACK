package com.soa.vie.takaful;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringApplicationContext implements ApplicationContextAware {

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringApplicationContext.context = applicationContext;
	}

	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

}