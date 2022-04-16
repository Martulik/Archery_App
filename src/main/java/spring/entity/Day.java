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
@Table(name = "days")
public class Day {
    @Id
    @Column(name = "date", nullable = false)
    private Date date;
    @Basic
    @Column(name = "time_start")
    private Time timeStart;
    @Basic
    @Column(name = "time_end")
    private Time timeEnd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return Objects.equals(date, day.date) && Objects.equals(timeStart, day.timeStart) && Objects.equals(timeEnd, day.timeEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, timeStart, timeEnd);
    }
}
