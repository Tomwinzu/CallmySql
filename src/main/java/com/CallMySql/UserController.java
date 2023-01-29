package com.CallMySql;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController

public class UserController {
    @Autowired
    private UserRepository userRepository;
    private RestTemplate restTemplate;
    User u = new User();

    @PostMapping(path = "/api/user-management/user")
    public String addNewUser(@RequestBody User user) {
        u.setUserName(u.getEmail());
        userRepository.save(user);
        userRepository.save(u);

        return "Saved";
    }


    private Genderize getGenderize(String firstName) {


        String url = "https://api.genderize.io/?name=" + firstName;

        Genderize getGenderize = restTemplate.getForObject(url, Genderize.class);
        u.setGender(getGenderize(firstName).getGender());
        userRepository.save(u);

        return getGenderize;
    }

    private Agify getAgify(String firstName) {
        String url = "https://api.agify.io/?name=" + firstName;
        Agify getAgify = restTemplate.getForObject(url, Agify.class);

        u.setAge(getAgify(firstName).getAge());
        userRepository.save(u);

        return getAgify;
    }


    private Nationalize getNationalize(String firstName) {
        String url = "https://api.nationalize.io?name=" + firstName;
        Nationalize getNationalize = restTemplate.getForObject(url, Nationalize.class);
        u.setNationality(getNationalize(firstName).getCountry().get(0).getCountry_id());
        userRepository.save(u);
        return getNationalize;
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();

    }
}