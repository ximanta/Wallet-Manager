package com.kodilla.walletmanager.domain.entities;

import com.kodilla.walletmanager.domain.enums.CurrencyType;
import com.kodilla.walletmanager.tools.ToolsManager;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@NamedQueries({
        @NamedQuery(
                name = "Transaction.thisWeek",
                query = "FROM Transaction WHERE YEARWEEK(date) = YEARWEEK(NOW()) AND user = :USER"),
        @NamedQuery(
                name = "Transaction.thisMonth",
                query = "FROM Transaction WHERE MONTH(date) = MONTH(CURRENT_DATE()) AND YEAR(date) = YEAR(CURRENT_DATE()) AND user = :USER"),
        @NamedQuery(
                name = "Transaction.betweenDate",
                query = "FROM Transaction WHERE date >= :FROMDATE and date <= :TODATE AND user = :USER")
})
@Getter
@Setter
@Entity
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column
    private Date date =  new Date(new java.util.Date().getTime());

    @Column
    @NotBlank
    private String title;

    @Column
    private String description;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column
    private CurrencyType currencyType;

    @Column(precision = 2, length = 25)
    private double amount;

    @NotNull
    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH },
            targetEntity = Category.class)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @NotNull
    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH },
            targetEntity = User.class)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", currencyType=" + currencyType +
                ", amount=" + amount +
                ", category=" + category +
                ", user=" + user + '}';
    }

    public void setAmount(double amount) {
        this.amount = ToolsManager.positiveTenthRoundDouble(amount);
    }

}