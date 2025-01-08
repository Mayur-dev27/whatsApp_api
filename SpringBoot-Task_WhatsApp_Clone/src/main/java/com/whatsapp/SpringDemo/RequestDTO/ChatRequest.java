package com.whatsapp.SpringDemo.RequestDTO;

import java.util.Set;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {
	
	private Long chatId;
	
	@NotBlank(message = "chat type required")
    private String chatType; // Private or Group chat
	
    private Set<Long> userIds; // List of user IDs for private chat
    
    private Long groupId; // Optional for group chatO
}
