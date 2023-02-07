package com.userManagement.service;


import com.userManagement.UserRepository;
import com.userManagement.service.bean.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class UserManagement {
    @Autowired
private UserRepository userRepository;
    @Autowired

    private RestTemplate restTemplate;





   public User addUser(@RequestBody UserPost userPost) {

           String tags = userPost.getTags().stream().collect(Collectors.joining(":"));
       User u = new User();
       u.setUserName(userPost.getEmail());
       u.setPassword(userPost.getPassword());
       u.setFirstName(userPost.getFirstName());
       u.setLastName(userPost.getLastName());
       u.setEmail(userPost.getEmail());
       u.setContactNumber(userPost.getContactNumber());
       u.setTags(tags);
       u.setAge(getAgify(u.getFirstName()).getAge());
       u.setGender(getGenderize(u.getFirstName()).getGender());
       u.setNationality(getNationalize(u.getFirstName()).getCountry().get(0).getCountry_id());
       u.setStatus("active");
       u.setCreated(date());
       u.setUpdated(date());
       userRepository.save(u);

       return null;

   }
    private Agify getAgify(String firstName) {
        String url = "https://api.agify.io/?name=" + firstName;
        Agify getAgify = restTemplate.getForObject(url, Agify.class);

        return getAgify;
    }


    private Genderize getGenderize(String firstName) {

        String url = "https://api.genderize.io/?name=" + firstName;

        Genderize getGenderize = restTemplate.getForObject(url, Genderize.class);

        return getGenderize;
    }



    private Nationalize getNationalize(String firstName) {
        String url = "https://api.nationalize.io?name=" + firstName;
        Nationalize getNationalize = restTemplate.getForObject(url, Nationalize.class);

        return getNationalize;
    }

    public String date() {

        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter currentDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateObj.format(currentDate);
        return formattedDate;
    }



    }

