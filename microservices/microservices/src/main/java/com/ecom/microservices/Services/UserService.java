package com.ecom.microservices.Services;


import com.ecom.microservices.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserService {

    private List<User> userlist = new ArrayList<>();
    private Long id = 1L;

    public List<User> getAllUsers(){
        return userlist;
    }

    public User getUserById(Long id){
        return userlist
                .stream()
                .filter(user -> user.getId().equals(id))
                .findAny()
                .orElse(null);
    }

    public User createUser(User user){
        user.setId(id++);
        userlist.add(user);
        return user;
    }

    public boolean updateUser(Long id, User updatedUser){
        return userlist
                .stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);
    }

    public boolean deleteUser(Long id) {
        User todelete = userlist
                .stream().
                filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);

        return userlist.remove(todelete);
    }
}
