package com.springboot.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SampleSpringBootAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleSpringBootAppApplication.class, args);
	}

	@GetMapping("/hello")
	public ResponseEntity<String> sayHello() {
		return ResponseEntity.ok().body("Hello Nanda Kumara!!");
	}

	@GetMapping("/")
	public ResponseEntity<String> provideLink() {
		return ResponseEntity.ok().body("check link http://localhost:1234/hello ");
	}

}
