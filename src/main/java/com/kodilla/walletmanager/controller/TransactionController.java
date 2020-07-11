package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.dto.TransactionDto;
import com.kodilla.walletmanager.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/create")
    public TransactionDto create(@RequestBody TransactionDto dto){
        return transactionService.create(dto);
    }

    @GetMapping("/getAll")
    public List<TransactionDto> getAll(){
        return transactionService.getAll();
    }

    @GetMapping("/get/{id}")
    public TransactionDto get(@PathVariable long id){
        return transactionService.get(id);
    }

    @PutMapping("/update")
    public TransactionDto update(@RequestBody TransactionDto dto){
        return transactionService.update(dto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable long id){
        return transactionService.delete(id);
    }
}
