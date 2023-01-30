package com.CallMySql;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController

public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    User u = new User();

    @PostMapping(path = "/api/user-management/user")
    public String addUser(@RequestBody UserPost userPost) {

        u.setUserName(userPost.getEmail());
        u.setPassword(userPost.getPassword());
        u.setFirstName(userPost.getFirstName());
        u.setLastName(userPost.getLastName());
        u.setEmail(userPost.getEmail());
        u.setContactNumber(userPost.getContactNumber());
        u.setTags(userPost.getTags().get(1));
      u.setAge(getAgify(userPost.getFirstName()).getAge());
       u.setGender(getGenderize(userPost.getFirstName()).getGender());
     u.setNationality(getNationalize(userPost.getFirstName()).getCountry().get(0).getCountry_id());



        userRepository.save(u);

        return "saved";
    }


    private Genderize getGenderize(String firstName) {

        String url = "https://api.genderize.io/?name=" + firstName;

        Genderize getGenderize = restTemplate.getForObject(url, Genderize.class);

        return getGenderize;
    }



    private Agify getAgify(String firstName) {
        String url = "https://api.agify.io/?name=" +firstName;
        Agify getAgify = restTemplate.getForObject(url, Agify.class);


        return getAgify;
    }


    private Nationalize getNationalize(String firstName) {
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