package com.example.icommerce.services;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import org.hibernate.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.icommerce.entities.CustomerActivity;
import com.example.icommerce.entities.User;
import com.example.icommerce.models.UserRequestModel;
import com.example.icommerce.repositories.CustomerActivityRepository;
import com.example.icommerce.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerActivityRepository activityRepository;

    public CustomerActivity createCustomerActivity (@NotEmpty String username, @NotEmpty String api, @NotEmpty String method, String query) {
        User user = getUserByUsername(username);
        if(user != null) {
            CustomerActivity customerActivity = new CustomerActivity();
            customerActivity.setApi(api);
            customerActivity.setMethod(method);
            customerActivity.setQuery(query);

            user.addActivity(customerActivity);

            userRepository.save(user);

            return customerActivity;
        }

        return null;
    }

    public List<CustomerActivity> getAllActivities (@NotEmpty(message = "username must contain value") String username) {
        return activityRepository.findAllByUser_Username(username);
    }

    public User getUserByUsername (String username) {
        if( StringHelper.isEmpty(username) ) {
            throw new IllegalArgumentException("'username' parameter is empty");
        }

        return userRepository.findByUsername(username).orElse(null);
    }

    public User createUser (String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true);
        user.setCreatedDate(Instant.now());
        user.setModifiedDate(Instant.now());

        return userRepository.save(user);
    }

    public User createUser (UserRequestModel model) {
        //Validation
        Objects.requireNonNull(model, "UserRequestModel is null");

        if ( StringHelper.isEmpty(model.getUsername()) ) {
            throw new IllegalArgumentException("'username' attribute must contain value");
        }

        return createUser(model.getUsername(), model.getPassword());
    }
}
