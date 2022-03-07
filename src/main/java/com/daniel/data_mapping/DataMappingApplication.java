package com.daniel.data_mapping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataMappingApplication {

	public static void main(String[] args) {
		new DataLoading();
		SpringApplication.run(DataMappingApplication.class, args);
	}

}
