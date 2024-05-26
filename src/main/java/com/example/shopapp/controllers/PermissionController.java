package com.example.shopapp.controllers;

import com.example.shopapp.dtos.request.PermissionCreationRequest;
import com.example.shopapp.dtos.request.RoleCreationRequest;
import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.dtos.response.PermissionResponse;
import com.example.shopapp.dtos.response.RoleResponse;
import com.example.shopapp.services.PermissionService;
import com.example.shopapp.services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    EntityResponse<PermissionResponse> createRole(@RequestBody PermissionCreationRequest request){
        return EntityResponse.<PermissionResponse>builder()
                .status(true)
                .body(permissionService.createPermission(request))
                .build();
    }

    @GetMapping
    EntityResponse<List<PermissionResponse>> getRoles(){
        return EntityResponse.<List<PermissionResponse>>builder()
                .status(true)
                .body(permissionService.getPermissions())
                .build();
    }

    @DeleteMapping("/{name}")
    EntityResponse<String> deleteRole(@PathVariable("name") String name){
        permissionService.deletePermission(name);
        return EntityResponse.<String>builder()
                .status(true)
                .body("Delete is successful")
                .build();
    }
}
