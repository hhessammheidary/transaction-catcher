package com.yo.transactionconsumer.repository;

import com.yo.transactionconsumer.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TransactionRepo extends MongoRepository<Transaction, String> {
    List<Transaction> findByFrom(String from);
    List<Transaction> findByTo(String to);
    @Query("{ 'from': ?0, 'to': ?1 }")
    List<Transaction> findByFromAndTo(String from, String to);
    @Query("{ '$or': [ { 'from': ?0 }, { 'to': ?1 } ] }")
    List<Transaction> findByFromOrTo(String from, String to);
}