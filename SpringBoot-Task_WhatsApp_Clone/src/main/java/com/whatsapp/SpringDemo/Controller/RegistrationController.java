package com.whatsapp.SpringDemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.SpringDemo.RequestDTO.UserRegisRequest;
import com.whatsapp.SpringDemo.RequestDTO.UserRequest;
import com.whatsapp.SpringDemo.ResponseDTO.UserRegisResponse;
import com.whatsapp.SpringDemo.ResponseDTO.UserResponse;
import com.whatsapp.SpringDemo.Service.UserService;


@RestController
@RequestMapping("/auth")
public class RegistrationController {

	@Autowired
	private UserService registrationService;
	
	@PostMapping("/register/user")
	public ResponseEntity<UserRegisResponse> registerNewUser(@RequestBody UserRegisRequest regisRequest) {
		UserRegisResponse regisResponse = registrationService.registerNewUser(regisRequest);
		
		return new ResponseEntity<>(regisResponse, HttpStatus.valueOf(regisResponse.getStatusCode()));
	}
	
	@PostMapping("/login/user")
	public String loginUser(@RequestBody UserRegisRequest regisRequest) {
		return registrationService.verify(regisRequest);
	}
	
    // Add a role to a user
    @PutMapping("/user/addRole")
    public ResponseEntity<String> addRoleToUser(@RequestBody UserRequest userRequest) {
        userRequest.setUserId(userRequest.getUserId());
        try {
        	registrationService.addRoleToUser(userRequest);
            return ResponseEntity.ok("Role added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding role: " + e.getMessage());
        }
    }

    // Remove a role from a user
    @PutMapping("/user/removeRole")
    public ResponseEntity<String> removeRoleFromUser(@RequestBody UserRequest userRequest) {
        userRequest.setUserId(userRequest.getUserId());
        try {
        	registrationService.removeRoleFromUser(userRequest);
            return ResponseEntity.ok("Role removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error removing role: " + e.getMessage());
        }
    }

    // Get user by ID
    @GetMapping("/user/getUserById")
    public ResponseEntity<UserResponse> getUserById(@RequestBody UserRequest request) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(request.getUserId());
        try {
            UserResponse userResponse = registrationService.getUserById(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new UserResponse());
        }
    }

    // Update user status (online/offline)
    @PutMapping("/user/updateUserStatus")
    public ResponseEntity<String> updateUserStatus(@RequestBody UserRequest request) {
        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(request.getUserId());
        try {
        	registrationService.updateUserStatus(userRequest);
            return ResponseEntity.ok("User status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error updating status: " + e.getMessage());
        }
    }
    
    @PutMapping("/user/updateUserFullName")
    public ResponseEntity<UserResponse> updateUserName(@RequestBody UserRequest request){
    	UserResponse userResponse = registrationService.updateUserName(request);
    	return ResponseEntity.ok(userResponse);
    }
    
    @PostMapping("/addUsers/users/{userId}/contacts/{contactId}")
    public ResponseEntity<String> addContact(@PathVariable Long userId, @PathVariable Long contactId) {
        try {
            registrationService.addContact(userId, contactId);
            return ResponseEntity.ok("Contact added successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/removeUser/{userId}/contacts/{contactId}")
    public ResponseEntity<String> removeContact(@PathVariable Long userId, @PathVariable Long contactId) {
        try {
        	registrationService.removeContact(userId, contactId);
            return ResponseEntity.ok("Contact removed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
