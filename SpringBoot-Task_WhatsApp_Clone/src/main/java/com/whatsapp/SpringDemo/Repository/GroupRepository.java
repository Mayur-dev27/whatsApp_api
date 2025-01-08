package com.whatsapp.SpringDemo.Repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whatsapp.SpringDemo.Entity.Group;



public interface GroupRepository extends JpaRepository<Group, Long> {
	
	Optional<Group> findById(Long groupId);

	List<Group> findByGroupName(String groupName);

}
