package com.kodilla.walletmanager.domain;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.tools.ToolsManager;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Data
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
                    CascadeType.DETACH,CascadeType.MERGE,
                    CascadeType.PERSIST, CascadeType.REFRESH},
            targetEntity = Category.class)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

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
}