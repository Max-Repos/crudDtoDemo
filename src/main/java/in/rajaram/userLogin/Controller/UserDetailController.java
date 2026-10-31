package in.rajaram.userLogin.Controller;

import in.rajaram.userLogin.Entity.User;
import in.rajaram.userLogin.Service.UserDetailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserDetailController {

    @GetMapping("/users")
    public String getUsers() {
        return "Users endpoint works";
    }

    @Autowired
    private UserDetailService userDetailService;

    //CREATE USER
    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDetailService.createUser(user));
    }

    // ALL USERS
    @GetMapping(value = "/allUser")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userDetailService.findAllUsers());
    }

    // USER BY ID
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(userDetailService.findUserById(id));
    }

    //UPDATE USER
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDetailService.updateUser(id, user));
    }

    //DELETE USER
    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userDetailService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    //EXCEPTION HANDLING
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
