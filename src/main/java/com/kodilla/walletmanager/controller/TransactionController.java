package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.domain.Transaction;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class TransactionController {

    @PostMapping("/create")
    public Transaction create(@RequestBody Transaction transaction){
        return new Transaction();
    }

    @GetMapping("/getAll")
    public List<Transaction> getAll(){
        return new ArrayList<>();
    }

    @GetMapping("/get/{transactionId}")
    public Transaction get(@PathVariable long transactionId){
        return new Transaction();
    }

    @PutMapping("/update")
    public Transaction update(@RequestBody Transaction transaction){
        return new Transaction();
    }

    @DeleteMapping("/delete/{transactionId}")
    public boolean delete(@PathVariable long transactionId){
        return true;
    }
}
