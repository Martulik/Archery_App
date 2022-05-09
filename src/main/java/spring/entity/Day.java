package spring.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "days")
public class Day
{
    @Id
    @Column(name = "date", nullable = false)
    private LocalDate date;
    @Basic
    @Column(name = "time_start")
    private LocalTime timeStart;
    @Basic
    @Column(name = "time_end")
    private LocalTime timeEnd;
    @Basic
    @Column(name = "are_lessons")
    private Boolean areLessons;
}
