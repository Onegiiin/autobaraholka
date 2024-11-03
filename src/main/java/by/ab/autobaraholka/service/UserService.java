package by.ab.autobaraholka.service;

import by.ab.autobaraholka.model.Ad;
import by.ab.autobaraholka.model.User;

import java.util.List;


public interface UserService {
     List<User> findAllUsers();
     User saveUser(User user);
     User findById(Integer id);
     User updateUser(User user);
     void deleteUser(Integer id);

     boolean containsToken(String token);
     User findByToken(String token);


}
