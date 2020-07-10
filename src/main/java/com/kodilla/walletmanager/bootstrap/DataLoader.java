package com.kodilla.walletmanager.bootstrap;

import com.kodilla.walletmanager.domain.Category;
import com.kodilla.walletmanager.domain.Transaction;
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
        List<Transaction> transactions = transactionRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        boolean areEmpty = transactions.isEmpty() && categories.isEmpty();
        if(areEmpty){
            Category category = classesFactory.makeCategory(ClassesFactory.COMPLETE);
            Category fromDb = categoryRepository.save(category);
            List<Transaction> list = classesFactory.makeTranactionList(ClassesFactory.FIVEOBJECT);
            list.get(0).setDate(Date.valueOf("2019-03-25"));
            list.get(1).setDate(Date.valueOf("2019-03-05"));
            list.get(2).setDate(Date.valueOf(LocalDate.now().minusDays(25)));
            list.get(3).setDate(Date.valueOf(LocalDate.now().minusDays(15)));
            list.get(4).setDate(Date.valueOf(LocalDate.now().minusDays(5)));

            for (Transaction transaction:list) {
                transaction.setCategory(fromDb);
                transactionRepository.save(transaction);
            }
        }

    }
}
