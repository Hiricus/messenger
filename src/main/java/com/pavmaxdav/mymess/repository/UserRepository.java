package com.pavmaxdav.mymess.repository;

import com.pavmaxdav.mymess.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    void removeByLogin(String login);
    Optional<User> findUserByLogin(String login);

    @Override
    Optional<User> findById(Integer integer);
}
