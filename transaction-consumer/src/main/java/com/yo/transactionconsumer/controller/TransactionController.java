package com.yo.transactionconsumer.controller;

import com.yo.transactionconsumer.DTO.TransactionDTO;
import com.yo.transactionconsumer.model.Transaction;
import com.yo.transactionconsumer.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TransactionController {
    TransactionRepo transactionRepo;

    @Autowired
    public TransactionController(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    private TransactionDTO convertToDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getHash(),
                transaction.getFrom(),
                transaction.getTo(),
                transaction.getValue(),
                transaction.getGasPrice()
        );
    }

    @GetMapping("/transactions/from/{address}")
    public ResponseEntity<List<TransactionDTO>> findTransactionByFrom(@PathVariable String address) {
        List<TransactionDTO> dtos = transactionRepo.findByFrom(address)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return dtos.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/transactions/to/{address}")
    public ResponseEntity<List<TransactionDTO>> findTransactionByTo(@PathVariable String address) {
        List<TransactionDTO> dtos = transactionRepo.findByTo(address)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return dtos.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/transactions/filter")
    public ResponseEntity<List<TransactionDTO>> findTransaction(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam(defaultValue = "or") String op) {

        List<Transaction> transactions = op.equals("and")
                ? transactionRepo.findByFromAndTo(from, to)
                : transactionRepo.findByFromOrTo(from, to);

        List<TransactionDTO> dtos = transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return dtos.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}