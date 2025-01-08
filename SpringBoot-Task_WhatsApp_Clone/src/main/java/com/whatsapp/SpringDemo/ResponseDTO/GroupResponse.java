package com.whatsapp.SpringDemo.ResponseDTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponse {

    private Long groupId;
    private String groupName;
    private Date createdAt;
//    private Set<UserDTO> members; // List of members (UserDTO)
//    private Set<MessageResponseDTO> messages; // List of messages in the group
//    private Set<ChatDTO> chats; // List of group chats
//    private Set<UserDTO> admins; // List of admins (UserDTO)
    
}
