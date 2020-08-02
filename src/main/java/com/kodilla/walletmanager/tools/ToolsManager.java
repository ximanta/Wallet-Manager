package com.kodilla.walletmanager.tools;

import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.service.transaction.TransactionServiceCRUD;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ToolsManager {
    public static final String REV = "REV";
    public static final String EXP = "EXP";
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceCRUD.class);

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

    public static boolean isTransactionDtoCorrect(TransactionDto dto){
        boolean isDate = dto.getDate() != null;
        boolean isType = dto.getType() != null;
        boolean isCategory = dto.getCategoryDto() != null;
        boolean isUser = dto.getUserDto() != null;
        boolean isTitleNotBlank = dto.getTitle() != null && !dto.getTitle().isEmpty();

        return isDate && isType && isCategory && isTitleNotBlank && isUser;
    }

    public static boolean isUserDtoCorrect(UserDto dto){
        boolean isLogin = dto.getLogin() != null;
        boolean isLoginEmpty = !dto.getLogin().isEmpty();
        boolean isEmile = dto.getEmile() != null;
        boolean isBirthDate = dto.getBirthDate() != null;
        boolean isPassword = isPasswordAccept(dto.getPassword());
        if (isLogin && isLoginEmpty && isEmile && isBirthDate && isPassword){
            LOGGER.info("UserDto is correct");
            return true;
        }else {
            LOGGER.info("UserDto is incorrect");
            return false;
        }

    }

    public static boolean isCategoryDtoCorrect(CategoryDto dto){
        boolean isNull = dto != null;
        boolean isName = dto.getName() != null;
        boolean isType = dto.getType().equals(TransactionType.EXPENSES) || dto.getType().equals(TransactionType.REVENUES);
        if (isName && isNull && isType){
            LOGGER.info("CategoryDto is correct");
            return true;
        }
        LOGGER.info("CategoryDto is incorrect");
        return false;
    }

    private static boolean isPasswordAccept(String password){
        boolean isPassword = password != null;
        boolean isLength = password.length() >= 6;
        if (isPassword && isLength){
            LOGGER.info("Password is accepted");
            return true;
        }
        LOGGER.warn("Incorrect password");
        return false;
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