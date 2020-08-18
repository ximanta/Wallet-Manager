package com.kodilla.walletmanager.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCertifying {
    private String login;
    private String password;
}
