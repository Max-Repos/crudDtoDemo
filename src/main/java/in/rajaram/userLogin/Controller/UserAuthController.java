package in.rajaram.userLogin.Controller;

import in.rajaram.userLogin.Entity.UserAuthEntity;
import in.rajaram.userLogin.Service.UserAuthEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthEntityService userAuthEntityService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody UserAuthEntity userAuthEntity) {
        userAuthEntity.setPassword(passwordEncoder.encode(userAuthEntity.getPassword()));
        userAuthEntityService.save(userAuthEntity);
        return ResponseEntity.ok().body("User registered successfully");
    }
}
