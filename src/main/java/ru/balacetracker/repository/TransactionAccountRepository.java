package ru.balacetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.balacetracker.model.jpa.TransactionAccount;

@Repository
public interface TransactionAccountRepository extends JpaRepository<TransactionAccount, Long> {

    @Query("SELECT (count(ta) > 0) FROM TransactionAccount ta " +
            "WHERE " +
            "ta.userId = :userId " +
            "AND ta.id = :transactionAccountId " +
            "AND ta.isDeleted = false")
    boolean isTransactionAccountAccessible(String userId, Long transactionAccountId);

}
