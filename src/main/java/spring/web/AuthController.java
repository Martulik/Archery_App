package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.entity.User;
import spring.repositories.UserRepository;
import spring.requests.AuthRequest;
import spring.security.jwt.JwtTokenProvider;

import java.util.Optional;

@RestController
@RequestMapping("/archery/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder pwdEncoder;

    @PostMapping(value = "/signIn", consumes = "application/json")
    public ResponseEntity<String> singIn(@RequestBody AuthRequest request) {
        try{
            String login = request.getEmail();
            String password = request.getPassword();
            Optional<User> user = userRepository.findUserByLogin(login);
            boolean passwordMatch = false;
            if (user.isPresent()) {
                User us = user.get();
                passwordMatch = pwdEncoder.matches(password, us.getPassword());
            }

            if (!passwordMatch)
                throw new BadCredentialsException("Invalid username or password");


            String token = jwtTokenProvider.createToken(
                    login,
                    user
                            .orElseThrow(() -> new UsernameNotFoundException("User not found")).getRoles()
            );

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

}
