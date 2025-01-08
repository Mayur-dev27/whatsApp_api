package com.whatsapp.SpringDemo.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whatsapp.SpringDemo.Entity.Chat;
import com.whatsapp.SpringDemo.Entity.Group;
import com.whatsapp.SpringDemo.Entity.User;
import com.whatsapp.SpringDemo.Repository.ChatRepository;
import com.whatsapp.SpringDemo.Repository.GroupRepository;
import com.whatsapp.SpringDemo.Repository.UserRepository;
import com.whatsapp.SpringDemo.RequestDTO.ChatRequest;
import com.whatsapp.SpringDemo.ResponseDTO.ChatResponse;
import com.whatsapp.SpringDemo.Service.ChatService;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public ChatResponse createChatWithSelection(ChatRequest chatRequest) {
        try {
            // If the user selected 'Private' chat type (one-on-one)
            if ("private".equalsIgnoreCase(chatRequest.getChatType())) {
                return createPrivateChat(chatRequest);
            }

            // If the user selected 'Group' chat type
            if ("group".equalsIgnoreCase(chatRequest.getChatType())) {
                return createGroupChat(chatRequest);
            }

            // If chat type is invalid, throw an exception
            throw new RuntimeException("Invalid chat type. Please select either 'private' or 'group'.");

        } catch (Exception e) {
            throw new RuntimeException("Error while creating chat: " + e.getMessage());
        }
    }

    private ChatResponse createPrivateChat(ChatRequest chatRequest) {
        try {
            // Validate user selection
            if (chatRequest.getUserIds() == null || chatRequest.getUserIds().size() != 2) {
                throw new RuntimeException("Private chat requires exactly two users.");
            }

            // Fetch users from database
            List<User> users = userRepository.findAllById(chatRequest.getUserIds());
            if (users.size() != 2) {
                throw new RuntimeException("One or more users not found.");
            }

            // Create the private chat
            Chat chat = new Chat();
            chat.setChatType("private");
            chat.setUsers(users);

            // Update the `users` relationship
            for (User user : users) {
                user.getChats().add(chat); // Add the chat to the user's chat list
            }

            // Save the chat and update users
            chat = chatRepository.save(chat);
            userRepository.saveAll(users);
//            chat = chatRepository.save(chat);

            // Return the chat response
            return new ChatResponse(chat.getChatId(), chat.getChatType());

        } catch (Exception e) {
            throw new RuntimeException("Error while creating private chat: " + e.getMessage());
        }
    }

    private ChatResponse createGroupChat(ChatRequest chatRequest) {
        try {
            // Fetch group from the database
            Group group = groupRepository.findById(chatRequest.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group not found."));

            // Fetch the user initiating the chat (Assuming the user ID is part of the request)
            User initiatingUser = userRepository.findById(chatRequest.getUserIds().iterator().next()) // Assuming the first user in the list is initiating
                    .orElseThrow(() -> new RuntimeException("User not found."));

            // Check if the initiating user is part of the group
            if (!group.getMembers().contains(initiatingUser)) {
                throw new RuntimeException("User is not a member of the group.");
            }

            // Create the group chat
            Chat chat = new Chat();
            chat.setChatType("group");
            chat.setGroup(group);

//            chat = chatRepository.save(chat);
         // Add all group members to the chat
            List<User> groupMembers = new ArrayList<>(group.getMembers());
            chat.setUsers(groupMembers);

            // Update the `users` relationship
            for (User user : groupMembers) {
                user.getChats().add(chat); // Add the chat to each user's chat list
            }

            // Save the chat and update users
            chat = chatRepository.save(chat);
            userRepository.saveAll(groupMembers);

            // Return the chat response
            return new ChatResponse(chat.getChatId(), chat.getChatType());

        } catch (Exception e) {
            throw new RuntimeException("Error while creating group chat: " + e.getMessage());
        }
    }

    @Override
    public ChatResponse viewChatDetails(Long chatId) {
        try {
            Chat chat = chatRepository.findById(chatId)
                    .orElseThrow(() -> new RuntimeException("Chat not found with id: " + chatId));

            // Convert Chat entity to ChatResponse DTO
            return new ChatResponse(chat.getChatId(), chat.getChatType());

        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving chat details: " + e.getMessage());
        }
    }

    // Delete Chat by chatId
    @Override
    public String deleteChat(Long chatId) {
        try {
            Chat chat = chatRepository.findById(chatId)
                    .orElseThrow(() -> new RuntimeException("Chat not found with id: " + chatId));

            // Delete the chat from the repository
            chatRepository.delete(chat);
            return "Chat with id " + chatId + " deleted successfully.";

        } catch (Exception e) {
            throw new RuntimeException("Error while deleting chat: " + e.getMessage());
        }
    }   
    
}
