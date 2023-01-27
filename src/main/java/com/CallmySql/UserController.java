package com.CallmySql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
  private RestTemplate restTemplate;
    @PostMapping(path="api/user-management/user")
    public @ResponseBody String addNewUser(@RequestParam String password, @RequestParam String firstName, @RequestParam String lastName,
                                           @RequestParam String email, @RequestParam String contactNumber, @RequestParam String tags) {

        User u = new User();
        u.setPassword(password);
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setTags(tags);
        userRepository.save(u);
        return "Saved";
    }
      @RequestMapping("/api/get")
       private  Genderize getGenderize(String firstName) {

        String url = "https://api.genderize.io/?name=" + firstName;

        Genderize getGenderize = restTemplate.getForObject(url, Genderize.class);
      User uGender=new User();

        return  getGenderize;
    }

    private Agify getAgify(String firstName) {

        String url = "https://api.agify.io/?name=" +firstName ;

        Agify getAgify = restTemplate.getForObject(url, Agify.class);


         return  getAgify;
    }


    private Nationalize getNationalize(String firstName) {

        String url = "https://api.nationalize.io?name=" + firstName;
        Nationalize getNationalize = restTemplate.getForObject(url, Nationalize.class);

        return getNationalize;

    }
    public @ResponseBody String getUser(@PathVariable("name") String firstName) {

        return getUser(firstName,getAgify(firstName).getAge(), getGenderize(firstName).getGender(), getNationalize(firstName).getCountry().get(0).getCountry_id());

    }


    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();

    }
}