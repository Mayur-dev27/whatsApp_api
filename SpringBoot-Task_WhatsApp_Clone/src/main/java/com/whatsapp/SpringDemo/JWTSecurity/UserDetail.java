package com.whatsapp.SpringDemo.JWTSecurity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.whatsapp.SpringDemo.Entity.User;

public class UserDetail implements UserDetails{
	
	private User user;
	
	public UserDetail(User user) {
		this.user=user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Your logic to fetch authorities (roles/permissions)
    return user.getRoles().stream()
          .map(role -> new SimpleGrantedAuthority(role.getRoleName()))  // Assuming Role has a method getRoleName()
          .toList(); 
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

}
