package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.dto.TransactionDto;
import com.kodilla.walletmanager.service.TransactionService;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    //CRUD
    @PostMapping("/create")
    public TransactionDto create(@RequestBody TransactionDto dto){
        return transactionService.create(dto);
    }

    @GetMapping("/getAll")
    public List<TransactionDto> getAll(@RequestParam(value = "type",required = false) String type){
        List<TransactionDto> dtos = transactionService.getAll();
        return ToolsManager.sortByType(dtos,type);
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

    //DATE
    @GetMapping("/byDate/{date}")
    public List<TransactionDto> findByDate(@PathVariable String date,
                                           @RequestParam(value = "type",required = false) String type){
        List<TransactionDto> dtos = transactionService.findByDate(date);
        return ToolsManager.sortByType(dtos,type);
    }

    @GetMapping("/thisWeek")
    public List<TransactionDto> thisWeek(@RequestParam(value = "type",required = false) String type){
        List<TransactionDto> dtos = transactionService.thisWeek();
        return ToolsManager.sortByType(dtos,type);
    }

    @GetMapping("/thisMonth")
    public List<TransactionDto> thisMonth(@RequestParam(value = "type",required = false) String type){
        List<TransactionDto> dtos = transactionService.thisMonth();
        return ToolsManager.sortByType(dtos,type);
    }

    @GetMapping("/selectedMonth/{year_month}")
    public List<TransactionDto> selectedMonth(@PathVariable String year_month,
                                              @RequestParam(value = "type",required = false) String type){
        List<TransactionDto> dtos = transactionService.selectedMonth(year_month);
        return ToolsManager.sortByType(dtos,type);
    }

    @GetMapping("/betweenDate/{fromDate}/{toDate}")
    public List<TransactionDto> betweenDate(@PathVariable String fromDate,
                                            @PathVariable String toDate,
                                            @RequestParam(value = "type",required = false)String type) {
        List<TransactionDto> dtos = transactionService.betweenDate(fromDate, toDate);
        return ToolsManager.sortByType(dtos,type);
    }
}
