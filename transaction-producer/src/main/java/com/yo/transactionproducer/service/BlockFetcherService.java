package com.yo.transactionproducer.service;

import com.yo.transactionproducer.DTO.TransactionDTO;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class BlockFetcherService {
    @Value("${infura.endpoint}")
    String url;
    private Web3j web3j;
    private String currentBlockNumber;

    @PostConstruct
    public void init() throws IOException, ExecutionException, InterruptedException {
        this.web3j = Web3j.build(new HttpService(url));
        this.currentBlockNumber = "";
    }



    public String getBlockNumber() throws ExecutionException, InterruptedException {
        return web3j.ethBlockNumber()
                .sendAsync()
                .get()
                .getBlockNumber()
                .toString();
    }

    public EthBlock.Block getBlock() throws IOException, ExecutionException, InterruptedException {
        String latestBlockNumber = getBlockNumber();
        if (latestBlockNumber.equals(currentBlockNumber)) {
            return null;
        }
        return web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true)
                .send()
                .getBlock();
    }

    public List<TransactionDTO> getTransactions() throws IOException, ExecutionException, InterruptedException {
        EthBlock.Block block = getBlock();
        if (block == null) {
            return Collections.emptyList();
        }

        this.currentBlockNumber = block.getNumber().toString();

        return block.getTransactions().stream()
                .map(txResult -> {
                    EthBlock.TransactionObject tx = (EthBlock.TransactionObject) txResult.get();
                    return new TransactionDTO(
                            tx.getBlockHash(),
                            tx.getBlockNumber(),
                            tx.getChainId(),
                            tx.getFrom(),
                            tx.getGas(),
                            tx.getGasPrice(),
                            tx.getHash(),
                            tx.getInput(),
                            tx.getNonce(),
                            tx.getR(),
                            tx.getS(),
                            tx.getTo(),
                            tx.getTransactionIndex(),
                            tx.getV(),
                            tx.getValue()
                    );
                })
                .collect(Collectors.toList());
    }
}