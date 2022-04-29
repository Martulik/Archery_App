package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import spring.entity.Student;
import spring.service.StudentService;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/archery/profile")
public class ProfileController {

    @Autowired
    StudentService studentService;

    @GetMapping(value = "/getFirstName")
    public List<String> getFirstName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> list = new LinkedList<>();

//        if (auth == null) { // не должно никогда срабатывать, тк отлавливается в JwtFilter
//            //значит время сеанса истекло
//            //и тут перенаправлять на страницу с логином
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); //401
//        }

//        System.out.println("Выводим лист зареганных");
//        var list = auth.getAuthorities();
//        for (Object i : list) {
//            System.out.println(i.toString());
//        }
//        System.out.println();
//
//        Object student = auth.getPrincipal();
//        System.out.println(student.toString());

        Student student = (Student) auth.getPrincipal();
        try {
            //return new ResponseEntity(HttpStatus.BAD_REQUEST); //400
            list.add(student.getFirst_name());
            return list;
        } catch (Exception e) {
            //return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE); //406
            return null;
        }
    }

//    @GetMapping(value = "/getLastName")
//    public ResponseEntity<String> getLastName() {
//        Student student = (Student) authenticationFacade.getAuthentication().getPrincipal();
//        return ResponseEntity.ok(student.getLast_name());
//    }
//
//    @GetMapping(value = "/getPhoneNumber")
//    public ResponseEntity<String> getPhoneNumber() {
//        Student student = (Student) authenticationFacade.getAuthentication().getPrincipal();
//        return ResponseEntity.ok(student.getPhone_number());
//    }
//
//    @GetMapping(value = "/getEmail")
//    public ResponseEntity<String> getEmail() {
//        Student student = (Student) authenticationFacade.getAuthentication().getPrincipal();
//        return ResponseEntity.ok(student.getEmail());
//    }
//
//    @GetMapping(value = "/getBirthDate")
//    public ResponseEntity<String> getBirthDate() {
//        Student student = (Student) authenticationFacade.getAuthentication().getPrincipal();
//        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
//        return ResponseEntity.ok(df.format(student.getBirth_date()));
//    }
//
//    @GetMapping(value = "/getAttentedClasses")
//    public ResponseEntity<String> getAttentedClasses() {
//        Student student = (Student) authenticationFacade.getAuthentication().getPrincipal();
//        return ResponseEntity.ok(Integer.toString(student.getAttended_classes()));
//    }
//
//    @GetMapping(value = "/getRankName")
//    public ResponseEntity<String> getRankName() {
//        Student student = (Student) authenticationFacade.getAuthentication().getPrincipal();
//        return ResponseEntity.ok(student.getRank_name().getRank_name());
//    }
//
//    @GetMapping(value = "/getProfileStatus")
//    public ResponseEntity<String> getProfileStatus() {
//        Student student = (Student) authenticationFacade.getAuthentication().getPrincipal();
//        return ResponseEntity.ok(student.getProfile_status().getStatus());
//    }
//
//
//    @PostMapping(value = "/updateFirstName", consumes = "application/json") //consume type?
//    public ResponseEntity<String> updateFirstName(@RequestBody String firstName) {
//        Student student = (Student) authenticationFacade.getAuthentication().getPrincipal();
//        studentService.updateFirstName(student.getId(), firstName);
//        return ResponseEntity.ok(firstName);
//    }
//
//    @PostMapping(value = "/updateLastName", consumes = "application/json")
//    public ResponseEntity<String> updateLastName(@RequestBody String lastName) {
//        Student student = (Student) authenticationFacade.getAuthentication().getPrincipal();
//        studentService.updateLastName(student.getId(), lastName);
//        return ResponseEntity.ok(lastName);
//    }
//
//    @PostMapping(value = "/updatePhoneNumber", consumes = "application/json")
//    public ResponseEntity<String> updatePhoneNumber(@RequestBody String phoneNumber) {
//        Student student = (Student) authenticationFacade.getAuthentication().getPrincipal();
//        studentService.updatePhoneNumber(student.getId(), phoneNumber);
//        return ResponseEntity.ok(phoneNumber);
//    }
//
//    @PostMapping(value = "/updateBirthDate", consumes = "application/json")
//    public ResponseEntity<String> updateBirthDate(@RequestBody String birthDate) {
//        Student student = (Student) authenticationFacade.getAuthentication().getPrincipal();
//        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
//        Date date;
//        try {
//            date = df.parse(birthDate);
//            //проверка на дату как при создании добавить
//        } catch (ParseException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        studentService.updateBirthDate(student.getId(), date);
//        return ResponseEntity.ok(birthDate);
//    }
}
