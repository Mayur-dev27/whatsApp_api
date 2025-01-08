package com.whatsapp.SpringDemo.RequestDTO;

import java.util.Date;
import java.util.Set;

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
public class GroupRequest {
	private Long groupId;
	
	@NotBlank(message = "Enter thr groupName.")
	@Size(min = 2,max = 20)
    private String groupName;
	
	private Date createdAt;
	
    private Set<Long> userIds; // List of user IDs for members
    private Set<Long> adminIds; // List of admin user IDs
}
