package com.example.shopapp.mapper;

import com.example.shopapp.dtos.request.UserCreationRequest;
import com.example.shopapp.dtos.request.UserUpdateRequest;
import com.example.shopapp.dtos.response.UserResponse;
import com.example.shopapp.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User userToUser(UserCreationRequest request);
    UserResponse userToUserResponse(User user);
    @Mapping(target = "roles", ignore = true)
    void userToUserUpdate(@MappingTarget User user, UserUpdateRequest request);
}
