package ir.sinafood.orderservice.service;

import ir.sinafood.orderservice.dto.CreateUserRequest;
import ir.sinafood.orderservice.dto.UserResponse;
import ir.sinafood.orderservice.entity.User;
import ir.sinafood.orderservice.exception.UserNotFoundException;
import ir.sinafood.orderservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserResponse create(CreateUserRequest request) {

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }


    public List<UserResponse> findAll() {

        return userRepository.findAll()
                .stream()
                .map(user ->
                        new UserResponse(
                                user.getId(),
                                user.getName(),
                                user.getEmail()
                        )
                )
                .toList();
    }


    public UserResponse findById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException(id));

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

}