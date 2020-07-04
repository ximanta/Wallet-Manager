package com.kodilla.walletmanager.domain;

import com.kodilla.walletmanager.domain.enums.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    private TransactionType type;
}
