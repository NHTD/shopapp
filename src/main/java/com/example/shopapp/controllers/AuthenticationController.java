package com.example.shopapp.controllers;

import com.example.shopapp.dtos.request.AuthenticationRequest;
import com.example.shopapp.dtos.response.AuthenticationResponse;
import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.services.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    EntityResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return EntityResponse.<AuthenticationResponse>builder()
                .status(true)
                .body(authenticationService.authenticate(request))
                .build();
    }

}
