package com.iudc.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.iudc.common.entity"})
public class iudcBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(iudcBackEndApplication.class, args);
	}

}
