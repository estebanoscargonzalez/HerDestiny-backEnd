package com.dev.security;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dev.domain.User;
import com.dev.domain.UsuarioPrincipal;
import com.dev.service.UserService;

import io.jsonwebtoken.lang.Objects;


@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String cdUsuario) throws UsernameNotFoundException {
        User user = userService.obtenerUsuario(cdUsuario).get();
        return UsuarioPrincipal.build(user);
    /*
        Usuario usuario = userService.obtenerCdUsuario(cdUsuario)
                .orElseThrow(() ->
                        new UsernameNotFoundException("El codigo de usuario no se encuentra en BD: "+ cdUsuario));

        Set<GrantedAuthority> authorities = usuario.getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getNombreRol())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(usuario.getEmail(),
                usuario.getPassword(),
                authorities);
    }
    */

    }
}