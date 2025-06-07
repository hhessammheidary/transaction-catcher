package com.yo.transactionconsumer.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class TransactionDTO {
    private String hash;
    private String from;
    private String to;
    private BigInteger value;
    private BigInteger gasPrice;
}
