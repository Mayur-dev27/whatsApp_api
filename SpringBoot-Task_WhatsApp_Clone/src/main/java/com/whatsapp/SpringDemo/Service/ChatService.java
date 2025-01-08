package com.whatsapp.SpringDemo.Service;

import com.whatsapp.SpringDemo.RequestDTO.ChatRequest;
import com.whatsapp.SpringDemo.ResponseDTO.ChatResponse;

public interface ChatService {

	ChatResponse createChatWithSelection(ChatRequest chatRequest);
	
	public ChatResponse viewChatDetails(Long chatId);
	
	public String deleteChat(Long chatId);
}
