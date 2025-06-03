package com.yo.transactionproducer.DTO;

import java.math.BigInteger;

public class TransactionDTO {
    private String hash;
    private String from;
    private String to;
    private BigInteger value;
    private BigInteger gasPrice;
}
