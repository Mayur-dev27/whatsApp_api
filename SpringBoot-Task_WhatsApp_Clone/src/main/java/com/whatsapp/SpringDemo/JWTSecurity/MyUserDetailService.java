package com.whatsapp.SpringDemo.JWTSecurity;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.whatsapp.SpringDemo.Entity.User;
import com.whatsapp.SpringDemo.Repository.UserRepository;


@Service
public class MyUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: "+email));
		
		// give user token if his block
//		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),getAuthorities(user));
		
		return new UserDetail(user);
	}
	

//    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
//        // Your logic to fetch authorities (roles/permissions)
//        return user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.getRoleName()))  // Assuming Role has a method getRoleName()
//                .toList(); 
//    }

	
	
}
