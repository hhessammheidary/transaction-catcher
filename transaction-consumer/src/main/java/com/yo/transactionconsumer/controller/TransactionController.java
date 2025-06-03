package com.yo.transactionconsumer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.transactionconsumer.model.Transaction;
import com.yo.transactionconsumer.repository.TransactionRepo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class TransactionController {
    TransactionRepo transactionRepo;

    @Autowired
    public TransactionController(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

//    @RequestMapping(value = "/")
//    public void redirect(HttpServletResponse response) throws IOException {
//        response.sendRedirect("/swagger-ui.html");
//    }

    @GetMapping("/transactions/from/{address}")
    public List<Transaction> findTransactionByFrom(@PathVariable String address) {
        return transactionRepo.findByFrom(address);
    }

    @GetMapping("/transactions/to/{address}")
    public List<Transaction> findTransactionByTo(@PathVariable String address) {
        return transactionRepo.findByTo(address);
    }

    @GetMapping("/transactions/filter")
    public List<Transaction> findTransaction(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(defaultValue = "or") String op){
        if (op.equals("and")){
            return transactionRepo.findByFromAndTo(from, to);
        }
        else {
            return transactionRepo.findByFromOrTo(from, to);
        }
    }



}
