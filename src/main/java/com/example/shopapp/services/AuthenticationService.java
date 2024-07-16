package com.example.shopapp.services;

import com.example.shopapp.dtos.request.AuthenticationRequest;
import com.example.shopapp.dtos.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
