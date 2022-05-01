package spring.service;

import spring.entity.Request;
import spring.entity.RequestStatus;
import spring.entity.Student;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface RequestService {
    Boolean existsByStudentIdAndTime(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd);
    Boolean existsByStudentIdAndDate(Long studentId, LocalDate date);
    Boolean addRequest(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd);
    void removeByStudentIdAndTime(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd);
    List<Student> findStudentsByDate(LocalDate date);
    RequestStatus showStatusByStudentIdAndDate(Long studentId, LocalDate date);
    void changePresenceOfStudent(Long studentId, LocalDate date, Boolean hasCome);
    List<Student> findStudentsByTime(LocalDate date, LocalTime timeStart, LocalTime timeEnd);





    List<Request> findByStatus(String status);
    void updateStatus(String status, long requestId);


    void removeRequest(Long requestId);
    void removeByDate(LocalDate date);

    void removeActiveRequestsByStudent(Long studentId, LocalDate date, LocalTime time);


}
