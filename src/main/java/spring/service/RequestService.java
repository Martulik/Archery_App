package spring.service;

import spring.entity.Day;
import spring.entity.Request;
import spring.entity.RequestStatus;
import spring.entity.Student;
import spring.requests.NumberOfStudentsAndShields;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public interface RequestService {
    List<Request> findByTime(LocalDate date, LocalTime timeStart, LocalTime timeEnd);
    Boolean existsByStudentIdAndTime(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd);

    void removeByStudentIdAndTime(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd);
    List<Student> findStudentsByDate(LocalDate date);
    RequestStatus showStatusByStudentIdAndDate(Long studentId, LocalDate date);
    void changePresenceOfStudent(Long studentId, LocalDate date, Boolean hasCome);
    List<Student> findStudentsByTime(LocalDate date, LocalTime timeStart, LocalTime timeEnd);
    void removeRequest(Long requestId);
    void updateStatus(String status, long requestId);
    List<Request> findByStatus(String status);
    List<Request> findByStudentIdAndDate(Long studentId, LocalDate date);

    Boolean existsByStudentIdAndDate(Long studentId, LocalDate date);
    List<Day> findActiveDaysWithRequests(Long studentId, LocalDate date);


    List<String> showInfoAboutSession(Long studentId, LocalDate date, LocalTime timeStart); //не трогала, разбила на методы ниже


    NumberOfStudentsAndShields getNumberOfStudentsAndShields(LocalDate date, LocalTime timeStart);
    boolean isAvailableLesson(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd);
    boolean isAvailableSession(Long studentId, LocalDate date, LocalTime timeStart);
    String setTimeEnd(LocalTime timeStart, String seasonTicket);
    String setSeasonTicket(Long studentId, LocalDate date);


    void addRequest(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd);
    void removeByStudentIdAndDateTimeStart(Long studentId, LocalDate date, LocalTime timeStart);





    /*void removeByDate(LocalDate date);
    void removeActiveRequestsByStudent(Long studentId, LocalDate date, LocalTime time);*/

}
