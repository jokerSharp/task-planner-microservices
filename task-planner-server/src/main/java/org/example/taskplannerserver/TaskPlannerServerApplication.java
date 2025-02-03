package org.example.taskplannerserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TaskPlannerServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskPlannerServerApplication.class, args);
    }

}
