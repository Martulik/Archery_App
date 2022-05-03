package spring.service;

import org.springframework.transaction.annotation.Transactional;
import spring.entity.Rank;
import spring.entity.Student;
import spring.requests.RegisterRequest;

import java.util.Date;
import java.util.List;

public interface StudentService {

    Student createStudent(RegisterRequest request);
    List<Student> getAllStudents();
    List<Student> getStudentsByStatus(String status);
    Student findStudentById(long id);
    Student findStudentByEmail(String email);
    Student findStudentByPhoneNumber(String phone);
    Student findStudentByToken(String token);

    void updateFirstName(long student_id, String firstName);
    void updateLastName(long student_id, String lastName);
    void updatePhoneNumber(long student_id, String phoneNumber);
    void updateEmail(long student_id, String email);
    void updateBirthDate(long student_id, Date birthDate);

    void updateProfileStatus(long student_id, String status);
    void updateRank(long student_id, String rank);
    void updateHasPaid(long student_id, Boolean hasPaid);

    Rank getRank(long id);
    Boolean hasPaid(long id);
    void updateToken(Long id, String token);
    void changeAttendedClasses(Long id, Boolean toIncrease);
}
