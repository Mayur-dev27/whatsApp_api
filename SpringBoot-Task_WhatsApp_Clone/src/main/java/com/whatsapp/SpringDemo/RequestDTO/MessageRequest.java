package com.whatsapp.SpringDemo.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {

	private Long messageId;
	
	@NotBlank(message = "Message not blanked")
	@Size(min = 1,max = 50,message = "Message must be between 1-50 chars")
    private String content;
    private String msgType; // Text, Image, Video, etc.
    private Long senderId;  // Sender of the message
    private Long receiverId; // Receiver (for private chat)
    private Long groupId; // Optional, for group messages
    private Long chatId; // Optional, for private chats
}
