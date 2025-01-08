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
public class MessageResponse {

    private Long msgId;
    private String content;
    private String msgType;
    private Date timeStamp;
    private boolean isRead;
    
//    private UserDTO sender; // Sender details (UserDTO)
//    private GroupDTO group; // Optional (GroupDTO for group messages)
//    private ChatDTO chat; // Optional (ChatDTO for private messages)
}
