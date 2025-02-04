package org.example.plannertasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PlannerTasksApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlannerTasksApplication.class, args);
    }

}
