package by.ab.autobaraholka.service.impl;

import by.ab.autobaraholka.model.Ad;
import by.ab.autobaraholka.model.AdRepository;
import by.ab.autobaraholka.model.User;
import by.ab.autobaraholka.model.UserRepository;
import by.ab.autobaraholka.service.AdService;
import by.ab.autobaraholka.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
     private final UserRepository repository;

     @Override
     public boolean containsToken(String token) {
          // TODO
          return true;
     }

     @Override
     public User findByToken(String token) {
          // TODO
          return repository.findById(1).orElse(null);
     }
}
