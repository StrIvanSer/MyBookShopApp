package com.example.MyBookShopApp.secutiry;

import com.example.MyBookShopApp.data.BalanceTransaction;
import com.example.MyBookShopApp.data.BalanceTransaction.TypeStatus;
import com.example.MyBookShopApp.repo.BalanceTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BalanceTransactionService {

    private final BalanceTransactionRepository balanceTransactionRepository;

    public BalanceTransactionService(BalanceTransactionRepository balanceTransactionRepository) {
        this.balanceTransactionRepository = balanceTransactionRepository;
    }


    public Long saveNewTransaction(Integer sum, BookstoreUserDetails user) {
        BalanceTransaction order = new BalanceTransaction();
        order.setTime(new Date());
        order.setTypeStatus(TypeStatus.PROCESSING);
        order.setUserId(user.getBookstoreUser().getId());
        Long aLong = balanceTransactionRepository.getLastInvId();
        order.setValue(sum);
        order.setInvId(aLong == null ? 1 : aLong + 1);
        balanceTransactionRepository.save(order);
        return order.getInvId();
    }


    public void updateAcceptTransaction(Long invId, TypeStatus typeStatus, String description, String outSum) {
        BalanceTransaction order = balanceTransactionRepository.findByInvId(invId);
        order.setTypeStatus(typeStatus);
        order.setDescription(description);
        balanceTransactionRepository.save(order);
    }

    public List<BalanceTransaction> findBalanceTransactionByUserId(Integer userId) {
        return balanceTransactionRepository.findBalanceTransactionByUserId(userId);
    }
}
