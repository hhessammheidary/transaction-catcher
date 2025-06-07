package com.yo.transactionproducer.controller;

import com.yo.transactionproducer.DTO.TransactionDTO;
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
    public ResponseEntity<List<TransactionDTO>> getLatestTransactions() {
        try {
            List<TransactionDTO> transactionDTOs = blockFetcher.getTransactions();
            return ResponseEntity.ok(transactionDTOs);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}