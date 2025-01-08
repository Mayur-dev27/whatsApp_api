package com.whatsapp.SpringDemo.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whatsapp.SpringDemo.Entity.Chat;
import com.whatsapp.SpringDemo.Entity.Group;
import com.whatsapp.SpringDemo.Entity.Message;
import com.whatsapp.SpringDemo.Entity.User;
import com.whatsapp.SpringDemo.Repository.ChatRepository;
import com.whatsapp.SpringDemo.Repository.GroupRepository;
import com.whatsapp.SpringDemo.Repository.MessageRepository;
import com.whatsapp.SpringDemo.Repository.UserRepository;
import com.whatsapp.SpringDemo.RequestDTO.MessageRequest;
import com.whatsapp.SpringDemo.RequestDTO.SerachRequest;
import com.whatsapp.SpringDemo.ResponseDTO.MessageResponse;
import com.whatsapp.SpringDemo.Service.MessageService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private GroupRepository groupRepository;
    
    @Autowired
    private EntityManager entityManager;

    // Create a new message (private or group chat)
    @Override
    public MessageResponse createMessage(MessageRequest messageRequest) {
        try {
            // Validate sender
            Optional<User> sender = userRepository.findById(messageRequest.getSenderId());
            if (sender.isEmpty()) {
                throw new RuntimeException("Sender not found with id: " + messageRequest.getSenderId());
            }

            // Fetch receiver for private chat (if applicable)
            Optional<User> receiver = Optional.empty();
            if (messageRequest.getReceiverId() != null) {
                receiver = userRepository.findById(messageRequest.getReceiverId());
                if (receiver.isEmpty()) {
                    throw new RuntimeException("Receiver not found with id: " + messageRequest.getReceiverId());
                }
            }

            // Fetch chat or group
            Optional<Chat> chat = Optional.empty();
            Optional<Group> group = Optional.empty();

            if (messageRequest.getChatId() != null) {
                chat = chatRepository.findById(messageRequest.getChatId());
                if (chat.isEmpty()) {
                    throw new RuntimeException("Chat not found with id: " + messageRequest.getChatId());
                }
            }

            if (messageRequest.getGroupId() != null) {
                group = groupRepository.findById(messageRequest.getGroupId());
                if (group.isEmpty()) {
                    throw new RuntimeException("Group not found with id: " + messageRequest.getGroupId());
                }
            }

            // Ensure valid message type (private or group)
            if (messageRequest.getReceiverId() != null && messageRequest.getGroupId() != null) {
                throw new RuntimeException("Cannot send a message to both a receiver and a group simultaneously.");
            }

            // Create message
            Message message = new Message();
            message.setContent(messageRequest.getContent());
            message.setMsgType(messageRequest.getMsgType());
            message.setTimeStamp(new Date());
            message.setRead(false);
            message.setSender(sender.get());

            if (receiver.isPresent()) {
                // Private chat: Set receiver and chat
                message.setReceiver(receiver.get());
                message.setChat(chat.orElseThrow(() -> new RuntimeException("Chat is required for private messages.")));
            } else if (group.isPresent()) {
                // Group chat: Set group
                message.setGroup(group.get());
            } else {
                throw new RuntimeException("Either receiverId or groupId must be provided.");
            }

            // Save the message
            message = messageRepository.save(message);

            // Return response
            return new MessageResponse(
                message.getMsgId(),
                message.getContent(),
                message.getMsgType(),
                message.getTimeStamp(),
                message.isRead()
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error while creating the message: " + e.getMessage());
        }
    }




    // View message details by messageId
    @Override
    public MessageResponse viewMessage(Long messageId) {
        try {
            Message message = messageRepository.findById(messageId)
                    .orElseThrow(() -> new RuntimeException("Message not found with id: " + messageId));

            // Convert Message entity to MessageResponse DTO
            return new MessageResponse(message.getMsgId(), message.getContent(), message.getMsgType(),
                    message.getTimeStamp(), message.isRead());

        } catch (Exception e) {
            throw new RuntimeException("Error while retrieving message with id " + messageId + ": " + e.getMessage());
        }
    }

    // Mark a message as read
    @Override
    public String markMessageAsRead(Long messageId) {
        try {
            Message message = messageRepository.findById(messageId)
                    .orElseThrow(() -> new RuntimeException("Message not found with id: " + messageId));

            message.setRead(true);
            messageRepository.save(message);

            return "Message with id " + messageId + " marked as read.";

        } catch (Exception e) {
            throw new RuntimeException("Error while marking message as read: " + e.getMessage());
        }
    }

    // Delete a message by messageId
    @Override
    public String deleteMessage(Long messageId) {
        try {
            Message message = messageRepository.findById(messageId)
                    .orElseThrow(() -> new RuntimeException("Message not found with id: " + messageId));

            messageRepository.delete(message);

            return "Message with id " + messageId + " deleted successfully.";

        } catch (Exception e) {
            throw new RuntimeException("Error while deleting message with id " + messageId + ": " + e.getMessage());
        }
    }

    @Override
    public List<MessageResponse> searchMessages(SerachRequest request){
    	String field = request.getField();
    	String value = request.getValue();
    	String condition = request.getCondition();
    	int page = request.getPage();
    	int size = request.getSize();
    	
    	try {
    		String query = "select m from Message m where ";
    		switch (condition.toLowerCase()) {
			case "stratswith":
				query+="m."+field+" LIKE :value";
				value = value+"%";
				break;
			case "endswith":
				query+="m."+field+" LIKE :value";
				value="%"+value;
				break;
			case "contains":
				query+="m."+field+" LIKE :value";
				value = "%"+value+"%";
				break;
			case "exact":
				query+="m."+field+" = :value";
				break;
			default:
				throw new IllegalArgumentException("Invalid condition: "+condition);
			}
    		TypedQuery<Message> q = entityManager.createQuery(query, Message.class);
    		q.setParameter("value", value);
    		q.setFirstResult(page*size);
    		q.setMaxResults(size);
    		
    		
    		List<Message> messages= q.getResultList();
    		
    		return messages.stream().
    				map(msg -> new MessageResponse(msg.getMsgId(),msg.getContent(),msg.getMsgType(),msg.getTimeStamp(),msg.isRead()))
    				.collect(Collectors.toList());
    	}
    	catch (Exception e) {
			throw new RuntimeException("Error while in searchMeassage impl.."+e.getMessage());
		}   	
    }
    
}
