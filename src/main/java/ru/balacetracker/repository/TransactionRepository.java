package ru.balacetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.balacetracker.model.jpa.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT (count(t) > 0) FROM Transaction t " +
            "WHERE " +
            "t.id = :transactionId " +
            "AND t.userId = :userId " +
            "AND t.isDeleted = false " +
            "AND t.isDisplayed = true")
    boolean isAccessible(String userId, Long transactionId);
}
