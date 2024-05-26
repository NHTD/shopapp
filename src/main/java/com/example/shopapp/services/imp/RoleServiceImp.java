package com.example.shopapp.services.imp;

import com.example.shopapp.dtos.request.RoleCreationRequest;
import com.example.shopapp.dtos.response.RoleResponse;
import com.example.shopapp.exception.ShopAppModelsNotFoundException;
import com.example.shopapp.mapper.RoleMapper;
import com.example.shopapp.models.Permission;
import com.example.shopapp.models.Role;
import com.example.shopapp.repositories.PermissionRepository;
import com.example.shopapp.repositories.RoleRepository;
import com.example.shopapp.services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImp implements RoleService {

    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public RoleResponse createRole(RoleCreationRequest request) {
        Role role = roleMapper.roleToRole(request);

        if(roleRepository.findByName(request.getName()).isPresent()){
            throw new ShopAppModelsNotFoundException("Role {} is existed", request.getName());
        }

        List<Permission> permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        return roleMapper.roleToRoleResponse(roleRepository.save(role));
    }

    @Override
    public List<RoleResponse> getRoles() {
        return roleRepository.findAll().stream().map(roleMapper::roleToRoleResponse).collect(Collectors.toList());
    }

    @Override
    public void deleteRole(String name) {
        roleRepository.deleteByName(name);
    }
}
