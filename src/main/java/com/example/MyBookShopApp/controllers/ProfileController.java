package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.BalanceTransaction;
import com.example.MyBookShopApp.data.BalanceTransaction.TypeStatus;
import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.repo.BalanceTransactionRepository;
import com.example.MyBookShopApp.secutiry.BookstoreUserDetails;
import com.example.MyBookShopApp.secutiry.BookstoreUserRegister;
import com.example.MyBookShopApp.services.BookService;
import com.example.MyBookShopApp.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
public class ProfileController {

    private final BalanceTransactionRepository balanceTransactionRepository;
    private final PaymentService paymentService;
    private final BookService bookService;
    private final BookstoreUserRegister userRegister;

    @Autowired
    public ProfileController(BalanceTransactionRepository balanceTransactionRepository, PaymentService paymentService,
                             BookService bookService, BookstoreUserRegister userRegister) {
        this.balanceTransactionRepository = balanceTransactionRepository;
        this.paymentService = paymentService;
        this.bookService = bookService;
        this.userRegister = userRegister;
    }

    @PostMapping("/payment")
    public RedirectView handlePay(@AuthenticationPrincipal BookstoreUserDetails user, @RequestParam("sum") Integer sum)
            throws NoSuchAlgorithmException {
        BalanceTransaction order = new BalanceTransaction();
        order.setTime(new Date());
        order.setTypeStatus(TypeStatus.PROCESSING);
        order.setUserId(user.getBookstoreUser().getId());
        Long aLong = balanceTransactionRepository.getLastInvId();
        order.setValue(sum);
        order.setInvId(aLong == null ? 1 : aLong + 1);
        balanceTransactionRepository.save(order);
        String paymentUrl = paymentService.getPaymentUrl(sum, order.getInvId());
        return new RedirectView(paymentUrl);
    }

    @GetMapping("/accept/payment/")
    public String handleAccept(@RequestParam("OutSum") String OutSum,
                               @RequestParam("InvId") Long InvId,
                               @RequestParam("SignatureValue") String SignatureValue,
                               @RequestParam("IsTest") String IsTest) {
        BalanceTransaction order = balanceTransactionRepository.findByInvId(InvId);
        order.setTypeStatus(TypeStatus.OK);
        order.setDescription("Пополнение счета через сервис ROBBOKASSA на + " + OutSum + " р.");
        balanceTransactionRepository.save(order);
        return "redirect:http://localhost:8085/profile";
    }

    @GetMapping("/fail/payment/")
    public String handleFail(@RequestParam("OutSum") String OutSum,
                             @RequestParam("InvId") Long InvId,
                             @RequestParam("IsTest") String IsTest) {
        BalanceTransaction order = balanceTransactionRepository.findByInvId(InvId);
        order.setTypeStatus(TypeStatus.FAIL);
        order.setDescription("Не удачное пополнение счета через сервис ROBBOKASSA на " + OutSum + " р.");
        balanceTransactionRepository.save(order);
        return "redirect:http://localhost:8085/profile";
    }

    @GetMapping("/my")
    public String handleMy(Model model,
                           @AuthenticationPrincipal BookstoreUserDetails user,
                           @RequestParam(value = "isPaid", required = false) Boolean isPaid) {
        List<Book> books = bookService.getPaidBooks(user.getBookstoreUser().getId());
        if(nonNull(isPaid) && isPaid){
            model.addAttribute("isPaid", "Эта книга уже куплена");
        }
        model.addAttribute("bookPaid", books);
        return "my";
    }

    @GetMapping("/profile")
    public String handleProfile(Model model) {
        List<BalanceTransaction> balanceTransactions = balanceTransactionRepository
                .findBalanceTransactionByUserId(userRegister.getCurrentUser().getId());
        model.addAttribute("curUsr", userRegister.getCurrentUser());
        model.addAttribute("transactionHistory", balanceTransactions);
        return "profile";
    }


}
