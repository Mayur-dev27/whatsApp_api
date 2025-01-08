package com.whatsapp.SpringDemo.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

    private Long chatId;
    private String chatType;
    
//    private Set<UserDTO> users; // List of users involved in the chat
//    private Set<MessageResponseDTO> messages; // List of messages in the chat
//    private GroupDTO group; // Optional (GroupDTO for group chat)
}
