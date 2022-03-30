package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.entity.ProfileStatus;
import spring.entity.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("select s from Student s where s.email = ?1")
    Optional<Student> findUserByEmail(String email);

    @Query("select s from Student s where s.phone_number = ?1")
    Optional<Student> findUserByPhone_number(String phone_number);

    @Query("select s from Student s where s.profileStatus.status = ?1")
    List<Student> findStudentsByProfileStatus(String status);
}
