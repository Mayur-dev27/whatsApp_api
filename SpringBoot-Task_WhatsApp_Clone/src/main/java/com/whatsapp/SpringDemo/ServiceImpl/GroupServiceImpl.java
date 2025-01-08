package com.whatsapp.SpringDemo.ServiceImpl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whatsapp.SpringDemo.Entity.Group;
import com.whatsapp.SpringDemo.Entity.User;
import com.whatsapp.SpringDemo.Repository.GroupRepository;
import com.whatsapp.SpringDemo.Repository.UserRepository;
import com.whatsapp.SpringDemo.RequestDTO.GroupRequest;
import com.whatsapp.SpringDemo.ResponseDTO.GroupResponse;
import com.whatsapp.SpringDemo.Service.GroupService;

@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public GroupResponse createGroup(GroupRequest groupRequest) {
        // Check if a group with the same name already exists
        if (!groupRepository.findByGroupName(groupRequest.getGroupName()).isEmpty()) {
            throw new RuntimeException("Group with this name already exists");
        }

        // Create and save the group
        Group group = new Group();
        group.setGroupName(groupRequest.getGroupName());
        group.setCreatedAt(new Date());
        // Ensure user (creator) is set as admin and member
        if (groupRequest.getUserIds() != null && !groupRequest.getUserIds().isEmpty()) {
            Long creatorId = groupRequest.getUserIds().iterator().next();  // Assume the first user in the list is the creator
            User creator = userRepository.findById(creatorId)
                    .orElseThrow(() -> new RuntimeException("Creator not found"));

            // Add creator as admin and member
            group.getAdmins().add(creator);
            group.getMembers().add(creator);
            creator.getGroups().add(group);
            
            // Add other users (userIds) as members
            for (Long userId : groupRequest.getUserIds()) {
                if (!userId.equals(creatorId)) {  // Avoid adding the creator again as a member
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    group.getMembers().add(user);
                    user.getGroups().add(group);
                }
            }
        }

        // Save the group
        group = groupRepository.save(group);
        userRepository.saveAll(group.getMembers());

        // Return response DTO
        return new GroupResponse(group.getGroupId(), group.getGroupName(), group.getCreatedAt());
    }

    @Override
    public void addMemberToGroup(GroupRequest groupRequest) {
        try {
            Group group = groupRepository.findById(groupRequest.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group with ID " + groupRequest.getGroupId() + " not found"));

            for (Long userId : groupRequest.getUserIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));
                group.getMembers().add(user);
            }

            groupRepository.save(group);
        } catch (Exception e) {
            throw new RuntimeException("Error while adding members to the group: " + e.getMessage());
        }
    }

    @Override
    public void removeMemberFromGroup(GroupRequest groupRequest) {
        try {
            Group group = groupRepository.findById(groupRequest.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group with ID " + groupRequest.getGroupId() + " not found"));

            for (Long userId : groupRequest.getUserIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new RuntimeException("User with ID " + userId + " not found"));
                group.getMembers().remove(user);
            }

            groupRepository.save(group);
        } catch (Exception e) {
            throw new RuntimeException("Error while removing members from the group: " + e.getMessage());
        }
    }

    @Override
    public void addAdminToGroup(GroupRequest groupRequest) {
        try {
            Group group = groupRepository.findById(groupRequest.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group with ID " + groupRequest.getGroupId() + " not found"));

            for (Long adminId : groupRequest.getAdminIds()) {
                User user = userRepository.findById(adminId)
                        .orElseThrow(() -> new RuntimeException("User with ID " + adminId + " not found"));
                group.getAdmins().add(user);
            }

            groupRepository.save(group);
        } catch (Exception e) {
            throw new RuntimeException("Error while adding admins to the group: " + e.getMessage());
        }
    }

    @Override
    public void removeAdminFromGroup(GroupRequest groupRequest) {
        try {
            Group group = groupRepository.findById(groupRequest.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group with ID " + groupRequest.getGroupId() + " not found"));

            for (Long adminId : groupRequest.getAdminIds()) {
                User user = userRepository.findById(adminId)
                        .orElseThrow(() -> new RuntimeException("User with ID " + adminId + " not found"));
                group.getAdmins().remove(user);
            }

            groupRepository.save(group);
        } catch (Exception e) {
            throw new RuntimeException("Error while removing admins from the group: " + e.getMessage());
        }
    }

    @Override
    public GroupResponse getGroupDetails(GroupRequest groupRequest) {
        try {
            Group group = groupRepository.findById(groupRequest.getGroupId())
                    .orElseThrow(() -> new RuntimeException("Group with ID " + groupRequest.getGroupId()+ " not found"));

            return new GroupResponse(
                    group.getGroupId(),
                    group.getGroupName(),
                    group.getCreatedAt()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching group details: " + e.getMessage());
        }
    }

    @Override
    public void deleteGroup(Long groupId) {
        try {
            // Fetch the group by ID
            Group group = groupRepository.findById(groupId)
                    .orElseThrow(() -> new RuntimeException("Group with ID " + groupId + " not found"));

            // Delete the group (this will also delete messages and chats if CascadeType.ALL is set)
            groupRepository.delete(group);

            System.out.println("Group with ID " + groupId + " has been deleted successfully.");
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting group: " + e.getMessage());
        }
    }


}
