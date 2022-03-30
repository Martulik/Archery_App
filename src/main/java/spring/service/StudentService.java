package spring.service;

import spring.entity.ProfileStatus;
import spring.entity.Student;

import java.util.List;

public interface StudentService {

    public List<Student> getStudentsByStatus(String status);
}
