package com.whatsapp.SpringDemo.Entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Message")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "msgId")
	private Long msgId;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "msgType")
	private String msgType;
	
	@Column(name = "timeStamp")
	private Date timeStamp;
	
	@Column(name = "isRead")
	private boolean isRead;
	
	
	@ManyToOne
	@JoinColumn(name = "sender_Id", nullable = false)
	private User sender;
	
	@ManyToOne
	@JoinColumn(name = "receiver_id", nullable = false)
	private User receiver;
 
	@ManyToOne
	@JoinColumn(name = "group_id", nullable = true)
	private Group group;
	
	@ManyToOne
	@JoinColumn(name = "chat_id", nullable = true)
	private Chat chat; 
}
