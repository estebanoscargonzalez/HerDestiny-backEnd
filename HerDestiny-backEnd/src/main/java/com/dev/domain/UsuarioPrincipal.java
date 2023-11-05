package com.dev.domain;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.authority.SimpleGrantedAuthority;


@Slf4j
public class UsuarioPrincipal  implements UserDetails {

	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;    

    private String password;    

    private String name;
    
    private String email;
    

    private Collection<? extends GrantedAuthority> authorities;
 
	  
	
    public UsuarioPrincipal(String name, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
	
    
    /**
     * Construye un objeto UsuarioPrncipal a partir de un objeto User.
     * 
     * @param user El objeto User del que se construirá el UsuarioPrincipal.
     * @return El objeto UsuarioPrincipal construido.
     */
    public static UsuarioPrincipal build(User user){
    	List<GrantedAuthority> authorities =
        		user.getRoles().stream().map(rol -> new SimpleGrantedAuthority(
        				rol.getRolNombre())).collect(Collectors.toList());

        return new UsuarioPrincipal(user.getName(), user.getUsername(), user.getEmail(), user.getPassword(), authorities);
    }

    
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
	}

	@Override
	public String getPassword() {
		 log.info("Devolviendo contraseña codificada para el usuario: {}", this.username);
		return password;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}
	
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}