package spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.entity.Day;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DayRepository extends JpaRepository<Day, String>
{
    Optional<Day> findByDate(LocalDate date);

    @Query("select d from Day d where d.date >= :start and d.date <= :end")
    List<Day> findFromTo(LocalDate start, LocalDate end);


    //Boolean findByDateAndAreLessons(LocalDate date, Boolean areLessons);
}
