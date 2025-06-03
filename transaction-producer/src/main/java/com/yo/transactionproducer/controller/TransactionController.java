package com.yo.transactionproducer.controller;

import com.yo.transactionproducer.model.Transaction;
import com.yo.transactionproducer.service.BlockFetcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final BlockFetcherService blockFetcher;

    @Autowired
    public TransactionController(BlockFetcherService blockFetcher) {
        this.blockFetcher = blockFetcher;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getLatestTransactions() {
        try {
            List<Transaction> transactions = blockFetcher.getTransactions();
            return ResponseEntity.ok(transactions);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}