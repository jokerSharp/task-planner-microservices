package org.example.plannertasks.client;

import org.example.taskplannerentity.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "planner-users")
public interface UserFeignClient {

    @PostMapping("api/users/id")
    ResponseEntity<User> findById(@RequestBody Long userId);
}
