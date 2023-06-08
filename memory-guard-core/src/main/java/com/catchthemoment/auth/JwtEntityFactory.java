package com.catchthemoment.auth;

import com.catchthemoment.entity.Role;
import com.catchthemoment.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JwtEntityFactory {

    public static JwtEntity create(User user) {
        return new JwtEntity(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                mapToGrantedAuthorities(new ArrayList<>(user.getRole().ordinal()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(ArrayList<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
