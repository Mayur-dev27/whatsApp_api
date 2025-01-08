package com.whatsapp.SpringDemo.ServiceImpl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.whatsapp.SpringDemo.Entity.Role;
import com.whatsapp.SpringDemo.Entity.User;
import com.whatsapp.SpringDemo.JWTSecurity.JwtService;
import com.whatsapp.SpringDemo.Repository.RoleRepository;
import com.whatsapp.SpringDemo.Repository.UserRepository;
import com.whatsapp.SpringDemo.RequestDTO.UserRegisRequest;
import com.whatsapp.SpringDemo.RequestDTO.UserRequest;
import com.whatsapp.SpringDemo.ResponseDTO.UserRegisResponse;
import com.whatsapp.SpringDemo.ResponseDTO.UserResponse;
import com.whatsapp.SpringDemo.Service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
	
	@Override
	public UserRegisResponse registerNewUser(UserRegisRequest regisRequest) {
		
		try {
			if (userRepository.findByEmail(regisRequest.getEmail()).isPresent()) {
			    return new UserRegisResponse("User already exits..", 400);
			}

			User user = new User();
			user.setEmail(regisRequest.getEmail());
            user.setPassword(passwordEncoder.encode(regisRequest.getPassword())); // Encode the password

            // Assign default role if roleName is null or handle role assignment logic
            String roleName = regisRequest.getRoleName() != null ? regisRequest.getRoleName() : "USER";
            user.setRoles(Set.of(roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName))));

            // Save the new user to the repository
            userRepository.save(user);

            // Return success response
            return new UserRegisResponse("User registered successfully.", 200);
			
		}catch (Exception e) {
			// Return failure response in case of an error
			return new UserRegisResponse("An error occurred during registration: " + e.getMessage(), 500);
		}
	}

	@Override
	public String verify(UserRegisRequest regisRequest) {
		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(regisRequest.getEmail(),regisRequest.getPassword()));
			
			if(authentication.isAuthenticated()){
				return jwtService.generateToken(regisRequest.getEmail());
			}
		} catch (Exception e) {
	        return "Authentication failed: " + e.getMessage();
	    }

	    return "Authentication failed";

	}

	// Add a role to a user
	public void addRoleToUser(UserRequest request) {
	    try {
	        User user = userRepository.findById(request.getUserId())
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
	            for (Long roleId : request.getRoleIds()) {
	                Role role = roleRepository.findById(roleId)
	                        .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
	                user.getRoles().add(role);
	            }
	            userRepository.save(user);
	        } else {
	            throw new RuntimeException("No roles provided for the user");
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error while adding roles to user: " + e.getMessage());
	    }
	}

	// Remove a role from a user
	public void removeRoleFromUser(UserRequest request) {
	    try {
	        User user = userRepository.findById(request.getUserId())
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
	            for (Long roleId : request.getRoleIds()) {
	                Role role = roleRepository.findById(roleId)
	                        .orElseThrow(() -> new RuntimeException("Role not found with ID: " + roleId));
	                user.getRoles().remove(role);
	            }
	            userRepository.save(user);
	        } else {
	            throw new RuntimeException("No roles provided to remove from the user");
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error while removing roles from user: " + e.getMessage());
	    }
	}

	// Get user by ID
	@Override
	public UserResponse getUserById(UserRequest request) {
	    try {
	        Optional<User> userOptional = userRepository.findById(request.getUserId());

	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            return new UserResponse(user.getUserId(), user.getFullName(), user.getEmail(), user.isOnline());
	        } else {
	            throw new RuntimeException("User not found");
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error while retrieving user: " + e.getMessage());
	    }
	}

	// Update user status (online/offline)
	@Override
	public void updateUserStatus(UserRequest request) {
	    try {
	        Optional<User> userOptional = userRepository.findById(request.getUserId());

	        if (userOptional.isPresent()) {
	            User user = userOptional.get();
	            user.setOnline(request.isOnline());
	            userRepository.save(user);
	        } else {
	            throw new RuntimeException("User not found");
	        }
	    } catch (Exception e) {
	        throw new RuntimeException("Error while updating user status: " + e.getMessage());
	    }
	}

	@Override
	public UserResponse updateUserName(UserRequest request) {
		
		try {
			Optional<User> optinalUser = userRepository.findById(request.getUserId());
			
			if(optinalUser.isPresent()) {
				User user= optinalUser.get();
				user.setFullName(request.getFullName());
				System.out.println("Updated Full Name: " + user.getFullName());  
				userRepository.save(user);
				return new UserResponse(user.getUserId(), user.getFullName(), user.getEmail(), user.isOnline());
			}else {
				throw new RuntimeException("User Not found");
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Error while updating userName..");
		}
	}
	
	public void addContact(Long userId, Long contactId) {
	    try {
	        User user = userRepository.findById(userId)
	                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

	        User contact = userRepository.findById(contactId)
	                .orElseThrow(() -> new RuntimeException("Contact not found with ID: " + contactId));

	        if (user.getContacts().contains(contact)) {
	            throw new RuntimeException("Contact is already in user's contact list.");
	        }

	        // Add contact to user's contact list
	        user.getContacts().add(contact);

	        // Persist the changes
	        userRepository.save(user);
	    } catch (Exception e) {
	        throw new RuntimeException("Error while adding contact: " + e.getMessage());
	    }
	}

	@Override
	public void removeContact(Long userId, Long contactId) {
	    
		try {
		    User user = userRepository.findById(userId)
		            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

		    User contact = userRepository.findById(contactId)
		            .orElseThrow(() -> new RuntimeException("Contact not found with ID: " + contactId));

		    user.getContacts().remove(contact);

		    // Optionally remove the user from the contact's contact set (bi-directional relationship)
//		    contact.getContacts().remove(user);

		    // Save both users to persist the change
		    userRepository.save(user);
		    userRepository.save(contact);
		} catch (Exception e) {
			throw new RuntimeException("Error occure while removing user.."+e.getMessage());
		}
	}


	
	
}
