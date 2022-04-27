package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.Day;
import spring.exception.DayNotFoundException;
import spring.exception.StartAfterEndException;
import spring.repositories.DayRepository;
import spring.repositories.RequestRepository;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DayServiceImpl implements DayService
{
    private DayRepository dayRepository;
    private RequestRepository requestRepository;

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
        return dayRepository.findByDateAndAreLessons(date, true);
    }

    public void changeAreLessons(Date date)
    {
        Day day = findByDate(date);
        if (day.getAreLessons())
        {
            requestRepository.removeByDayDate(date);
            day.setAreLessons(false);
        }
        else
        {
            day.setAreLessons(true);
        }
    }

    public void changeTimeStart(Date date, LocalTime timeStart)
    {
        Day day = findByDate(date);
        requestRepository.removeByDayDate(date); //добавить случай, когда начало ставят позже конца
        if (day.getTimeEnd().isBefore(timeStart))
        {
            throw new StartAfterEndException("Start is after end");
        }
        day.setTimeStart(timeStart);
    }

    public void changeTimeEnd(Date date, LocalTime timeEnd)
    {
        Day day = findByDate(date);
        requestRepository.removeByDayDate(date);
        if (day.getTimeStart().isAfter(timeEnd))
        {
            throw new StartAfterEndException("Start is after end");
        }
        day.setTimeEnd(timeEnd);
    }

    @Autowired
    public void setDayRepository(DayRepository dayRepository)
    {
        this.dayRepository = dayRepository;
    }
    @Autowired
    public void setRequestRepository(RequestRepository requestRepository)
    {
        this.requestRepository = requestRepository;
    }
}
