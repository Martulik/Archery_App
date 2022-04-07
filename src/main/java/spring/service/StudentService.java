package spring.service;

import spring.entity.ProfileStatus;
import spring.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getStudentsByStatus(String status);
    Student findStudentById(long id);
    void updateProfileStatus(long student_id, String status);

    void updateRank(long student_id, String rank);
}
