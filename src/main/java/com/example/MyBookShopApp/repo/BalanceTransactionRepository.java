package com.example.MyBookShopApp.repo;

import com.example.MyBookShopApp.data.BalanceTransaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BalanceTransactionRepository extends JpaRepository<BalanceTransaction, Integer> {

    @Query(value = "SELECT inv_id FROM public.balance_transaction ORDER BY inv_id DESC LIMIT 1", nativeQuery = true)
    Long getLastInvId();

    BalanceTransaction findByInvId(Long invId);

    List<BalanceTransaction> findBalanceTransactionByUserId(Integer userId);

    @Query(value = "SELECT sum(t.value) FROM balance_transaction AS t WHERE user_id = :userId AND t.type_status = 1 ", nativeQuery = true)
    Integer getAccountMoney(@Param("userId") Integer userId);
}
