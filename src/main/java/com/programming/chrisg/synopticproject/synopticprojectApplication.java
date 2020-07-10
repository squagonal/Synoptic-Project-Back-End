package com.programming.chrisg.synopticproject;

import com.programming.chrisg.synopticproject.config.SwaggerConfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Import(SwaggerConfiguration.class)
public class synopticprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.programming.chrisg.synopticproject.synopticprojectApplication.class, args);
    }

}
