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
import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Boolean existsByStudentIdAndDayDateAndTimeStartAndTimeEnd(Long studentId, Date date, LocalTime timeStart, LocalTime timeEnd);
    Boolean existsByStudentIdAndDayDate(Long studentId, Date date);
    @Query("select r from Request r where (r.timeStart >= ?2 and r.timeStart <= ?3) or (r.timeEnd >= ?2 and r.timeEnd <= ?3) or (r.timeStart < ?2 and r.timeEnd > ?3)")
    List<Request> findIfIntersectByTime(Date date, LocalTime timeStart, LocalTime timeEnd);
    void removeByStudentIdAndDayDateAndTimeStartAndTimeEnd(Long studentId, Date date, LocalTime timeStart, LocalTime timeEnd);
    List<Request> findByDayDate(Date date);
    Optional<RequestStatus> findRequestStatusByStudentIdAndDayDate(Long studentId, Date date);
    @Transactional
    @Modifying
    @Query("update Request r set r.status = :status where r.student.id = :studentId and r.day.date = :date")
    void updateStatusByDate(long studentId, Date date, RequestStatus status);





    List<Request> findByStatusStatus(String status);

    @Transactional
    @Modifying
    @Query("update Request r set r.status = :status where r.requestId = :requestId")
    void updateStatus(String status, long requestId);

    void removeByRequestId(Long requestId);
    void removeByDayDate(Date date);

    @Transactional
    @Modifying
    @Query("delete from Request r where r.student.id = :studentId and r.day.date >= :date and r.timeStart >= :time")
    void removeActiveRequestsByStudentId(Long studentId, Date date, LocalTime time);
}
