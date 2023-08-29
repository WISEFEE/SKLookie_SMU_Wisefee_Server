package com.sklookiesmu.wisefee;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.io.File;

@SpringBootApplication
public class WisefeeApplication {

	@Value("${upload.directory}")
	private String uploadDirectory;


	public static void main(String[] args) {
		SpringApplication.run(WisefeeApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}

	@PostConstruct
	public void init() {
		File directory = new File(uploadDirectory);
		if (!directory.exists()) {
			directory.mkdirs(); // 디렉터리 생성
		}
	}

}
