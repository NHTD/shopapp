package com.example.shopapp.mapper;

import com.example.shopapp.dtos.request.PermissionCreationRequest;
import com.example.shopapp.dtos.response.PermissionResponse;
import com.example.shopapp.models.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission permissionToPermission(PermissionCreationRequest request);
    PermissionResponse permissionToPermissionResponse(Permission permission);
}
