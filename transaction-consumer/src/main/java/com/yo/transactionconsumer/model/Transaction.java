package com.yo.transactionconsumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@Document(collection = "transaction")
public class Transaction {
    @MongoId
    private String id;
    private String blockHash;
    private BigInteger blockNumber;
    private Long chainId;
    private String from;
    private BigInteger gas;
    private BigInteger gasPrice;
    private String hash;
    private  String input;
    private  BigInteger nonce;
    private  String r;
    private  String s;
    private  String to;
    private  BigInteger transactionIndex;
    private  long v;
    private  BigInteger value;

//    public Transaction(
//            String blockHash,
//            BigInteger blockNumber,
//            Long chainId,
//            String from,
//            BigInteger gas,
//            BigInteger gasPrice,
//            String hash,
//            String input,
//            BigInteger nonce,
//            String r,
//            String s,
//            String to,
//            BigInteger transactionIndex,
//            long v,
//            BigInteger value
//    ) {
//        this.blockHash = blockHash;
//        this.blockNumber = blockNumber;
//        this.chainId = chainId;
//        this.from = from;
//        this.gas = gas;
//        this.gasPrice = gasPrice;
//        this.hash = hash;
//        this.input = input;
//        this.nonce = nonce;
//        this.r = r;
//        this.s = s;
//        this.to = to;
//        this.transactionIndex = transactionIndex;
//        this.v = v;
//        this.value = value;
//    }
}
