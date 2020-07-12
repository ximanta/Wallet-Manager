package com.kodilla.walletmanager.tools;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.dto.CategoryDto;
import com.kodilla.walletmanager.dto.TransactionDto;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public final class ClassesFactory {
    public static final String COMPLETE = "COMPLETE";
    public static final String INCOMPLETE = "INCOMPLETE";
    public static final String THREEOBJECT = "THREEOBJECT";
    public static final String FIVEOBJECT = "FIVEOBJECT";

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

        public final List<Transaction> makeTranactionList(final String name){
            Transaction transaction1 = makeTransaction(ClassesFactory.COMPLETE);
            Transaction transaction2 = makeTransaction(ClassesFactory.COMPLETE);
            Transaction transaction3 = makeTransaction(ClassesFactory.COMPLETE);
            Transaction transaction4 = makeTransaction(ClassesFactory.COMPLETE);
            Transaction transaction5 = makeTransaction(ClassesFactory.COMPLETE);

        switch (name){
            case THREEOBJECT:
                List<Transaction> treeList = new ArrayList<>();
                treeList.add(transaction1);
                treeList.add(transaction2);
                treeList.add(transaction3);

                return treeList;
            case FIVEOBJECT:
                List<Transaction> fiveList = new ArrayList<>();
                fiveList.add(transaction1);
                fiveList.add(transaction2);
                fiveList.add(transaction3);
                fiveList.add(transaction4);
                fiveList.add(transaction5);

                return fiveList;

            default:
                return null;
            }
        }
}
