package com.whatsapp.SpringDemo.RequestDTO;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

	private Long userId;
	
	@NotBlank(message = "Name must be filled.")
	@Size(min = 2,max = 20)
    private String fullName;
	
	@Email(message = "Enter correct Email")
    private String email;
	
//	@NotBlank(message = "Enter the Password")
//    private String password;
	
    private boolean isOnline;
    private Set<Long> roleIds; // List of role IDs
    private Set<Long> contactIds; // List of user IDs for contacts
    private Set<Long> groupIds; // List of group IDs
	
}
