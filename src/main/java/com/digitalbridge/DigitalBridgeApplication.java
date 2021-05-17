package com.digitalbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;


@SpringBootApplication
@EnableSpringDataWebSupport
public class DigitalBridgeApplication {


	public static void main(String[] args) {
		SpringApplication.run(DigitalBridgeApplication.class, args);
	}

}
