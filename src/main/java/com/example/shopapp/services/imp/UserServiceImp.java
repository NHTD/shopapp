package com.example.shopapp.services.imp;

import com.example.shopapp.dtos.request.UserCreationRequest;
import com.example.shopapp.dtos.request.UserUpdateRequest;
import com.example.shopapp.dtos.response.UserResponse;
import com.example.shopapp.exception.ShopAppModelsNotFoundException;
import com.example.shopapp.mapper.UserMapper;
import com.example.shopapp.models.Role;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.repositories.UserRepository;
import com.example.shopapp.services.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImp implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserCreationRequest request) {
        User user = userMapper.userToUser(request);
        boolean isExisted = userRepository.existsByPhoneNumber(request.getPhoneNumber());

        if(isExisted){
            throw new ShopAppModelsNotFoundException("User {} is already existed", request.getFullName());
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));

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
        User user = userRepository.findById(id).orElseThrow(() -> new ShopAppModelsNotFoundException("User is not existed"));

        userMapper.userToUserUpdate(user, request);

        List<Role> roles = roleRepository.findAllById(request.getRoles());
        user.setRoles(new HashSet<>(roles));

        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
