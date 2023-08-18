package com.kucharka.Kucharka;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
public class KucharkaApplication  {

	public static void main(String[] args) {

		SpringApplication.run(KucharkaApplication.class, args);

	}



}


