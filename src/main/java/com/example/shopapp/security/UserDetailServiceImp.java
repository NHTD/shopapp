package com.example.shopapp.security;

import com.example.shopapp.exception.ShopAppModelsNotFoundException;
import com.example.shopapp.models.User;
import com.example.shopapp.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserDetailServiceImp implements UserDetailsService {

    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new ShopAppModelsNotFoundException("User not found with phone number " + username));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .flatMap(role -> {
                    Set<GrantedAuthority> authoritySet = role.getPermissions().stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                            .collect(Collectors.toSet());
                    authoritySet.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
                    return authoritySet.stream();
                }).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getPhoneNumber(), user.getPassword(), authorities);
    }
}
