package org.example.plannertasks.client;

import org.example.taskplannerentity.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "planner-users", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

    @PostMapping("api/users/id")
    ResponseEntity<User> findById(@RequestBody Long userId);
}

@Component
class UserFeignClientFallback implements UserFeignClient {

    @Override
    public ResponseEntity<User> findById(Long userId) {
        return null;
    }
}
