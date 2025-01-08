package com.whatsapp.SpringDemo.Controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.SpringDemo.RequestDTO.MessageRequest;
import com.whatsapp.SpringDemo.RequestDTO.SerachRequest;
import com.whatsapp.SpringDemo.ResponseDTO.MessageResponse;
import com.whatsapp.SpringDemo.Service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;
	

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
	
	
	@PostMapping("/createMessage")
	public ResponseEntity<MessageResponse> createMessage(@RequestBody MessageRequest request){
		MessageResponse response = messageService.createMessage(request);
		if(response!=null) {
		return  new ResponseEntity<>(response,HttpStatus.CREATED);
		}
		else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/getMessageById/{msgId}")
	public ResponseEntity<MessageResponse> getMessageById(@PathVariable Long msgId){
		MessageResponse response = messageService.viewMessage(msgId);
		if(response != null) {
		return new ResponseEntity<>(response,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/markMessageReadById/{msgId}")
	public String markMessageAsRead(@PathVariable Long msgId) {
		return messageService.markMessageAsRead(msgId);
	}
	
	@DeleteMapping("/deleteMessageById/{msgId}")
	public String deleteMessageById(@PathVariable Long msgId) {
		return messageService.deleteMessage(msgId);
	}
	
	
	@PostMapping("/serachMessage")
	public ResponseEntity<List<MessageResponse>> serachMsgFilter(@RequestBody SerachRequest request){
		List<MessageResponse> messages = messageService.searchMessages(request);
//		return ResponseEntity.ok(messages);
		if(messages != null) {
		return new ResponseEntity<>(messages,HttpStatus.OK);
		}
		else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	
	
	
	
	
	
	
	
	
    // WebSocket endpoint for creating a message
//    @MessageMapping("/sendMessage") // Matches "/app/sendMessage" via WebSocket
//    @SendTo("/topic/messages") // Broadcasts to "/topic/messages" for subscribers
//    public MessageResponse sendMessage(MessageRequest messageRequest) {
//        // Use the service layer to process the message
//        return messageService.createMessage(messageRequest);
//    }


    @MessageMapping("/sendMessage") // Matches "/app/sendMessage" via WebSocket
    public void sendMessage(MessageRequest messageRequest) {
        // Create the message using the service layer
        MessageResponse response = messageService.createMessage(messageRequest);

        // Broadcast to specific topic (private or group chat)
        if (messageRequest.getGroupId() != null) {
            // Group chat: Broadcast to group topic
            messagingTemplate.convertAndSend("/topic/group/" + messageRequest.getGroupId(), response);
        } else {
            // Private chat: Broadcast to user topic
            messagingTemplate.convertAndSend("/topic/user/" + messageRequest.getReceiverId(), response);
        }
    }
    
 
}
