package com.example.shopapp.services.imp;

import com.example.shopapp.dtos.request.UserCreationRequest;
import com.example.shopapp.dtos.request.UserUpdateRequest;
import com.example.shopapp.dtos.response.UserResponse;
import com.example.shopapp.exception.ShopAppForbiddenException;
import com.example.shopapp.exception.ShopAppInvalidDataException;
import com.example.shopapp.exception.ShopAppModelsNotFoundException;
import com.example.shopapp.mapper.UserMapper;
import com.example.shopapp.models.Role;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.security.JwtConstant;
import com.example.shopapp.security.UserDetailServiceImp;
import com.example.shopapp.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImp implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    UserDetailServiceImp userDetailService;

    @Override
    public UserResponse createUser(UserCreationRequest request) throws Exception {
        log.info("Service: create user");

        User user = userMapper.userToUser(request);
        boolean isExisted = userRepository.existsByPhoneNumber(request.getPhoneNumber());

        if(isExisted){
            throw new ShopAppModelsNotFoundException("User with phone number {} is already existed", request.getPhoneNumber());
        }

        if(request.getPassword() != null ) {

            if (!request.getPassword().matches(".*[0-9].*")) {
                throw new ShopAppInvalidDataException("Password must contain at least one digit [0-9]");
            }

            if (!request.getPassword().matches(".*[a-z].*")) {
                throw new ShopAppInvalidDataException("Password must contain at least one lowercase Latin character [a-z]");
            }

            if (!request.getPassword().matches(".*[A-Z].*")) {
                throw new ShopAppInvalidDataException("Password must contain at least one uppercase Latin character [A-Z]");
            }

            if (!request.getPassword().matches(".*[!@#&()–[{}]:;',?/*~$^+=<>].*")) {
                throw new ShopAppInvalidDataException("Password must contain at least one special character like ! @ # & ( )");
            }

            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

//        if(request.getFacebookAccountId()==0 && request.getGoogleAccountId()==0){
//
//        }

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new ShopAppModelsNotFoundException("Default role USER not found"));
        user.setRoles(Collections.singleton(role));

        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream().map(userMapper::userToUserResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ShopAppModelsNotFoundException("User is not existed"));

        return userMapper.userToUserResponse(user);
    }

    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> new ShopAppModelsNotFoundException("User not found"));

        String newPhoneNumber = request.getPhoneNumber();
        if(!user.getPhoneNumber().equals(newPhoneNumber) && userRepository.existsByPhoneNumber(newPhoneNumber)){
            throw new ShopAppModelsNotFoundException("Phone number already exists");
        }

        if(request.getFullName() != null){
            user.setFullName(request.getFullName());
        }

        if(newPhoneNumber != null) {
            user.setPhoneNumber(newPhoneNumber);
        }

        if(request.getAddress() != null) {
            user.setAddress(request.getAddress());
        }

        if(request.getDateOfBirth() != null) {
            user.setDateOfBirth(request.getDateOfBirth());
        }

        if(request.getFacebookAccountId() > 0){
            user.setFacebookAccountId(request.getFacebookAccountId());
        }

        if(request.getGoogleAccountId() > 0) {
            user.setGoogleAccountId(request.getGoogleAccountId());
        }

        if(request.getPassword() != null ) {
            String[] str = new String[]{"@", ".", "!", "|", "#", "&", "*"};

            if(!request.getRetypePassword().equals(request.getPassword())){
                throw new ShopAppModelsNotFoundException("Password and retype password not the same");
            }

            for (String s : str) {
                if (!request.getPassword().contains(s)) {
                    throw new ShopAppModelsNotFoundException("Password must have at least a special character (@ ! . | # & *)");
                }
            }

            for(int i=0; i<request.getPassword().length(); i++){
                if(!(request.getPassword().charAt(i)>='A' && request.getPassword().charAt(i)<='Z') &&
                    !(request.getPassword().charAt(i)>='1' && request.getPassword().charAt(i)<='9')
                ){
                    throw new ShopAppModelsNotFoundException("Password must have at least one capital letter and digit");
                }
            }

            String newPassword = request.getPassword();
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        userMapper.userToUserUpdate(user, request);

//        List<Role> roles = roleRepository.findAllById(request.getRoles());
//        user.setRoles(new HashSet<>(roles));

        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponse getUserDetailsFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        boolean isTokenValid = claims.getExpiration().before(new Date());

        if(isTokenValid){
            throw new ShopAppModelsNotFoundException("Token is expired");
        }

        String phoneNumber = String.valueOf(claims.get("phoneNumber"));
        if(phoneNumber.isEmpty()){
            throw new ShopAppModelsNotFoundException("Phone number is not existed");
        }

//        UserDetails userDetails = userDetailService.loadUserByUsername(phoneNumber);
//
//        if(!phoneNumber.equals(userDetails.getUsername()) && !isTokenValid){
//            throw new ShopAppForbiddenException("Invalid token: {}", token);
//        }

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new ShopAppModelsNotFoundException("User with phone number {} is not existed", phoneNumber));

        return userMapper.userToUserResponse(user);
    }
}
