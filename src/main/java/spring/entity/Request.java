package spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "requests")
public class Request {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "request_id")
    private Long requestId;
    @Basic
    @Column(name = "student_id")
    private Long studentId;
    @Basic
    @Column(name = "date")
    private Date date;
    @Basic
    @Column(name = "time_start")
    private Time timeStart;
    @Basic
    @Column(name = "time_end")
    private Time timeEnd;
    @Basic
    @Column(name = "status")
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return requestId == request.requestId && studentId == request.studentId && Objects.equals(date, request.date) && Objects.equals(timeStart, request.timeStart) && Objects.equals(timeEnd, request.timeEnd) && Objects.equals(status, request.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, studentId, date, timeStart, timeEnd, status);
    }
}
