package com.whatsapp.SpringDemo.Entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Chat")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "chatId")
	private Long chatId;
	
	@Column(name = "chatType")
	private String chatType;    // grp or private
	
	
	@OneToMany(mappedBy = "chat")
	private List<Message> messages = new ArrayList<>();

	
    // One-to-Many relationship with User (for Private Chat)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_chats",
        joinColumns = @JoinColumn(name = "chat_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();
    
    // Many-to-one relationship with Group (for Group Chat)
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

}
