package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.Day;
import spring.exception.DayNotFoundException;
import spring.repositories.DayRepository;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DayServiceImpl implements DayService
{
    private DayRepository dayRepository;

    public Day findByDate(Date date)
    {
        Optional<Day> optionalDay = dayRepository.findByDate(date);
        if (optionalDay.isPresent())
        {
            return optionalDay.get();
        }
        throw new DayNotFoundException("Day is not found");
    }

    public List<Day> findFromTo(Date start, Date end)
    {
        return dayRepository.findFromTo(start, end);
    }

    public Boolean areLessons(Date date)
    {
        Optional<Day> optionalDay = dayRepository.findByDateAndTimeStart(date, Time.valueOf("0:00:00"));
        return optionalDay.isEmpty();
    }

    @Autowired
    public void setDayRepository(DayRepository dayRepository)
    {
        this.dayRepository = dayRepository;
    }
}
