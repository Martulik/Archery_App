package spring.web;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import spring.entity.Student;
import spring.exception.InvalidRegisterException;
import spring.repositories.ProfileStatusRepository;
import spring.repositories.StudentRepository;
import spring.requests.AuthRequest;
import spring.requests.RegisterRequest;
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
    PasswordEncoder pwdEncoder;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfileStatusRepository profileStatusRepository;



    @PostMapping(value = "/signIn", consumes = "application/json")
    public ResponseEntity<String> signIn(@RequestBody AuthRequest request) {
        try{
            String login = request.getLogin();
            String password = request.getPassword();
            Optional<Student> student = studentRepository.findUserByEmail(login); //изменить на поиск по email или телефону
            boolean passwordMatch = false;
            if (student.isPresent()) {
                Student st = student.get();
                passwordMatch = pwdEncoder.matches(password, st.getPassword_hash());
            } else {
                student = studentRepository.findUserByPhone_number(login);
                if (student.isPresent()) {
                    Student st = student.get();
                    passwordMatch = pwdEncoder.matches(password, st.getPassword_hash());
                }
            }
            if (!passwordMatch)
                throw new BadCredentialsException("Invalid username or password");

            String token = jwtTokenProvider.createToken(
                    login,
                    student
                            .orElseThrow(() -> new UsernameNotFoundException("User not found")).getRoles()
            );

            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        //добавить проверку на дибила
        String email = request.getEmail();
        String phone_number = request.getPhone_number();
        Optional<Student> studentEmail = studentRepository.findUserByEmail(email);
        Optional<Student> studentPhone = studentRepository.findUserByPhone_number(phone_number);

        if (studentEmail.isPresent() || studentPhone.isPresent()) {
            //тут надо перенаправить на страницу со входом
            throw new InvalidRegisterException("This student is registered, please just sign in");
        }
        Student student = new Student();
        BeanUtils.copyProperties(request, student); //проверить правильно ли работает
        student.setProfileStatus(profileStatusRepository.findByStatus("OnChecking").get()); //заполнить таблицу статусов

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
    }
}
