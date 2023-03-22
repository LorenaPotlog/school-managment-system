package com.example.school;

import com.example.school.service.DatabaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolApplication {


	public static void main(String[] args) {
		SpringApplication.run(SchoolApplication.class, args);
	}

}
