package com.yo.transactionconsumer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.transactionconsumer.model.Transaction;
import com.yo.transactionconsumer.repository.TransactionRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumerService {
    private static final Logger log = LoggerFactory.getLogger(TransactionConsumerService.class);
    private final ObjectMapper objectMapper;
    private final TransactionRepo repository;

    @Autowired
    public TransactionConsumerService(ObjectMapper objectMapper,  TransactionRepo repository) {
        this.objectMapper = objectMapper;
        this.repository = repository;
    }

    @KafkaListener(topics = "${transaction.topic.name}", groupId = "transaction-group1")
    public void consumeTransaction(String transactionJson) {
        try {
            Transaction transaction = objectMapper.readValue(transactionJson, Transaction.class);
            log.info("Consumed transaction: {}", transaction.getHash());
            processTransaction(transaction);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing transaction JSON", e);
        }
    }

    private void processTransaction(Transaction transaction) {
        repository.save(transaction);
        System.out.println("Processing transaction from: " + transaction.getFrom());
        System.out.println("To: " + transaction.getTo());
        System.out.println("Value: " + transaction.getValue());
    }
}