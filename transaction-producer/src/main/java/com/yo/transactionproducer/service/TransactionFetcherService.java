package com.yo.transactionproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yo.transactionproducer.DTO.TransactionDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionFetcherService {
    private final BlockFetcherService blockFetcherService;
    private static final Logger log = LoggerFactory.getLogger(TransactionFetcherService.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Value("${transaction.topic.name}")
    private String topicName;

    @Scheduled(fixedRate = 3000)
    public void fetchTransactionsContinuously() {
        try {
            List<TransactionDTO> transactionDTOs = blockFetcherService.getTransactions();
            if (!transactionDTOs.isEmpty()) {
                log.info("Fetched {} new transactions", transactionDTOs.size());

                transactionDTOs.forEach(tx -> {
                    try {
                        kafkaTemplate.send(topicName, objectMapper.writeValueAsString(tx));
                    } catch (JsonProcessingException e) {
                        log.error("Error serializing transaction {}", tx.getHash(), e);
                    }
                });
            } else {
                log.info("No new transactions found");
            }
        } catch (Exception e) {
            log.error("Error fetching transactions", e);
        }
    }
}
