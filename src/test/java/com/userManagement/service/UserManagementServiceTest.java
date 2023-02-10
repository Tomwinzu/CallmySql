package com.userManagement.service;

import com.userManagement.UserRepository;
import com.userManagement.service.bean.Agify;
import com.userManagement.service.bean.Genderize;
import com.userManagement.service.bean.Nationalize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserManagementServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks

    UserManagementService userTest = new UserManagementService();

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
    void addUserTest() {

        List<String> mocklist = mock(List.class);
        mocklist.size();
        mocklist.add("tom");
        verify(mocklist, times(1)).size();
        verify(mocklist, atMost(2)).size();
        verify(mocklist, atLeastOnce()).size();
        verify(mocklist, atLeast(1)).size();
        verify(mocklist).add("tom");
        verify(mocklist).add(anyString());


    }

    @Test
    void dateTest() {

        System.out.println(userTest.date());
        assertEquals("-", userTest.date().substring(5, 6));
        assertEquals(":", userTest.date().substring(16, 17));

    }

    @Test
    void genderizeTest() throws Exception {

        Genderize gen = new Genderize();
        Class<UserManagementService> genderizeClass = UserManagementService.class;
        Method genderizeTestMethod = genderizeClass.getDeclaredMethod("getGenderize", String.class);
        genderizeTestMethod.setAccessible(true);
        Mockito.when(restTemplate.getForObject("https://api.genderize.io/?name=tom", Genderize.class)).thenReturn(gen);
        Object result = genderizeTestMethod.invoke(userTest, "tom");
        Assertions.assertEquals(gen, result);

    }

    @Test
    void agifyTest() throws Exception {

        Agify ag = new Agify();
        Class<UserManagementService> agifyClass = UserManagementService.class;
        Method agifyTestMethod = agifyClass.getDeclaredMethod("getAgify", String.class);
        agifyTestMethod.setAccessible(true);
        Mockito.when(restTemplate.getForObject("https://api.agify.io/?name=tom", Agify.class)).thenReturn(ag);
        Object result = agifyTestMethod.invoke(userTest, "tom");
        assertEquals(ag, result);
    }


    @Test
    void nationalizeTest() throws Exception{

        Nationalize na = new Nationalize();
        Class <UserManagementService> natinalizeClass=UserManagementService.class;
        Method nationlizeTestMethod=natinalizeClass.getDeclaredMethod("getNationalize", String.class);
        nationlizeTestMethod.setAccessible(true);
        Mockito.when(restTemplate.getForObject("https://api.nationalize.io?name=tom", Nationalize.class)).thenReturn(na);
        Object result=nationlizeTestMethod.invoke(userTest,"tom");
        Assertions.assertEquals(na, result);

    }

}
