package com.example.shopapp.services;

import com.example.shopapp.dtos.request.RoleCreationRequest;
import com.example.shopapp.dtos.request.UserCreationRequest;
import com.example.shopapp.dtos.request.UserUpdateRequest;
import com.example.shopapp.dtos.response.RoleResponse;
import com.example.shopapp.dtos.response.UserResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleCreationRequest request);
    List<RoleResponse> getRoles();
    void deleteRole(String name);
}
