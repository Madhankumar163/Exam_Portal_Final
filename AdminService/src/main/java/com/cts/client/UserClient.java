package com.cts.client;

import com.cts.dto.RegisterRequest;
import com.cts.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "UserService", path = "/users")
public interface UserClient {
    @PostMapping("/register")
    UserDTO registerUser(@RequestBody RegisterRequest request);

    @GetMapping("/email/{email}")
    UserDTO getUserByEmail(@PathVariable String email);
}