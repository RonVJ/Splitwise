package com.pet.splitwise.services;

import com.pet.splitwise.exceptions.UserAlreadyExistsException;
import com.pet.splitwise.models.User;
import com.pet.splitwise.models.UserStatus;
import com.pet.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User registerUser(String userName
            , String phoneNumber
            , String password) throws UserAlreadyExistsException {
        // check if user exist already
        // if yes, check status
        // if status invited, change to active
        // if status active, return User
        // if no user, create user and return

        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);
        User user;
        if(userOptional.isPresent()) {
            user = userOptional.get();
            if(user.getUserStatus().equals(UserStatus.ACTIVE)) {
                throw new UserAlreadyExistsException();
            }
            else {
                // set user status to active
                // save to db
                user.setUserStatus(UserStatus.ACTIVE);
                user.setName(userName);
                user.setPassword(password);
                return userRepository.save(user);
            }
        }

        user = new User();
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        user.setName(userName);
        user.setUserStatus(UserStatus.ACTIVE);

        return userRepository.save(user);
    }
}
