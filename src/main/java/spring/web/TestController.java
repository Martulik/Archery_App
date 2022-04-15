package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.entity.Student;
import spring.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/archery/test")
public class TestController {
    @Autowired
    StudentService studentService;

    static HttpHeaders header = new HttpHeaders();
    static HttpEntity<String> entity;

    @GetMapping(value = "/studentList")
    public List<Student> studentList() {
//        header.setAccessControlAllowOrigin("http://localhost:8080");
//        entity = new HttpEntity<>(null, header);
        return studentService.getAllStudents();
    }
}
