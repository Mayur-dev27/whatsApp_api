package com.whatsapp.SpringDemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.SpringDemo.RequestDTO.ChatRequest;
import com.whatsapp.SpringDemo.ResponseDTO.ChatResponse;
import com.whatsapp.SpringDemo.Service.ChatService;

@RestController
@RequestMapping("/chats")
public class ChatController {

	@Autowired
	private ChatService chatService;
	
	
	@PostMapping("/createSelectedChat")
	public ResponseEntity<ChatResponse> createChat(@RequestBody ChatRequest request){
		ChatResponse response = chatService.createChatWithSelection(request);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/getChatDetailsById/{chatId}")
	public ResponseEntity<ChatResponse> getChatDetailsById(@PathVariable Long chatId){
		ChatResponse response = chatService.viewChatDetails(chatId);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/deleteChatById/{chatId}")
	public String deleteChatById(@PathVariable Long chatId){
		return chatService.deleteChat(chatId);
	}
	
	
}
