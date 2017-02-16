package com.mio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.mio.filter.MySeesionFilter;

@SpringBootApplication
@EnableCaching
public class CachetestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachetestApplication.class, args);
	}
	
	  @Bean  
	    public FilterRegistrationBean  filterRegistrationBean() {  
	        FilterRegistrationBean registrationBean = new FilterRegistrationBean();  
	        MySeesionFilter httpBasicFilter = new MySeesionFilter();  
	        registrationBean.setFilter(httpBasicFilter);  
	        List<String> urlPatterns = new ArrayList<String>();  
	        urlPatterns.add("/*");  
	        registrationBean.setUrlPatterns(urlPatterns);  
	        return registrationBean;  
	    } 
}
