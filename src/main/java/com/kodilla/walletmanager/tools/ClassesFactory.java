package com.kodilla.walletmanager.tools;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.dto.CategoryDto;
import com.kodilla.walletmanager.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;

@Component
public final class ClassesFactory {
    public static final String COMPLETE = "COMPLETE";
    public static final String INCOMPLETE = "INCOMPLETE";

    public final Transaction makeTransaction(final String name){
        switch (name){
            case COMPLETE:
                return new Transaction.TransactionBuilder()
                        .title("Test")
                        .description("Test Description")
                        .date(Date.valueOf(LocalDate.now()))
                        .type(TransactionType.REVENUES)
                        .amount(50)
                        .category(makeCategory(COMPLETE)).build();
            case INCOMPLETE:
                return new Transaction.TransactionBuilder()
                        .title("Test")
                        .date(Date.valueOf(LocalDate.now()))
                        .type(TransactionType.REVENUES)
                        .category(makeCategory(INCOMPLETE)).build();
            default:
                return null;
        }
    }

    public final TransactionDto makeTransactionDto(final String name){
        switch (name){
            case COMPLETE:
                return new TransactionDto.TransactionDtoBuilder()
                        .title("Test")
                        .description("Test Description")
                        .date(Date.valueOf(LocalDate.now()))
                        .type(TransactionType.REVENUES)
                        .amount(50)
                        .category(makeCategoryDto(COMPLETE)).build();
            case INCOMPLETE:
                return new TransactionDto.TransactionDtoBuilder()
                        .title("Test")
                        .date(Date.valueOf(LocalDate.now()))
                        .type(TransactionType.REVENUES)
                        .category(makeCategoryDto(INCOMPLETE)).build();
            default:
                return null;
        }
    }

    public final Category makeCategory(final String name) {
        switch (name) {
            case COMPLETE:
                Category category = new Category();
                category.setName("Test");
                category.setType(TransactionType.REVENUES);
                category.setTransactions(new HashSet<>());
                return category;
            case INCOMPLETE:
                Category icCategory = new Category();
                icCategory.setName("Test");
                icCategory.setType(TransactionType.REVENUES);
                return icCategory;
            default:
                return null;
        }
    }

        public final CategoryDto makeCategoryDto ( final String name){
            switch (name) {
                case COMPLETE:
                    CategoryDto categoryDto = new CategoryDto();
                    categoryDto.setName("Test");
                    categoryDto.setType(TransactionType.REVENUES);
                    categoryDto.setTransactionDtos(new HashSet<>());
                    return categoryDto;
                case INCOMPLETE:
                    CategoryDto icCategoryDto = new CategoryDto();
                    icCategoryDto.setName("Test");
                    icCategoryDto.setType(TransactionType.REVENUES);
                    return icCategoryDto;
                default:
                    return null;
            }

        }
}
