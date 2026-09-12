package ir.sinafood.orderservice.controller;
import ir.sinafood.orderservice.dto.CreateUserRequest;
import ir.sinafood.orderservice.dto.UserResponse;
import ir.sinafood.orderservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public UserResponse create(
            @RequestBody CreateUserRequest request
    ){

        return userService.create(request);
    }



    @GetMapping
    public List<UserResponse> findAll(){

        return userService.findAll();
    }



    @GetMapping("/{id}")
    public UserResponse findById(
            @PathVariable UUID id
    ){

        return userService.findById(id);
    }

}