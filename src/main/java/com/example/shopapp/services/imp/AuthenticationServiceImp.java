package com.example.shopapp.services.imp;

import com.example.shopapp.dtos.request.AuthenticationRequest;
import com.example.shopapp.dtos.response.AuthenticationResponse;
import com.example.shopapp.exception.ShopAppModelsNotFoundException;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.security.JwtProvider;
import com.example.shopapp.security.UserDetailServiceImp;
import com.example.shopapp.services.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServiceImp implements AuthenticationService {

    JwtProvider jwtProvider;
    UserDetailServiceImp userDetailService;
    PasswordEncoder passwordEncoder;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        String phoneNumber = request.getPhoneNumber();
        String password = request.getPassword();

        Authentication authentication = authenticate(phoneNumber, password);

        String jwt = jwtProvider.generateToken(authentication);

        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    private Authentication authenticate(String phoneNumber, String password){
        UserDetails userDetails = userDetailService.loadUserByUsername(phoneNumber);

        if(userDetails==null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
