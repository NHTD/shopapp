package com.example.shopapp.mapper;

import com.example.shopapp.dtos.request.RoleCreationRequest;
import com.example.shopapp.dtos.response.RoleResponse;
import com.example.shopapp.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role roleToRole(RoleCreationRequest request);
    RoleResponse roleToRoleResponse(Role role);
}
