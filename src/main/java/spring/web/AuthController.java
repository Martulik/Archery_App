package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.entity.ProfileStatus;
import spring.entity.Student;
import spring.exception.StudentNotFoundException;
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
    StudentRepository studentRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    ProfileStatusService profileStatusService;
    @Autowired
    SecurityService securityService;


    @PostMapping(value = "/signIn", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> signIn(@RequestBody AuthRequest request) {
        try {
            String login = request.getLogin();
            String password = request.getPassword();
            securityService.autologin(login, password);
            Student student = studentService.findStudentByEmail(login);
            String token = jwtTokenProvider.createToken(student.getEmail(), student.getRoles());
            studentService.updateToken(student.getId(), token);
            return ResponseEntity.ok(token);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<HttpStatus> register(@RequestBody RegisterRequest request) {
        String email = request.getEmail();
        String phone_number = request.getPhone_number();
        Student studentPhone;
        try {
            Student studentEmail = studentService.findStudentByEmail(email);
        } catch (StudentNotFoundException e) {
            try {
                studentPhone = studentService.findStudentByPhoneNumber(phone_number);
            } catch (StudentNotFoundException ex) {
                Student student = studentService.createStudent(request);
                studentRepository.save(student);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            ProfileStatus status = profileStatusService.findByProfileStatus(studentPhone.getProfile_status());
            if (!status.getStatus().equals(ProfileStatusConstants.NOT_REGISTERED)) {
                return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
            }
            Student student = studentService.createStudent(request);
            studentRepository.save(student);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
    }


    @PostMapping(value = "/refreshToken", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> refreshToken(@RequestBody String oldToken) { //возможно тут надо делать обертку для реквеста
        try {
            Student student = studentService.findStudentByToken(oldToken);
            String token = jwtTokenProvider.createToken(student.getEmail(), student.getRoles());
            studentService.updateToken(student.getId(), token);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);//400
        }
    }


    @PutMapping("/exit")
    public void exit() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Student student = studentService.findStudentByEmail(auth.getName());
            studentService.updateToken(student.getId(), null);
            SecurityContextHolder.getContext().setAuthentication(null);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}