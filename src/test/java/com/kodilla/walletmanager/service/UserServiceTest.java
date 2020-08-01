package com.kodilla.walletmanager.service;

import com.kodilla.walletmanager.domain.entities.User;
import com.kodilla.walletmanager.domain.dto.UserDto;
import com.kodilla.walletmanager.mapper.UserMapper;
import com.kodilla.walletmanager.repository.UserRepository;
import com.kodilla.walletmanager.tools.ClassesFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserRepository repository;

    @Autowired
    UserMapper mapper;

    @Autowired
    ClassesFactory factory;

    @Autowired
    UserService service;


    @Test
    public void get() {

    }

    @Test
    public void getAll() {
    }

    @Test
    public void create() {
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }
}