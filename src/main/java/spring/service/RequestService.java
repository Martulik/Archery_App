package spring.service;

import spring.entity.Day;
import spring.entity.Request;
import spring.entity.Student;

import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;
import java.util.List;

public interface RequestService {
    List<Request> findByStatus(String status);
    void updateStatus(String status, long requestId);
    Boolean existsByStudentAndTime(Long studentId, Date date, LocalTime timeStart, LocalTime timeEnd);
    Boolean existsByStudentAndDate(Long studentId, Date date);
    void removeRequest(Long requestId);
    void removeByDate(Date date);
    void removeByStudentAndTime(Long studentId, Date date, LocalTime timeStart, LocalTime timeEnd);
    void removeActiveRequestsByStudent(Long studentId, Date date, LocalTime time);
    void changeIfHasCome(Long studentId, Date date, Boolean hasCome);
    Boolean addRequest(Student student, Day day, LocalTime timeStart, LocalTime timeEnd);
}
