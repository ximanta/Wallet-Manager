package com.kodilla.walletmanager.repository;

import com.kodilla.walletmanager.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query
    Optional<User> get(@Param("LOGIN") String login,@Param("PASSWORD")String password);

    @Query
    Optional<User> getByLogin(@Param("LOGIN") String login);
}
