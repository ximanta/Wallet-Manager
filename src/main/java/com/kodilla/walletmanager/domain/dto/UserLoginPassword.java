package com.kodilla.walletmanager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginPassword {
    private String login;
    private String password;
}
