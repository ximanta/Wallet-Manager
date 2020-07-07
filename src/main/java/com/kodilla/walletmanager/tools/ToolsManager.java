package com.kodilla.walletmanager.tools;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ToolsManager {

    public static double positiveTenthRoundDouble(double d){
        if (d < 0){
            System.out.println("Amount cannot be negative");
            return 0;
        }
        else if((d * 100) - (long)(d * 100) != 0) {
            double tmp = Math.round(d) * 100;
            return tmp / 100 ;
        }
        else {
            return d;
        }
    }

    public static boolean isTransactionDtoCorrect(TransactionDto transactionDto){
        if (transactionDto.getDate() != null){
            if (transactionDto.getType() != null){
                return transactionDto.getTitle() != null && !transactionDto.getTitle().isEmpty();
            }
        }
        return false;
    }

    public static boolean isMonthCorrect(int month,int year){
        if (month >= 0 && month <= 12){
            return year >= 1980 && year <= LocalDate.now().getYear();
        }
        return false;
    }

    public static boolean isTheSameEnum(TransactionType first, TransactionType second){
        if (first != null && first == second){
            return true;
        }else {
            System.out.println("Incorrect category");
            return false;
        }
    }

}

