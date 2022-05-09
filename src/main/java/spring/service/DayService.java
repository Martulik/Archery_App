package spring.service;

import spring.entity.Day;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DayService {
    Day findByDate(LocalDate date);
    List<Day> findFromTo(LocalDate start, LocalDate end);
    List<LocalDate> showMonth();




  /*  Boolean areLessons(LocalDate date);
    void changeAreLessons(LocalDate date);
    void changeTimeStart(LocalDate date, LocalTime timeStart);
    void changeTimeEnd(LocalDate date, LocalTime timeEnd);*/
}
