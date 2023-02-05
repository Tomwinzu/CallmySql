package com.CallMySql;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    UserController toTest = new UserController();

    @BeforeEach
    void setUp() {
        System.out.println("Test is starting");
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void getUp() {
        System.out.println("Test is end");

    }

    @Test
    void addUser() {


    }

    @Test
    void date() {
        System.out.println(toTest.date());
        assertEquals("-", toTest.date().substring(2, 3));
        assertEquals("-", toTest.date().substring(5, 6));
        assertEquals(":", toTest.date().substring(13, 14));
    }


    @Test
    void tagsTest() {
        List<String> tags = new ArrayList<>();
        tags.add("a");
        tags.add("b");
        System.out.println(tags.stream().collect(Collectors.joining(":")));

    }


    @Test
    void getAllUsers() {

        List<User> userList = new ArrayList<>();
        List<String> tags = new ArrayList<>();
        tags.add("a");
        tags.add("b");
        User u1 = new User();
        u1.setPassword("112233");
        u1.setFirstName("tom");
        u1.setLastName("Wang");
        u1.setEmail("tom@yahoo.com");
        u1.setUserName(u1.getEmail());
        u1.setContactNumber("130030088");
        u1.setStatus("active");
        User u2 = new User();
        u2.setPassword("223344");
        u2.setFirstName("jenny");
        u2.setLastName("Win");
        u2.setEmail("tjenny@yahoo.com");
        u2.setUserName(u2.getEmail());
        u2.setContactNumber("130030000");
        u2.setStatus("active");
        userList.add(u1);
        userList.add(u2);
        Mockito.when(userRepository.findAll()).thenReturn(userList);
        toTest.getAllUsers().forEach(i -> System.out.println(i.getUserName()));
        toTest.getAllUsers().forEach(i -> System.out.println(i.getPassword()));
        toTest.getAllUsers().forEach(i -> System.out.println(i.getFirstName()));
        toTest.getAllUsers().forEach(i -> System.out.println(i.getLastName()));
        toTest.getAllUsers().forEach(i -> System.out.println(i.getEmail()));
        toTest.getAllUsers().forEach(i -> System.out.println(i.getContactNumber()));
              toTest.getAllUsers().forEach(i -> System.out.println(i.getStatus()));
    }
@Test
    void getGenderizeTest() {

        Genderize gen=new Genderize();
         Mockito.when(restTemplate.getForObject("https://api.genderize.io/?name=tom",Genderize.class)).thenReturn(gen);
       toTest.getAllUsers().forEach(i-> System.out.println(i.getGender()));
       Genderize genderize  =new Genderize();
       Assertions.assertEquals(gen,genderize);

    }


}
