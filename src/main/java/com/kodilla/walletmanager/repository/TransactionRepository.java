package com.kodilla.walletmanager.repository;

import com.kodilla.walletmanager.domain.entities.Transaction;
import com.kodilla.walletmanager.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByUser(User user);

    @Query
    List<Transaction> thisWeek(@Param("USER")User user);

    @Query
    List<Transaction> thisMonth(@Param("USER")User user);

    @Query
    List<Transaction> betweenDate(@Param("FROMDATE") Date fromDate, @Param("TODATE") Date toDate, @Param("USER")User user);
    
}
