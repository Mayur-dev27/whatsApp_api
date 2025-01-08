package com.whatsapp.SpringDemo.Service;

import java.util.List;

import com.whatsapp.SpringDemo.RequestDTO.SerachRequest;
import com.whatsapp.SpringDemo.RequestDTO.UserRegisRequest;
import com.whatsapp.SpringDemo.RequestDTO.UserRequest;
import com.whatsapp.SpringDemo.ResponseDTO.UserRegisResponse;
import com.whatsapp.SpringDemo.ResponseDTO.UserResponse;

public interface UserService {

	public UserRegisResponse registerNewUser(UserRegisRequest regisRequest);
	
	public String verify(UserRegisRequest regisRequest);
	
	public UserResponse getUserById(UserRequest request);

	public UserResponse updateUserName(UserRequest request);
	
	public void updateUserStatus(UserRequest request);
	
	public void addRoleToUser(UserRequest request);
	
	public void removeRoleFromUser(UserRequest request);
	
	public void addContact(Long userId, Long contactId);
	
	public void removeContact(Long userId, Long contactId);
	
	public List<UserResponse> searchUserFilter(SerachRequest request);
}
