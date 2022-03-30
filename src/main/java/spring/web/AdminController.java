package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.entity.Student;
import spring.repositories.ProfileStatusRepository;
import spring.repositories.StudentRepository;

import java.util.List;

@RestController
@RequestMapping("/archery/admin")
public class AdminController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ProfileStatusRepository profileStatusRepository;

    @GetMapping(value = "/listToApprove")
    public List<Student> needToApproveList() { //??
        return studentRepository.findStudentsByProfileStatus("onChecking");
    }

    //метод approve

    //метод disapprove (с отправкой сообщения почему)

}
