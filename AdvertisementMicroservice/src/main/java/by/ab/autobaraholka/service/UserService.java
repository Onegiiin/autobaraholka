package by.ab.autobaraholka.service;

import by.ab.autobaraholka.model.Ad;
import by.ab.autobaraholka.model.User;

import java.util.List;


public interface UserService {

     boolean containsToken(String token);
     User findByToken(String token);


}
