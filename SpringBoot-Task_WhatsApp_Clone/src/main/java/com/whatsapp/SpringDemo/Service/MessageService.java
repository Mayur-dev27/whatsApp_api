package com.whatsapp.SpringDemo.Service;

import java.util.List;

import com.whatsapp.SpringDemo.RequestDTO.MessageRequest;
import com.whatsapp.SpringDemo.RequestDTO.SerachRequest;
import com.whatsapp.SpringDemo.ResponseDTO.MessageResponse;

public interface MessageService {

    // Method to create a new message (private or group chat)
    MessageResponse createMessage(MessageRequest messageRequest);

    // Method to view message details by messageId
    MessageResponse viewMessage(Long messageId);

    // Method to mark a message as read
    String markMessageAsRead(Long messageId);

    // Method to delete a message by messageId
    String deleteMessage(Long messageId);
    
    
    public List<MessageResponse> searchMessages(SerachRequest request);
}
