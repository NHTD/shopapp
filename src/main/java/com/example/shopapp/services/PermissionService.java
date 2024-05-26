package com.example.shopapp.services;

import com.example.shopapp.dtos.request.PermissionCreationRequest;
import com.example.shopapp.dtos.request.RoleCreationRequest;
import com.example.shopapp.dtos.response.PermissionResponse;
import com.example.shopapp.dtos.response.RoleResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionCreationRequest request);
    List<PermissionResponse> getPermissions();
    void deletePermission(String permission);
}
