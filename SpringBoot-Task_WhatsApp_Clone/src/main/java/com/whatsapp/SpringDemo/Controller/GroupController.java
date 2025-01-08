package com.whatsapp.SpringDemo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whatsapp.SpringDemo.RequestDTO.GroupRequest;
import com.whatsapp.SpringDemo.ResponseDTO.GroupResponse;
import com.whatsapp.SpringDemo.Service.GroupService;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    // Create a new group
    @PostMapping("/create")
    public ResponseEntity<GroupResponse> createGroup(@RequestBody GroupRequest groupRequest) {
        GroupResponse groupResponse = groupService.createGroup(groupRequest);
        if(groupResponse != null) {
        return new ResponseEntity<>(groupResponse,HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Add members to an existing group
    @PutMapping("/addMembers")
    public ResponseEntity<String> addMembersToGroup(@RequestBody GroupRequest groupRequest) {
        groupService.addMemberToGroup(groupRequest);
        return ResponseEntity.ok("Members added successfully");
    }

    // Remove members from an existing group
    @PutMapping("/removeMembers")
    public ResponseEntity<String> removeMembersFromGroup(@RequestBody GroupRequest groupRequest) {
        groupService.removeMemberFromGroup(groupRequest);
        return ResponseEntity.ok("Members removed successfully");
    }

    // Add admins to an existing group
    @PutMapping("/addAdmins")
    public ResponseEntity<String> addAdminsToGroup(@RequestBody GroupRequest groupRequest) {
        groupService.addAdminToGroup(groupRequest);
        return ResponseEntity.ok("Admins added successfully");
    }

    // Remove admins from an existing group
    @PutMapping("/removeAdmins")
    public ResponseEntity<String> removeAdminsFromGroup(@RequestBody GroupRequest groupRequest) {
        groupService.removeAdminFromGroup(groupRequest);
        return ResponseEntity.ok("Admins removed successfully");
    }

    // Get details of a specific group
    @GetMapping("/getGroupDetails")
    public ResponseEntity<GroupResponse> getGroupDetails(@RequestBody GroupRequest groupRequest) {
        GroupResponse groupResponse = groupService.getGroupDetails(groupRequest);
        return ResponseEntity.ok(groupResponse);
    }
    
    @DeleteMapping("/deleteGroup/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) {
        try {
            groupService.deleteGroup(groupId); // Call the service layer to delete the group
            return new ResponseEntity<>("Group deleted successfully.", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Error deleting group: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    } 
  
}
