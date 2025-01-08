package com.whatsapp.SpringDemo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whatsapp.SpringDemo.Entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

}
