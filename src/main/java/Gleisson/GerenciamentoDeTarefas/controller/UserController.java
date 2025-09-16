package Gleisson.GerenciamentoDeTarefas.controller;

import Gleisson.GerenciamentoDeTarefas.controller.dto.CreateUserDto;
import Gleisson.GerenciamentoDeTarefas.controller.dto.UpdateUserDto;
import Gleisson.GerenciamentoDeTarefas.entity.User;
import Gleisson.GerenciamentoDeTarefas.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { // Chama o UserService
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UUID> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        UUID userId = userService.createUser(createUserDto);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser(
            @PathVariable String userId,
            @RequestBody @Valid UpdateUserDto updateUserDto) {
        userService.updateById(userId, updateUserDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }
}
