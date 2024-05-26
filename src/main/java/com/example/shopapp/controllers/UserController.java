package com.example.shopapp.controllers;

import com.example.shopapp.dtos.request.UserCreationRequest;
import com.example.shopapp.dtos.request.UserUpdateRequest;
import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.dtos.response.UserResponse;
import com.example.shopapp.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @PostMapping
    EntityResponse<UserResponse> createUser(@RequestBody UserCreationRequest request){
        return EntityResponse.<UserResponse>builder()
                .status(true)
                .body(userService.createUser(request))
                .build();
    }

    @GetMapping
    EntityResponse<List<UserResponse>> getUsers(){
        return EntityResponse.<List<UserResponse>>builder()
                .status(true)
                .body(userService.getUsers())
                .build();
    }

    @GetMapping("/{id}")
    EntityResponse<UserResponse> getUser(@PathVariable("id") Long id){
        return EntityResponse.<UserResponse>builder()
                .status(true)
                .body(userService.getUser(id))
                .build();
    }

    @PutMapping("/{id}")
    EntityResponse<UserResponse> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request){
        return EntityResponse.<UserResponse>builder()
                .status(true)
                .body(userService.updateUser(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    EntityResponse<String> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return EntityResponse.<String>builder()
                .status(true)
                .body("Delete is successful")
                .build();
    }
}
