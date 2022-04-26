package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.entity.Request;
import spring.entity.Student;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByStatusStatus(String status);

    @Transactional
    @Modifying
    @Query("update Request r set r.status = :status where r.requestId = :requestId")
    void updateStatus(String status, long requestId);
    @Transactional
    @Modifying
    @Query("update Request r set r.status = :status where r.student.id = :studentId and r.day.date = :date")
    void updateStatusByDate(String status, long studentId, Date date);
    Boolean existsByStudentIdAndDayDateAndTimeStartAndTimeEnd(Long studentId, Date date, LocalTime timeStart, LocalTime timeEnd);
    Boolean existsByStudentIdAndDayDate(Long studentId, Date date);
    @Query("select r from Request r where (r.timeStart >= ?2 and r.timeStart <= ?3) or (r.timeEnd >= ?2 and r.timeEnd <= ?3) or (r.timeStart < ?2 and r.timeEnd > ?3)")
    List<Request> findIfIntersectByTime(Date date, LocalTime timeStart, LocalTime timeEnd);
    void removeByRequestId(Long requestId);
    void removeByDayDate(Date date);
    void removeByStudentIdAndDayDateAndTimeStartAndTimeEnd(Long studentId, Date date, LocalTime timeStart, LocalTime timeEnd);
    @Transactional
    @Modifying
    @Query("delete from Request r where r.student.id = :studentId and r.day.date >= :date and r.timeStart >= :time")
    void removeActiveRequestsByStudentId(Long studentId, Date date, LocalTime time);
}
