package com.whatsapp.SpringDemo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whatsapp.SpringDemo.Entity.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

	Optional<Chat> findById(Long chatId);
}
