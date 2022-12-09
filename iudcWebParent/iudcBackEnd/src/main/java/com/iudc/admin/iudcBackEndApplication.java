package com.iudc.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EntityScan({"com.iudc.common.entity"})
public class iudcBackEndApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(iudcBackEndApplication.class, args);
	}

}
