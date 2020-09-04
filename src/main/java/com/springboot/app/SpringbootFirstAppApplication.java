package com.springboot.app;

import com.springboot.app.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)

public class SpringbootFirstAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootFirstAppApplication.class, args);
	}

}
