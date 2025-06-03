package com.yo.transactionproducer.model;

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
@AllArgsConstructor
public class Transaction {
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
}
