package by.ab.autobaraholka.controller;

import by.ab.autobaraholka.model.Ad;
import by.ab.autobaraholka.model.User;
import by.ab.autobaraholka.service.AdService;
import by.ab.autobaraholka.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService service;
    @GetMapping
    public List<User> findAllUsers(){
        return service.findAllUsers();
    }

    @PostMapping("save_user")
    public String saveUser(@RequestBody User user){
        service.saveUser(user);
        return "Success";
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable int id){
        return service.findById(id);
    }

    @PutMapping("update_user")
    public String updateUser(@RequestBody User user){
        service.updateUser(user);
        return "Success";
    }

    @DeleteMapping("delete_user/{id}")
    public void deleteUser(@PathVariable int id){
        service.deleteUser(id);
    }
}
