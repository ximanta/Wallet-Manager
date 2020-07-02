package com.kodilla.walletmanager.domain;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import com.kodilla.walletmanager.tools.ToolsManager;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Calendar;

@Data
@NoArgsConstructor
@Entity(name = "TRANSACTION")
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

    public void setAmount(double amount) {
        this.amount = ToolsManager.positiveTenthRoundDouble(amount);
    }
}