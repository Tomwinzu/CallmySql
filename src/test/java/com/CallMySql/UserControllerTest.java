package com.CallMySql;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
    UserController userTest = new UserController();

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
    void dateTest() {

        System.out.println(userTest.date());
        assertEquals("-", userTest.date().substring(2, 3));
        assertEquals("-", userTest.date().substring(5, 6));
        assertEquals(":", userTest.date().substring(13, 14));
    }


    @Test
    void tagsTest() {

        List<String> tags = new ArrayList<>();
        tags.add("a");
        tags.add("b");
         Assertions.assertEquals("a:b",tags.stream().collect(Collectors.joining(":")));


    }


    @Test
    void getAllUsersTest() {

        List<User> userList = new ArrayList<>();

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
        userTest.getAllUsers().forEach(i -> System.out.println(i.getUserName()));
        userTest.getAllUsers().forEach(i -> System.out.println(i.getPassword()));
        userTest.getAllUsers().forEach(i -> System.out.println(i.getFirstName()));
        userTest.getAllUsers().forEach(i -> System.out.println(i.getLastName()));
        userTest.getAllUsers().forEach(i -> System.out.println(i.getEmail()));
        userTest.getAllUsers().forEach(i -> System.out.println(i.getContactNumber()));
        userTest.getAllUsers().forEach(i -> System.out.println(i.getStatus()));

        Assertions.assertEquals(userList,userTest.getAllUsers());
    }

    @Test
    void genderizeTest() {

        Genderize gen = new Genderize();
        Mockito.when(restTemplate.getForObject("https://api.genderize.io/?name=tom", Genderize.class)).thenReturn(gen);
        userTest.getAllUsers().forEach(i -> System.out.println(i.getGender()));
        Genderize genderize = userTest.getGenderize("tom");
        Assertions.assertEquals(gen, genderize);
        System.out.println(genderize);
        }
        @Test
        void agifyTest() {

            Agify ag= new Agify();
            Mockito.when(restTemplate.getForObject("https://api.agify.io/?name=tom", Agify.class)).thenReturn(ag);
            userTest.getAllUsers().forEach(i -> System.out.println(i.getAge()));
            Agify agify = userTest.getAgify("tom");
            Assertions.assertEquals(ag, agify);
            System.out.println(agify);
        }

        @Test
        void nationalize () {

            Nationalize na= new Nationalize();
            Mockito.when(restTemplate.getForObject("https://api.nationalize.io?name=tom",  Nationalize.class)).thenReturn(na);
            userTest.getAllUsers().forEach(i -> System.out.println(i.getNationality()));
            Nationalize nationalize = userTest.getNationalize("tom");
            Assertions.assertEquals(na, nationalize);
            System.out.println(na);
        }




}
