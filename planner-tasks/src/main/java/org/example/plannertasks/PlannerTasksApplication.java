package org.example.plannertasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "org.example")
@EnableJpaRepositories(basePackages = "org.example.plannertasks")
@EnableDiscoveryClient
@SpringBootApplication
public class PlannerTasksApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlannerTasksApplication.class, args);
    }

}
