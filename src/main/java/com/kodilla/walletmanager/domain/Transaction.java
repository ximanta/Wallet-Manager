package com.kodilla.walletmanager.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.tools.ToolsManager;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@NamedQueries({
        @NamedQuery(
                name = "Transaction.thisWeek",
                query = "FROM Transaction WHERE YEARWEEK(date) = YEARWEEK(NOW())"),
        @NamedQuery(
                name = "Transaction.thisMonth",
                query = "FROM Transaction WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE())"),
        @NamedQuery(
                name = "Transaction.selectedMonth",
                query = "FROM Transaction WHERE MONTH(date) = :MONTH AND YEAR(date) = :YEAR"),
        @NamedQuery(
                name = "Transaction.betweenDate",
                query = "FROM Transaction WHERE date >= :FROMDATE and date <= :TODATE")
})

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column
    private Date date =  new Date(new java.util.Date().getTime());

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column
    private TransactionType type;

    @Column
    @NotBlank
    private String title;

    @Column
    private String description;

    @Column
    private double amount;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH},
            targetEntity = Category.class)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", category=" + category +
                '}';
    }

    public void setAmount(double amount) {
        this.amount = ToolsManager.positiveTenthRoundDouble(amount);
    }

    public void setCategory(Category category) {
        if (ToolsManager.isTheSameEnum(category.getType(),type)){
            this.category = category;
        } else {
            System.out.println("Not the came type TransactionType");
        }
    }

    public static class TransactionBuilder{
        private Long id;
        private Date date =  new Date(new java.util.Date().getTime());
        private TransactionType type;
        private String title;
        private String description;
        private double amount;
        private Category category;

        public TransactionBuilder id(Long id){
            this.id = id;
            return this;
        }

        public TransactionBuilder date(Date date){
            this.date = date;
            return this;
        }

        public TransactionBuilder type(TransactionType type){
            this.type = type;
            return this;
        }

        public TransactionBuilder title(String title){
            this.title = title;
            return this;
        }

        public TransactionBuilder description(String description){
            this.description = description;
            return this;
        }

        public TransactionBuilder amount(double amount){
            this.amount = amount;
            return this;
        }

        public TransactionBuilder category(Category category){
            this.category = category;
            return this;
        }

        public Transaction build(){
            Transaction transaction = new Transaction();
            transaction.setId(id);
            transaction.setDate(date);
            transaction.setType(type);
            transaction.setTitle(title);
            transaction.setDescription(description);
            transaction.setAmount(amount);
            transaction.setCategory(category);

            return transaction;
        }
    }
}