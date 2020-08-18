package com.kodilla.walletmanager.tools;

import com.kodilla.walletmanager.domain.dto.CategoryDto;
import com.kodilla.walletmanager.domain.dto.TransactionDto;
import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.service.transaction.TransactionServiceCRUD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

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
            return (double)Math.round(d * 100) / 100;
    }

    public static boolean isTransactionDtoCorrect(TransactionDto dto){
        boolean isDate = dto.getDate() != null;
        boolean isCategory = dto.getCategoryDto() != null;
        boolean isUser = dto.getUserDto() != null;
        boolean isTitle = dto.getTitle() != null;
        boolean isTitleNotBlank = !dto.getTitle().isEmpty();

        return isDate && isCategory && isTitleNotBlank && isTitle && isUser;
    }

    public static boolean isUserDtoCorrect(UserDto dto){
        boolean isLogin = dto.getLogin() != null;
        boolean isEmile = dto.getEmile() != null;
        boolean isBirthDate = dto.getBirthDate() != null;
        boolean isCurrencyType = dto.getCurrencyType() !=null;

        if (isLogin && isEmile && isBirthDate && isCurrencyType){
            boolean isLoginEmpty = isLoginAccept(dto.getLogin());
            boolean isPassword = isPasswordAccept(dto.getPassword());
            if (isLoginEmpty && isPassword){
                LOGGER.info("UserDto is correct");
                return true;
            }
        }
        LOGGER.info("UserDto valid body");
        return false;
    }

    public static boolean isCategoryDtoCorrect(CategoryDto dto){
        boolean isNull = dto != null;
        boolean isName = dto.getName() != null;
        boolean isType = dto.getType() != null;
        if (isName && isNull && isType){
            boolean isTypeCorrect = dto.getType().equals(TransactionType.EXPENSES) || dto.getType().equals(TransactionType.REVENUES);
            boolean isBlank = !dto.getName().isEmpty();
            if (isTypeCorrect && isBlank){
                LOGGER.info("CategoryDto is correct");
                return true;
            }
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
        LOGGER.warn("Incorrect password, at least 6 characters");
        return false;
    }

    private static boolean isLoginAccept(String login){
        if (login != null){
            if (login.length() >= 5){
                    LOGGER.info("Correct Login");
                    return true;
            }
            LOGGER.error("To short Login, aat least 5 characters");
            return false;
        }
        LOGGER.error("Null Login");
        return false;
    }

    public static List<TransactionDto> sortTransactionByType(List<TransactionDto> dtos, String type){
        if (type == null){
            return dtos;
        }else {
            switch (type){
                case REV:
                    List<TransactionDto> revenues = new ArrayList<>();
                    for (TransactionDto dto:dtos) {
                        if (dto.getCategoryDto().getType() == TransactionType.REVENUES){
                            revenues.add(dto);
                        }
                    }
                    return revenues;
                case EXP:
                    List<TransactionDto> expenses = new ArrayList<>();
                    for (TransactionDto dto:dtos) {
                        if (dto.getCategoryDto().getType().equals(TransactionType.EXPENSES)){
                            expenses.add(dto);
                        }
                    }
                    return expenses;
                default:
                    return new ArrayList<>();
            }
        }
    }

    public static List<CategoryDto> sortCategoryByType(List<CategoryDto> list, String type){
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