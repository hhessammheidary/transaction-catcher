package com.yo.transactionproducer.service;

import com.yo.transactionproducer.model.Transaction;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    String apiKey = "7cef9237ec2d417f8c30145c88013e45";
    String url = "https://mainnet.infura.io/v3/" + apiKey;
    private Web3j web3j;
    private String currentBlockNumber;
    private static final Logger log = LoggerFactory.getLogger(BlockFetcherService.class);

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
//        else if(Integer.parseInt(latestBlockNumber) - Integer.parseInt(currentBlockNumber) > 1){
//            log.info("block {} missed", Integer.parseInt(latestBlockNumber) - 1);
//        }
        return web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true)
                .send()
                .getBlock();
    }

    public List<Transaction> getTransactions() throws IOException, ExecutionException, InterruptedException {
        EthBlock.Block block = getBlock();
        if (block == null) {
            return Collections.emptyList();
        }

        // Update current block number
        this.currentBlockNumber = block.getNumber().toString();

        return block.getTransactions().stream()
                .map(txResult -> {
                    EthBlock.TransactionObject tx = (EthBlock.TransactionObject) txResult.get();
                    return new Transaction(
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