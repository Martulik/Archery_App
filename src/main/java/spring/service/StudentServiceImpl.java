package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.ProfileStatus;
import spring.entity.Student;
import spring.repositories.StudentRepository;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getStudentsByStatus(String status) {
        return (List<Student>) studentRepository.findStudentsByProfileStatus(status);
    }
}
