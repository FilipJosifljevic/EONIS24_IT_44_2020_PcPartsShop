package erp.pcpartsbackend.controllers;

import erp.pcpartsbackend.models.User;
import erp.pcpartsbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(
                    "Users not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") UUID userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            return new ResponseEntity<>(
                    "User not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        if(userService.existById(user.getUserId())){
            return new ResponseEntity<>(
                    "User with that id already exists",
                    HttpStatus.CONFLICT);
        }
        User savedUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(savedUser);
    }

    @PutMapping("users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable("id") UUID userId) {
        user.setUserId(userId);
        if(!userService.existById(user.getUserId())){
            return new ResponseEntity<>(
                    "User with that id doesn't exist",
                    HttpStatus.CONFLICT);
        }
        User savedUser = userService.addUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(savedUser);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID userId) {
        if(!userService.existById(userId)){
            return new ResponseEntity<>(
                    "User with that id doesn't exist",
                    HttpStatus.CONFLICT);
        }
        userService.deleteUser(userService.getUser(userId));
        return ResponseEntity.status(HttpStatus.OK).body("User with that id has been deleted");
    }
}
