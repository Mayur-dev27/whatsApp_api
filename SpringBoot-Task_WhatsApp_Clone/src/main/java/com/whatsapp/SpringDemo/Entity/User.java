package com.whatsapp.SpringDemo.Entity;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
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
@Table(name = "User")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private Long userId;
	
	@Column(name = "fullName")
	private String fullName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "isOnline")
	private boolean isOnline;
	
	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
	private List<Message> sentMessages = new ArrayList<>();
	
    // Messages received by the user
    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<Message> receivedMessages = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.EAGER) 
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "userId"),
			inverseJoinColumns = @JoinColumn(name ="roleId")
			)
	private Set<Role> roles = new HashSet<>();
	
	
	// User Entity  self refrencesing relationship 
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
	    name = "user_contacts",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "contact_id")
	)
	private Set<User> contacts = new HashSet<>();

	
	
	@ManyToMany(mappedBy = "users")
	private List<Chat> chats = new ArrayList<>();    
    
	
	@ManyToMany(fetch = FetchType.EAGER)                 // no. of group members 
	@JoinTable(
	    name = "group_memberships",
	    joinColumns = @JoinColumn(name = "user_id"),
	    inverseJoinColumns = @JoinColumn(name = "group_id")
	)
	private Set<Group> groups = new HashSet<>();
	
}
