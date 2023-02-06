package com.CallMySql;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import java.util.stream.Collectors;


@RestController

public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;


    @PostMapping(path = "/api/user-management/user")
    @ResponseBody
    public ResponseEntity<String> addUser(@RequestBody UserPost userPost) {


        String tags = userPost.getTags().stream().collect(Collectors.joining(":"));
        User u = new User();
        u.setUserName(userPost.getEmail());
        u.setPassword(userPost.getPassword());
        u.setFirstName(userPost.getFirstName());
        u.setLastName(userPost.getLastName());
        u.setEmail(userPost.getEmail());
        u.setContactNumber(userPost.getContactNumber());
        u.setTags(tags);
        u.setAge(getAgify(userPost.getFirstName()).getAge());
        u.setGender(getGenderize(userPost.getFirstName()).getGender());
        u.setNationality(getNationalize(userPost.getFirstName()).getCountry().get(0).getCountry_id());
        u.setStatus("active");
        u.setCreated(date());
        u.setUpdated(date());
        userRepository.save(u);

        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }


    public String date() {

        LocalDateTime dateObj = LocalDateTime.now();
        DateTimeFormatter currentDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String formattedDate = dateObj.format(currentDate);
        return formattedDate;
    }


public Genderize getGenderize(String firstName) {

        String url = "https://api.genderize.io/?name=" + firstName;

        Genderize getGenderize = restTemplate.getForObject(url, Genderize.class);

        return getGenderize;
    }


public  Agify getAgify(String firstName) {
        String url = "https://api.agify.io/?name=" + firstName;
        Agify getAgify = restTemplate.getForObject(url, Agify.class);


        return getAgify;
    }


public  Nationalize getNationalize(String firstName) {
        String url = "https://api.nationalize.io?name=" + firstName;
        Nationalize getNationalize = restTemplate.getForObject(url, Nationalize.class);

        return getNationalize;
    }


    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();

    }


}