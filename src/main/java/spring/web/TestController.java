package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping(value = "/studentList")
    public List<Student> studentList() {
        return studentService.getAllStudents();
    }
}
