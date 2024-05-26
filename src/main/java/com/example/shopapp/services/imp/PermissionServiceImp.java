package com.example.shopapp.services.imp;

import com.example.shopapp.dtos.request.PermissionCreationRequest;
import com.example.shopapp.dtos.response.PermissionResponse;
import com.example.shopapp.exception.ShopAppModelsNotFoundException;
import com.example.shopapp.mapper.PermissionMapper;
import com.example.shopapp.models.Permission;
import com.example.shopapp.repositories.PermissionRepository;
import com.example.shopapp.services.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImp implements PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse createPermission(PermissionCreationRequest request) {
        Permission permission = permissionMapper.permissionToPermission(request);
        if(permissionRepository.findByName(request.getName()).isPresent()){
            throw new ShopAppModelsNotFoundException("Name {} is existed", request.getName());
        }

        return permissionMapper.permissionToPermissionResponse(permissionRepository.save(permission));
    }

    @Override
    public List<PermissionResponse> getPermissions() {
        return permissionRepository.findAll().stream().map(permissionMapper::permissionToPermissionResponse).collect(Collectors.toList());
    }

    @Override
    public void deletePermission(String permission) {
        permissionRepository.deleteByName(permission);
    }
}
