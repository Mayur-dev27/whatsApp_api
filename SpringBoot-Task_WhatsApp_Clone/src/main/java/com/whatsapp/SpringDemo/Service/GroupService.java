package com.whatsapp.SpringDemo.Service;

import com.whatsapp.SpringDemo.RequestDTO.GroupRequest;
import com.whatsapp.SpringDemo.ResponseDTO.GroupResponse;

public interface GroupService {
	
    GroupResponse createGroup(GroupRequest groupRequest);

    void addMemberToGroup(GroupRequest groupRequest);

    void removeMemberFromGroup(GroupRequest groupRequest);

    void addAdminToGroup(GroupRequest groupRequest);

    void removeAdminFromGroup(GroupRequest groupRequest);

    GroupResponse getGroupDetails(GroupRequest groupRequest);
    
    public void deleteGroup(Long groupId);
}
