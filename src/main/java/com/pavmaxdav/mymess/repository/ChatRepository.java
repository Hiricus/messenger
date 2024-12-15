package com.pavmaxdav.mymess.repository;

import com.pavmaxdav.mymess.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {
    @Override
    Optional<Chat> findById(Integer integer);
}
