package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.ProfileStatus;
import spring.entity.Rank;
import spring.entity.Student;
import spring.exception.StudentNotFoundException;
import spring.repositories.ProfileStatusRepository;
import spring.repositories.RankRepository;
import spring.repositories.StudentRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ProfileStatusRepository profileStatusRepository;
    @Autowired
    private RankRepository rankRepository;

    @Override
    public List<Student> getStudentsByStatus(String status) {
        return studentRepository.findStudentsByProfileStatus(status);
    }

    @Override
    public Student findStudentById(long id) {
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        }
        throw new StudentNotFoundException("Student not found");
    }

    @Override
    public void updateProfileStatus(long student_id, String status) {
        Optional<ProfileStatus> optionalProfileStatus = profileStatusRepository.findByStatus(status);
        Optional<Student> optionalStudent = studentRepository.findById(student_id);
        if (optionalProfileStatus.isPresent() && optionalStudent.isPresent()) {
            studentRepository.updateProfileStatus(student_id, optionalProfileStatus.get());
        }
        throw new NoSuchElementException("Invalid student_id or profile_status");
    }

    @Override
    public void updateRank(long student_id, String rank) {
        Optional<Rank> optionalRank = rankRepository.findByRank_name(rank);
        Optional<Student> optionalStudent = studentRepository.findById(student_id);
        if(optionalRank.isPresent() && optionalStudent.isPresent()) {
            studentRepository.updateRank(student_id, optionalRank.get());
        }
        throw new NoSuchElementException("Invalid student_id or rank_name");
    }
}
