package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.entity.ProfileStatus;
import spring.entity.Student;
import spring.exception.InvalidRegisterException;
import spring.repositories.StudentRepository;
import spring.requests.AuthRequest;
import spring.requests.RegisterRequest;
import spring.security.jwt.JwtTokenProvider;
import spring.service.ProfileStatusService;
import spring.service.StudentService;
import spring.utils.ProfileStatusConstants;

@RestController
@RequestMapping("/archery/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    PasswordEncoder pwdEncoder;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;
    @Autowired
    ProfileStatusService profileStatusService;

    ProfileStatusConstants statusConst;

    @PostMapping(value = "/signIn", consumes = "application/json")
    public ResponseEntity<String> signIn(@RequestBody AuthRequest request) {
        try{
            String login = request.getLogin();
            String password = request.getPassword();
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

            if (!passwordMatch)
                throw new BadCredentialsException("Invalid username or password");

            String token = jwtTokenProvider.createToken(
                    login,
                    student.getRoles()
            );

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String email = request.getEmail();
        String phone_number = request.getPhone_number();
        Student studentEmail = studentService.findStudentByEmail(email);
        Student studentPhone = studentService.findStudentByPhoneNumber(phone_number);
        if (studentPhone != null) {
            ProfileStatus status = profileStatusService.findByProfileStatus(studentPhone.getProfile_status());
            if (!status.getStatus().equals(statusConst.NOT_REGISTERED)) {
                throw new InvalidRegisterException("This student is registered, please just sign in");
            }
        }
        if (studentEmail!= null) {
            //тут надо перенаправить на страницу со входом?
            throw new InvalidRegisterException("This student is registered, please just sign in");
        }
        Student student = studentService.createStudent(request);
        studentRepository.save(student);

        String token = jwtTokenProvider.createToken(
                email,
                student.getRoles()
        );

        return ResponseEntity.ok(token);
    }

    @PutMapping("/exit")
    public void exit() {
        //Вернуть на главную страницу и очистить токен?
        studentService.updateToken(null);
    }
}
