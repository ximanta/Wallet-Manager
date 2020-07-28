package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.service.TransactionService;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService service;

    //CRUD
    @PostMapping("/create")
    public TransactionDto create(@RequestBody TransactionDto dto){
        return service.create(dto);
    }

    @GetMapping("/getAll")
    public List<TransactionDto> getAll(@RequestParam(value = "type",required = false) String type){
        List<TransactionDto> dtos = service.getAll();
        return ToolsManager.sortByTypeT(dtos,type);
    }

    @GetMapping("/get/{id}")
    public TransactionDto get(@PathVariable long id){
        return service.get(id);
    }

    @PutMapping("/update")
    public TransactionDto update(@RequestBody TransactionDto dto){
        return service.update(dto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable long id){
        return service.delete(id);
    }

    //DATE
    @GetMapping("/byDate/{date}")
    public List<TransactionDto> findByDate(@PathVariable String date,
                                           @RequestParam(value = "type",required = false) String type){
        List<TransactionDto> dtos = service.findByDate(date);
        return ToolsManager.sortByTypeT(dtos,type);
    }

    @GetMapping("/thisWeek")
    public List<TransactionDto> thisWeek(@RequestParam(value = "type",required = false) String type){
        List<TransactionDto> dtos = service.thisWeek();
        return ToolsManager.sortByTypeT(dtos,type);
    }

    @GetMapping("/thisMonth")
    public List<TransactionDto> thisMonth(@RequestParam(value = "type",required = false) String type){
        List<TransactionDto> dtos = service.thisMonth();
        return ToolsManager.sortByTypeT(dtos,type);
    }

    @GetMapping("/selectedMonth/{year_month}")
    public List<TransactionDto> selectedMonth(@PathVariable String year_month,
                                              @RequestParam(value = "type",required = false) String type){
        List<TransactionDto> dtos = service.selectedMonth(year_month);
        return ToolsManager.sortByTypeT(dtos,type);
    }

    @GetMapping("/betweenDate/{fromDate}/{toDate}")
    public List<TransactionDto> betweenDate(@PathVariable String fromDate,
                                            @PathVariable String toDate,
                                            @RequestParam(value = "type",required = false)String type) {
        List<TransactionDto> dtos = service.betweenDate(fromDate, toDate);
        return ToolsManager.sortByTypeT(dtos,type);
    }
}
