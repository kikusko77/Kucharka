package com.kucharka.Kucharka;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@AllArgsConstructor
public class KucharkaApplication  {

	public static void main(String[] args) {
		SpringApplication.run(KucharkaApplication.class, args);
	}



}


