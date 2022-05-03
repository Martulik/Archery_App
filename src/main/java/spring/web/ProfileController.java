package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.entity.Student;
import spring.service.StudentService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/archery/profile")
public class ProfileController {

    @Autowired
    StudentService studentService;

    @GetMapping(value = "/getFirstName")
    public String getFirstName(Authentication auth) {
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) { // не должно никогда срабатывать, тк отлавливается в JwtFilter
                            //значит время сеанса истекло и тут перенаправлять на страницу с логином
            //return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); //401
            return null;
        }

        String login = auth.getName();
        System.out.println(login);

        Student student = studentService.findStudentByEmail(login);
        System.out.println(student.toString());
        String FirstName = student.getFirst_name();
        System.out.println(FirstName);
        return FirstName;
    }


    @GetMapping(value = "/getLastName")
    public ResponseEntity<String> getLastName() {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(student.getLast_name());
    }

    @GetMapping(value = "/getPhoneNumber")
    public ResponseEntity<String> getPhoneNumber() {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(student.getPhone_number());
    }

    @GetMapping(value = "/getEmail")
    public ResponseEntity<String> getEmail() {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(student.getEmail());
    }

    @GetMapping(value = "/getBirthDate")
    public ResponseEntity<String> getBirthDate() {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        return ResponseEntity.ok(df.format(student.getBirth_date()));
    }

    @GetMapping(value = "/getAttentedClasses")
    public ResponseEntity<String> getAttentedClasses() {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(Integer.toString(student.getAttended_classes()));
    }

    @GetMapping(value = "/getRankName")
    public ResponseEntity<String> getRankName() {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(student.getRank_name().getRank_name());
    }

    @GetMapping(value = "/getProfileStatus")
    public ResponseEntity<String> getProfileStatus() {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(student.getProfile_status().getStatus());
    }


    @PostMapping(value = "/updateFirstName", consumes = "application/json") //consume type?
    public ResponseEntity<String> updateFirstName(@RequestBody String firstName) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        studentService.updateFirstName(student.getId(), firstName);
        return ResponseEntity.ok(firstName);
    }

    @PostMapping(value = "/updateLastName", consumes = "application/json")
    public ResponseEntity<String> updateLastName(@RequestBody String lastName) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        studentService.updateLastName(student.getId(), lastName);
        return ResponseEntity.ok(lastName);
    }

    @PostMapping(value = "/updatePhoneNumber", consumes = "application/json")
    public ResponseEntity<String> updatePhoneNumber(@RequestBody String phoneNumber) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        studentService.updatePhoneNumber(student.getId(), phoneNumber);
        return ResponseEntity.ok(phoneNumber);
    }

    @PostMapping(value = "/updateBirthDate", consumes = "application/json")
    public ResponseEntity<String> updateBirthDate(@RequestBody String birthDate) {
        Student student = (Student) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        try {
            date = df.parse(birthDate);
            //проверка на дату как при создании добавить
        } catch (ParseException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        studentService.updateBirthDate(student.getId(), date);
        return ResponseEntity.ok(birthDate);
    }
}
