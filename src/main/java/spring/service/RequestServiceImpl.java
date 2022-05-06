package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.*;
import spring.repositories.RequestRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static spring.Application.*;

@Service
public class RequestServiceImpl implements RequestService
{
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private StudentService studentService;
    @Autowired
    private DayService dayService;

    public Boolean existsByStudentIdAndTime(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd)
    {
        return requestRepository.existsByStudentIdAndDayDateAndTimeStartAndTimeEnd(studentId, date, timeStart, timeEnd);
    }

    public Boolean existsByStudentIdAndDate(Long studentId, LocalDate date)
    {
        return requestRepository.existsByStudentIdAndDayDate(studentId, date);
    }
    public Boolean addRequest(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd)
    {
        Day day = dayService.findByDate(date);
        Student student = studentService.findStudentById(studentId); //нужно ли обработать искл?
        final long SESSION = 30L;
        LocalTime localStart = timeStart;
        LocalTime localEnd = timeStart;
        while (localEnd.isBefore(timeEnd))
        {
            localEnd = localEnd.plusMinutes(SESSION);
            if (localEnd.isAfter(timeEnd))
            {
                localEnd = timeEnd;
            }
            int numberOfJuniors = 0;
            int numberOfMiddles = 0;
            int numberOfSeniors = 0;
            List<Request> requests = requestRepository.findIfIntersectByTime(day.getDate(), localStart, localEnd);
            for (Request request: requests)
            {
                switch (request.getStudent().getRank_name().getRank_name())
                {
                    case "JUNIOR" -> ++numberOfJuniors;
                    case "MIDDLE" -> ++numberOfMiddles;
                    default -> ++numberOfSeniors;
                }
            }
            if (numberOfJuniors > wishedNumberOfJuniors)
            {
                return false;
            }
            if (numberOfJuniors + numberOfMiddles > wishedNumberOfDemandingTrainer)
            {
                return false;
            }
            if (numberOfJuniors + numberOfMiddles / 2 + numberOfMiddles % 2 + numberOfSeniors / 2 + numberOfSeniors % 2 > numberOfShields)
            {
                return false;
            }
            localStart = localStart.plusMinutes(SESSION);
        }
        Request request = new Request();
        request.setDay(day);
        request.setStudent(student);
        request.setTimeStart(timeStart);
        request.setTimeEnd(timeEnd);
        requestRepository.save(request);
        return true;
    }

    public void removeByStudentIdAndTime(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd)
    {
        requestRepository.removeByStudentIdAndDayDateAndTimeStartAndTimeEnd(studentId, date, timeStart, timeEnd);
    }

    public List<Student> findStudentsByDate(LocalDate date)
    {
        List<Request> requests = requestRepository.findByDayDate(date);
        return requests.stream().map(Request::getStudent).distinct().collect(Collectors.toList());
    }

    public RequestStatus showStatusByStudentIdAndDate(Long studentId, LocalDate date)
    {
        return requestRepository.findRequestStatusByStudentIdAndDayDate(studentId, date).get();
    }

    public void changePresenceOfStudent(Long studentId, LocalDate date, Boolean hasCome)
    {
        RequestStatus oldStatus = requestRepository.findRequestStatusByStudentIdAndDayDate(studentId, date).get();
        RequestStatus requestStatus = new RequestStatus();
        if (hasCome)
        {
            requestStatus.setStatus("HAS_COME");//статусы есть: "не просмотрено" (по умолчанию); "просмотрено" (не выводить в списке заявок);
            //"пришел"; "не пришел" (уже после занятия, когда заполняется посещаемость)
            studentService.changeAttendedClasses(studentId, true); //при посещении надо изменить число посещенных занятий
        }
        else
        {
            requestStatus.setStatus("HAS_NOT_COME");
            if (oldStatus.getStatus().equals("HAS_COME")) //если старый статус был "пришел", то он изменяется в отсутствие и надо уменьшить число занятий
            {
                studentService.changeAttendedClasses(studentId, false);
            }
        }
        requestRepository.updateStatusByDate(studentId, date, requestStatus);
    }

    public List<Student> findStudentsByTime(LocalDate date, LocalTime timeStart, LocalTime timeEnd)
    {
        return requestRepository.findIfIntersectByTime(date, timeStart, timeEnd).stream().map(Request::getStudent).toList();
    }

    public void removeRequest(Long requestId)
    {
        requestRepository.removeByRequestId(requestId);
    }

    public void updateStatus(String status, long requestId)
    {
        RequestStatus requestStatus = new RequestStatus();
        requestStatus.setStatus(status);
        requestRepository.updateStatus(requestStatus, requestId);
    }

    public List<Request> findByStatus(String status)
    {
        return requestRepository.findByStatusStatus(status);
    }






    /*
    public void removeByDate(LocalDate date)
    {
        requestRepository.removeByDayDate(date);
    }

    public void removeActiveRequestsByStudent(Long studentId, LocalDate date, LocalTime time)
    {
        requestRepository.removeActiveRequestsByStudentId(studentId, date, time);
    }*/
}
