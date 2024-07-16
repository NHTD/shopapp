package com.example.shopapp.controllers;

import com.example.shopapp.dtos.request.UserCreationRequest;
import com.example.shopapp.dtos.request.UserUpdateRequest;
import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.dtos.response.UserResponse;
import com.example.shopapp.models.User;
import com.example.shopapp.services.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService;

    @PostMapping
    EntityResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) throws Exception {
        log.info("Controller: create user");

        return EntityResponse.<UserResponse>builder()
                .status(true)
                .body(userService.createUser(request))
                .build();
    }

    @PostMapping("/details")
    @PreAuthorize("hasAnyAuthority('READ_DATA')")
    public EntityResponse<UserResponse> getUserDetails(@RequestHeader("Authorization") String token){
        String extractedToken = token.substring(7);
        return EntityResponse.<UserResponse>builder()
                .status(true)
                .body(userService.getUserDetailsFromToken(extractedToken))
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

    @PutMapping("/details/{id}")
    @PreAuthorize("hasAnyAuthority('READ_DATA')")
    EntityResponse<?> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserUpdateRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ){
        String token = authorizationHeader.substring(7);
        UserResponse user = userService.getUserDetailsFromToken(token);

        if(!Objects.equals(user.getId(), id)) {
            return EntityResponse.builder().status(false).body("Id not match").build();
        }

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
