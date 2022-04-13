package spring.service;

import spring.entity.Student;
import spring.requests.RegisterRequest;

import java.util.List;

public interface StudentService {

    Student createStudent(RegisterRequest request);
    List<Student> getStudentsByStatus(String status);
    Student findStudentById(long id);
    Student findStudentByEmail(String email);
    Student findStudentByPhoneNumber(String phone);
    void updateProfileStatus(long student_id, String status);
    void updateRank(long student_id, String rank);
    void updateToken(String token);

}