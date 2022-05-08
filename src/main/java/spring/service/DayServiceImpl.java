package spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.Day;
import spring.exception.DayNotFoundException;
import spring.exception.StartAfterEndException;
import spring.repositories.DayRepository;
import spring.repositories.RequestRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DayServiceImpl implements DayService
{
    private final DayRepository dayRepository;

    public Day findByDate(LocalDate date)
    {
        Optional<Day> optionalDay = dayRepository.findByDate(date);
        if (optionalDay.isPresent())
        {
            return optionalDay.get();
        }
        throw new DayNotFoundException("Day is not found");
    }

    public List<Day> findFromTo(LocalDate start, LocalDate end)
    {
        return dayRepository.findFromTo(start, end);
    }








    /*public Boolean areLessons(LocalDate date)
    {
        return dayRepository.findByDateAndAreLessons(date, true);
    }

    public void changeAreLessons(LocalDate date)
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

    public void changeTimeStart(LocalDate date, LocalTime timeStart)
    {
        Day day = findByDate(date);
        requestRepository.removeByDayDate(date); //добавить случай, когда начало ставят позже конца
        if (day.getTimeEnd().isBefore(timeStart))
        {
            throw new StartAfterEndException("Start is after end");
        }
        day.setTimeStart(timeStart);
    }

    public void changeTimeEnd(LocalDate date, LocalTime timeEnd)
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
    public void setRequestRepository(RequestRepository requestRepository)
    {
        this.requestRepository = requestRepository;
    }*/
}
