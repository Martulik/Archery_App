package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.entity.Day;
import spring.entity.Request;
import spring.entity.RequestStatus;
import spring.entity.Student;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Boolean existsByStudentIdAndDayDateAndTimeStartAndTimeEnd(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd);
    Boolean existsByStudentIdAndDayDate(Long studentId, LocalDate date);
    @Query("select r from Request r where r.day.date = ?1 and ((r.timeStart >= ?2 and r.timeStart <= ?3) or (r.timeEnd >= ?2 and r.timeEnd <= ?3) or (r.timeStart < ?2 and r.timeEnd > ?3))")
    List<Request> findIfIntersectByTime(LocalDate date, LocalTime timeStart, LocalTime timeEnd);
    void removeByStudentIdAndDayDateAndTimeStartAndTimeEnd(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd);
    List<Request> findByDayDate(LocalDate date);
    Optional<RequestStatus> findRequestStatusByStudentIdAndDayDate(Long studentId, LocalDate date);
    @Transactional
    @Modifying
    @Query("update Request r set r.status = :status where r.student.id = :studentId and r.day.date = :date")
    void updateStatusByDate(long studentId, LocalDate date, RequestStatus status);
    @Transactional
    @Modifying
    @Query("update Request r set r.status = :status where r.requestId = :requestId")
    void updateStatus(RequestStatus status, long requestId);
    List<Request> findByStatusStatus(String status);
    void removeByRequestId(Long requestId);
}
