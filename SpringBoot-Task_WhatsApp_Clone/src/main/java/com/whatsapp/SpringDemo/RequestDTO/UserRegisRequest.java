package com.whatsapp.SpringDemo.RequestDTO;



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
public class UserRegisRequest {
	
	private Long userId;

    @NotBlank(message = "Username is required")
    private String email;

    @Size(min = 4, message = "Password must be at least 4 characters long")
    private String password;

    private String roleName; // Default can be handled in the service layer if needed
}
