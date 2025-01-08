package com.whatsapp.SpringDemo.Entity;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "'Group'")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "groupId")
	private Long groupId;
	
	@Column(name = "groupName")
	private String groupName;
	
	@Column(name = "createdAt")
	private Date createdAt;
	
	@ManyToMany(mappedBy = "groups")
	private Set<User> members = new HashSet<>();
	
	@OneToMany(mappedBy = "group")
	private List<Message> messages = new ArrayList<>();
	
    // One-to-Many relationship with Chat entity (group chats)
    @OneToMany(mappedBy = "group")
    private Set<Chat> chats = new HashSet<>();
	
	
	@ManyToMany(fetch = FetchType.EAGER)                         // for admins table 
	@JoinTable(
	    name = "group_admins",                // Name of the join table in the database
	    joinColumns = @JoinColumn(name = "group_id"),  // Foreign key referencing the Group entity
	    inverseJoinColumns = @JoinColumn(name = "user_id") // Foreign key referencing the User entity
	)
	private Set<User> admins = new HashSet<>();

}
