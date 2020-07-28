package com.kodilla.walletmanager.tools;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ToolsManager {
    public static final String REV = "REV";
    public static final String EXP = "EXP";

    public static double positiveTenthRoundDouble(double d){
        if (d < 0){
            System.out.println("Amount cannot be negative");
            return 0;
        } else {
            return tenthRoundDouble(d);
        }
    }

    public static double tenthRoundDouble(double d){
        if((d * 100) - (long)(d * 100) != 0) {
            double tmp = Math.round(d) * 100;
            return tmp / 100 ;
        } else {
            return d;
        }
    }

    public static boolean isTransactionDtoCorrect(TransactionDto transactionDto){
        boolean isDate = transactionDto.getDate() != null;
        boolean isType = transactionDto.getType() != null;
        boolean isCategory = transactionDto.getCategoryDto() != null;
        boolean isTitleNotBlank = transactionDto.getTitle() != null && !transactionDto.getTitle().isEmpty();

        return isDate && isType && isCategory && isTitleNotBlank;
    }

    public static boolean isMonthCorrect(int month,int year){
        boolean isMonthCorrect = month > 0 && month <= 12;
        boolean isYearCorrect = year >= 1980 && year <= LocalDate.now().getYear();

        return isMonthCorrect && isYearCorrect;
    }

    public static boolean isTheSameEnum(TransactionType first, TransactionType second){
        return  first != null && first == second;
    }

    public static List<TransactionDto> sortByTypeT(List<TransactionDto> dtos, String type){
        if (type == null){
            return dtos;
        }else {
            switch (type){
                case REV:
                    List<TransactionDto> revenues = new ArrayList<>();
                    for (TransactionDto dto:dtos) {
                        if (dto.getType() == TransactionType.REVENUES){
                            revenues.add(dto);
                        }
                    }
                    return revenues;
                case EXP:
                    List<TransactionDto> expenses = new ArrayList<>();
                    for (TransactionDto dto:dtos) {
                        if (dto.getType().equals(TransactionType.EXPENSES)){
                            expenses.add(dto);
                        }
                    }
                    return expenses;
                default:
                    return new ArrayList<>();
            }
        }
    }

    public static List<CategoryDto> sortByTypeC(List<CategoryDto> list, String type){
        if (type == null){
            return list;
        }else {
            switch (type){
                case REV:
                    List<CategoryDto> revenues = new ArrayList<>();
                    for (CategoryDto dto: list) {
                        if (dto.getType().equals(TransactionType.REVENUES)){
                            revenues.add(dto);
                        }
                    }
                    return revenues;
                case EXP:
                    List<CategoryDto> expenses = new ArrayList<>();
                    for (CategoryDto dto:list) {
                        if (dto.getType().equals(TransactionType.EXPENSES)){
                            expenses.add(dto);
                        }
                    }
                    return expenses;
                default:
                    return new ArrayList<>();
            }
        }
    }

}

