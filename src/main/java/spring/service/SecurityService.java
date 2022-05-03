package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.entity.Student;

@Service
public class SecurityService {

    private final AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder pwdEncoder;
    @Autowired
    private StudentService studentService;

    @Autowired
    public SecurityService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void autologin(String login, String password) {
        boolean passwordMatch = false;

        Student student = studentService.findStudentByEmail(login);
        if (student != null) {
            passwordMatch = pwdEncoder.matches(password, student.getPassword_hash());
        } else {
            student = studentService.findStudentByPhoneNumber(login);
            if (student != null) {
                passwordMatch = pwdEncoder.matches(password, student.getPassword_hash());
            }
        }

        if (!passwordMatch) {
            throw new BadCredentialsException("Invalid username or password");
        }
        Authentication usernamePasswordAuthenticationToken = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(login, password));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}