package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.entity.Student;
import spring.service.ProfileStatusService;
import spring.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/archery/admin")
public class AdminController {

    @Autowired
    StudentService studentService;
    @Autowired
    ProfileStatusService profileStatusService;

    @GetMapping(value = "/listToApprove")
    public List<Student> needToApproveList() { //??
        return studentService.getStudentsByStatus("onChecking");
    }

    public void approve(Student student, String rank) {
        studentService.updateProfileStatus(student.getId(), "approved");
        studentService.updateRank(student.getId(), rank);
    }

    public void disapprove() {
        //надо отправить сообщение на почту почему или позвонить
    }

}
