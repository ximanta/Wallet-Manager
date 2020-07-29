package com.kodilla.walletmanager.controller;

import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.service.TransactionService;
import com.kodilla.walletmanager.tools.ToolsManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/transaction")
public class TransactionController {
    final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    //CRUD
    @PostMapping("")
    public TransactionDto create(@RequestBody TransactionDto dto){
        return service.create(dto);
    }

    @GetMapping("/all")
    public List<TransactionDto> getAll(@RequestParam(value = "type",required = false) String type){
        List<TransactionDto> dtos = service.getAll();
        return ToolsManager.sortByTypeT(dtos,type);
    }

    @PutMapping("")
    public TransactionDto update(@RequestBody TransactionDto dto){
        return service.update(dto);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable long id){
        return service.delete(id);
    }

    //DATE

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

    @GetMapping("/{fromDate}/{toDate}")
    public List<TransactionDto> betweenDate(@PathVariable String fromDate,
                                            @PathVariable String toDate,
                                            @RequestParam(value = "type",required = false)String type) {
        List<TransactionDto> dtos = service.betweenDate(fromDate, toDate);
        return ToolsManager.sortByTypeT(dtos,type);
    }
}
