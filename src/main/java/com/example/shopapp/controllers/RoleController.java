package com.example.shopapp.controllers;

import com.example.shopapp.dtos.request.RoleCreationRequest;
import com.example.shopapp.dtos.response.EntityResponse;
import com.example.shopapp.dtos.response.RoleResponse;
import com.example.shopapp.services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @PostMapping
    EntityResponse<RoleResponse> createRole(@RequestBody RoleCreationRequest request){
        return EntityResponse.<RoleResponse>builder()
                .status(true)
                .body(roleService.createRole(request))
                .build();
    }

    @GetMapping
    EntityResponse<List<RoleResponse>> getRoles(){
        return EntityResponse.<List<RoleResponse>>builder()
                .status(true)
                .body(roleService.getRoles())
                .build();
    }

    @DeleteMapping("/{name}")
    EntityResponse<String> deleteRole(@PathVariable("name") String name){
        return EntityResponse.<String>builder()
                .status(true)
                .body("Delete is successful")
                .build();
    }

}
