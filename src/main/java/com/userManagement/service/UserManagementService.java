package com.userManagement.service;

import com.userManagement.User;
import com.userManagement.UserRepository;
import com.userManagement.service.bean.Agify;
import com.userManagement.service.bean.Genderize;
import com.userManagement.service.bean.Nationalize;
import com.userManagement.service.bean.UserPost;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class UserManagementService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestTemplate restTemplate;
    User user = new User();

    public void addUser(@RequestBody UserPost userPost) {

        String tags = userPost.getTags().stream().collect(Collectors.joining(":"));

        user.setUserName(userPost.getEmail());
        user.setPassword(userPost.getPassword());
        user.setFirstName(userPost.getFirstName());
        user.setLastName(userPost.getLastName());
        user.setEmail(userPost.getEmail());
        user.setContactNumber(userPost.getContactNumber());
        user.setTags(tags);
        user.setAge(getAgify(user.getFirstName()).getAge());
        user.setGender(getGenderize(user.getFirstName()).getGender());
        user.setNationality(getNationalize(user.getFirstName()).getCountry().get(0).getCountry_id());
        user.setStatus("active");
        user.setCreated(date());
        user.setUpdated(date());
        userRepository.save(user);


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

    public void deleteUserByEmail(String userName) {
        if (userRepository.findById(Integer.valueOf(userName)).isPresent());
            userRepository.deleteById(Integer.valueOf(userName));
    }

    public void updateUser(User user) {

        if (userRepository.findById(Integer.valueOf(user.getUserName())).isPresent()) {
            var dbUser = userRepository.findById(Integer.valueOf(user.getUserName())).get();

            user.setUserName(user.getUserName());
            user.setAge(getAgify(user.getFirstName()).getAge());
            user.setGender(getGenderize(user.getFirstName()).getGender());
            user.setNationality(getNationalize(user.getFirstName()).getCountry().get(0).getCountry_id());
             user.setUpdated(String.valueOf(LocalDateTime.now()));
            userRepository.save(user);

        } else {
            throw new EntityNotFoundException("User email: " + user.getEmail() + " cannot be found in the database");

        }

    }
}