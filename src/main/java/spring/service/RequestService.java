package spring.service;

import spring.entity.Request;
import spring.entity.RequestStatus;
import spring.entity.Student;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface RequestService {
    Boolean existsByStudentIdAndTime(Long studentId, Date date, LocalTime timeStart, LocalTime timeEnd);
    Boolean existsByStudentIdAndDate(Long studentId, Date date);
    Boolean addRequest(Long studentId, Date date, LocalTime timeStart, LocalTime timeEnd);
    void removeByStudentIdAndTime(Long studentId, Date date, LocalTime timeStart, LocalTime timeEnd);
    List<Student> findStudentsByDate(Date date);
    RequestStatus showStatusByStudentIdAndDate(Long studentId, Date date);
    void changePresenceOfStudent(Long studentId, Date date, Boolean hasCome);
    List<Student> findStudentsByTime(Date date, LocalTime timeStart, LocalTime timeEnd);





    List<Request> findByStatus(String status);
    void updateStatus(String status, long requestId);


    void removeRequest(Long requestId);
    void removeByDate(Date date);

    void removeActiveRequestsByStudent(Long studentId, Date date, LocalTime time);


}
