package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
=======
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
>>>>>>> k.borisova
import org.springframework.web.bind.annotation.*;
import spring.entity.ProfileStatus;
import spring.entity.Student;
import spring.repositories.StudentRepository;
import spring.requests.AuthRequest;
import spring.requests.RegisterRequest;
import spring.security.jwt.JwtTokenProvider;
import spring.service.SecurityService;
import spring.service.ProfileStatusService;
import spring.service.StudentService;
import spring.utils.ProfileStatusConstants;

@RestController
@RequestMapping("/archery/auth")
public class AuthController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
<<<<<<< HEAD
    PasswordEncoder pwdEncoder;

    @Autowired
=======
>>>>>>> k.borisova
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;
    @Autowired
    ProfileStatusService profileStatusService;
    @Autowired
    SecurityService securityService;

    @PostMapping(value = "/signIn", consumes = "application/json")
    public ResponseEntity signIn(@RequestBody AuthRequest request) {
        try {
            String login = request.getLogin();
            String password = request.getPassword();
<<<<<<< HEAD
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

            String token = jwtTokenProvider.createToken(
                    login,
                    student.getRoles()
            );
            studentService.updateToken(student.getId(), token);
            return new ResponseEntity(HttpStatus.OK);
=======
//            boolean passwordMatch = false;
//
//            Student student = studentService.findStudentByEmail(login);
//            if (student != null) {
//                passwordMatch = pwdEncoder.matches(password, student.getPassword_hash());
//            } else {
//                student = studentService.findStudentByPhoneNumber(login);
//                if (student != null) {
//                    passwordMatch = pwdEncoder.matches(password, student.getPassword_hash());
//                }
//            }
//
//            if (!passwordMatch) {
//                throw new BadCredentialsException("Invalid username or password");
//            }
//
//            String token = jwtTokenProvider.createToken(
//                    login,
//                    student.getRoles()
//            );
//            studentService.updateToken(student.getId(), token);
            securityService.autologin(login, password);
//            Authentication r = new UsernamePasswordAuthenticationToken(login, password);
//            Authentication result = authenticationManager.authenticate(r);
//            SecurityContextHolder.getContext().setAuthentication(result);
            return new ResponseEntity<>(HttpStatus.OK);
>>>>>>> k.borisova
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity register(@RequestBody RegisterRequest request) {
        String email = request.getEmail();
        String phone_number = request.getPhone_number();
        Student studentEmail = studentService.findStudentByEmail(email);
        Student studentPhone = studentService.findStudentByPhoneNumber(phone_number);
        if (studentPhone != null) {
            ProfileStatus status = profileStatusService.findByProfileStatus(studentPhone.getProfile_status());
            if (!status.getStatus().equals(ProfileStatusConstants.NOT_REGISTERED)) {
                return new ResponseEntity(HttpStatus.ALREADY_REPORTED);
            }
        }
        if (studentEmail != null) {
            //тут надо перенаправить на страницу со входом?
            return new ResponseEntity(HttpStatus.ALREADY_REPORTED);
        }
        Student student = studentService.createStudent(request);
        studentRepository.save(student);

        String token = jwtTokenProvider.createToken(
                email,
                student.getRoles()
        );
        studentService.updateToken(student.getId(), token);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/exit")
    public void exit() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Student student = (Student) auth.getPrincipal();
        studentService.updateToken(student.getId(), null);
        SecurityContextHolder.getContext().setAuthentication(null);
    }
}