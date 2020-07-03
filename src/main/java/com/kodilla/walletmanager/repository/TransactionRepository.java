package com.kodilla.walletmanager.repository;

import com.kodilla.walletmanager.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByDate(Date date);

    @Query
    List<Transaction> thisWeek();

    @Query
    List<Transaction> thisMonth();

    @Query
    List<Transaction> selectedMonth(@Param("MONTH") int month, @Param("YEAR") int year);

    @Query
    List<Transaction> betweenDate(@Param("FROMDATE") Date fromDate, @Param("TODATE") Date toDate);
}
