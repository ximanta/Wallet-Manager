package com.kodilla.walletmanager.bootstrap;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.Transaction;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.repository.CategoryRepository;
import com.kodilla.walletmanager.repository.TransactionRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ClassesFactory classesFactory;

    @Override
    public void run(String... args) throws Exception {
        transactionRepository.deleteAll();
        categoryRepository.deleteAll();
        List<Transaction> transactions = transactionRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        boolean areEmpty = transactions.isEmpty() && categories.isEmpty();
        if(areEmpty){
            Category category = classesFactory.makeCategory(ClassesFactory.COMPLETE);
            Category fromDb = categoryRepository.save(category);
            List<Transaction> listRev = classesFactory.makeTranactionList(ClassesFactory.FIVEOBJECT);
            listRev.get(0).setDate(Date.valueOf("2019-03-25"));
            listRev.get(1).setDate(Date.valueOf("2019-03-05"));
            listRev.get(2).setDate(Date.valueOf(LocalDate.now().minusDays(25)));
            listRev.get(3).setDate(Date.valueOf(LocalDate.now().minusDays(15)));
            listRev.get(4).setDate(Date.valueOf(LocalDate.now().minusDays(5)));

            List<Transaction> listExp = classesFactory.makeTranactionList(ClassesFactory.FIVEOBJECT);
            listExp.get(0).setDate(Date.valueOf("2019-03-25"));
            listExp.get(1).setDate(Date.valueOf("2019-03-05"));
            listExp.get(2).setDate(Date.valueOf(LocalDate.now().minusDays(25)));
            listExp.get(3).setDate(Date.valueOf(LocalDate.now().minusDays(15)));
            listExp.get(4).setDate(Date.valueOf(LocalDate.now().minusDays(5)));

            for (Transaction transaction:listRev) {
                transaction.setCategory(fromDb);
                transactionRepository.save(transaction);
            }
            Category categoryExp = classesFactory.makeCategory(ClassesFactory.COMPLETE);
            categoryExp.setType(TransactionType.EXPENSES);
            Category fromDbExp = categoryRepository.save(categoryExp);

            for (Transaction transaction:listExp) {
                transaction.setType(TransactionType.EXPENSES);
                transaction.setCategory(fromDbExp);
                transactionRepository.save(transaction);
            }
        }

    }
}
