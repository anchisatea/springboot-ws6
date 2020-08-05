package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/users")
    public UsersResponse createNewUser(@RequestBody NewUserRequest request){
        // Validate input
        // Create user into database =>
        User  user = new User();
        user.setName((request.getName()));
        user.setAge(request.getAge());
        user = userRepository.save(user);
        return new UsersResponse(user.getId(), user.getName() + user.getAge());
    }

    @GetMapping("/users")
    public PagingResponse getAllUser(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(name = "item_per_page", defaultValue = "10") int itemPerPage){

        PagingResponse pagingResponse = new PagingResponse(page, itemPerPage);
        List<UsersResponse> usersResponseList  = new ArrayList<>();

        //Get All
        List<User> users = (List<User>) userRepository.findAll();
        for (User user: users) {
            usersResponseList.add(new UsersResponse(user.getId(), user.getName()));
        }

        //usersResponseList.add(new UsersResponse(1, "User 1"));
        //usersResponseList.add(new UsersResponse(2, "User 2"));
        //usersResponseList.add(new UsersResponse(3, "User 3"));

        pagingResponse.setUsersResponse(usersResponseList);
        return pagingResponse;
    }

    //@GetMapping("/users")
    //public String getPage(@RequestParam(required = false, defaultValue = "1") String page, @RequestParam(name = "item_per_page",required =false, defaultValue = "10") String itemPerPage){
    //    return "Page: " + page + ", itemPerPage: " + itemPerPage;
    //}


    @GetMapping("/users/{id}")
    public UsersResponse getUserById(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        return new UsersResponse(user.get().getId(), user.get() .getName() );
    }


    @PostMapping("/users1")
    public String createNewUserWithFormData(NewUserRequest request){
       return request.getName() + request.getAge();
    }
}
