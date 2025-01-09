package com.whatsapp.SpringDemo.Service;

import java.util.List;

import com.whatsapp.SpringDemo.RequestDTO.GroupRequest;
import com.whatsapp.SpringDemo.RequestDTO.SerachRequest;
import com.whatsapp.SpringDemo.ResponseDTO.GroupResponse;

public interface GroupService {
	
    GroupResponse createGroup(GroupRequest groupRequest);

    void addMemberToGroup(GroupRequest groupRequest);

    void removeMemberFromGroup(GroupRequest groupRequest);

    void addAdminToGroup(GroupRequest groupRequest);

    void removeAdminFromGroup(GroupRequest groupRequest);

    GroupResponse getGroupDetails(GroupRequest groupRequest);
    
    public void deleteGroup(Long groupId);
    
    public List<GroupResponse> searchGroup(SerachRequest request);
}
