package com.whatsapp.SpringDemo.ResponseDTO;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long userId;
    private String fullName;
    private String email;
    private boolean isOnline;
    
//    private Set<String> roles; // List of role names
    
//    private Set<UserContactDTO> contacts; // List of contacts (UserDTO)
//    private Set<ChatDTO> chats; // List of chats
//    private Set<GroupDTO> groups; // List of groups
}
