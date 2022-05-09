package spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring.entity.*;
import spring.exception.RequestNotFound;
import spring.exception.RequestStatusNotFound;
import spring.exception.SeasonTicketNotFoundException;
import spring.repositories.RequestRepository;
import spring.repositories.RequestStatusRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.sql.Time;
import java.util.stream.Collectors;

import static spring.Application.*;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService
{

    private final RequestRepository requestRepository;
    private final StudentService studentService;
    private final DayService dayService;
    private final RequestStatusRepository requestStatusRepository;
    private final PurchaseHistoryService purchaseHistoryService;


    public List<Request> findByTime(LocalDate date, LocalTime timeStart, LocalTime timeEnd)
    {
        return requestRepository.findByDayDateAndTimeStartAndTimeEnd(date, timeStart, timeEnd);
    }

    public Boolean existsByStudentIdAndTime(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd)
    {
        return requestRepository.existsByStudentIdAndDayDateAndTimeStartAndTimeEnd(studentId, date, timeStart, timeEnd);
    }


    public void addRequest(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd)
    {
        Day day = dayService.findByDate(date);
        Student student = studentService.findStudentById(studentId);
        Request request = new Request();
        request.setDay(day);
        request.setStudent(student);
        request.setTimeStart(timeStart);
        request.setTimeEnd(timeEnd);
        requestRepository.save(request);
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

    public List<Request> findByStudentIdAndDate(Long studentId, LocalDate date)
    {
        return requestRepository.findByStudentIdAndDayDate(studentId, date);
    }

    public RequestStatus showStatusByStudentIdAndDate(Long studentId, LocalDate date)
    {
        List<Request> requests = requestRepository.findByStudentIdAndDayDate(studentId, date);
        if (requests.isEmpty())
        {
            throw new RequestNotFound("Request is not found");
        }
        return requests.get(0).getStatus();
    }

    public void changePresenceOfStudent(Long studentId, LocalDate date, Boolean hasCome)
    {
        RequestStatus oldStatus = showStatusByStudentIdAndDate(studentId, date);
        if (hasCome)
        {
           requestRepository.updateStatusByDate(studentId, date, requestStatusRepository.findByStatus("HAS_COME").get());
            //статусы есть: "не просмотрено" (по умолчанию); "просмотрено" (не выводить в списке заявок);
            //"пришел"; "не пришел" (уже после занятия, когда заполняется посещаемость)
            studentService.changeAttendedClasses(studentId, true); //при посещении надо изменить число посещенных занятий
        }
        else
        {
            requestRepository.updateStatusByDate(studentId, date, requestStatusRepository.findByStatus("HAS_NOT_COME").get());
            if (oldStatus.getStatus().equals("HAS_COME")) //если старый статус был "пришел", то он изменяется в отсутствие и надо уменьшить число занятий
            {
                studentService.changeAttendedClasses(studentId, false);
            }
        }
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
        Optional<RequestStatus> requestStatusOptional = requestStatusRepository.findByStatus(status);
        if (requestStatusOptional.isEmpty())
        {
            throw new RequestStatusNotFound("Request status is not found");
        }
        requestRepository.updateStatus(requestStatusOptional.get(), requestId);
    }

    public List<Request> findByStatus(String status)
    {
        return requestRepository.findByStatusStatus(status);
    }
    public  List<String> showInfoAboutSession(Long studentId, LocalDate date, LocalTime timeStart, LocalTime timeEnd)
    {
        int numberOfJuniors = 0; //блок вывода информации о посещаемости
        int numberOfMiddles = 0;
        int numberOfSeniors = 0;
        List<Request> requests = requestRepository.findByDayDateAndTimeStartAndTimeEnd(date, timeStart, timeEnd);
        for (Request request: requests)
        {
            switch (request.getStudent().getRank_name().getRank_name())
            {
                case "juniors" -> ++numberOfJuniors;
                case "middles" -> ++numberOfMiddles;
                default -> ++numberOfSeniors;
            }
        }
        int numberOfOccupiedShiels = numberOfJuniors + numberOfMiddles / 2 + numberOfMiddles % 2 + numberOfSeniors / 2 + numberOfSeniors % 2;
        List<String> info = new ArrayList<>();
        info.add(Integer.toString(numberOfJuniors));
        info.add(Integer.toString(numberOfMiddles));
        info.add(Integer.toString(numberOfSeniors));
        info.add(Integer.toString(numberOfOccupiedShiels));


        if (LocalDate.now().isAfter(date) || (LocalDate.now().isEqual(date) && (LocalTime.now().isAfter(timeStart))))
        {
            info.add("Редактирование заявки недоступно: занятие уже началось или прошло"); //проверка на актуальность времени
            return info;
        }

        if (existsByStudentIdAndTime(studentId, date, timeStart, timeEnd)) //проверка, записан(а) ли уже
        {
            info.add("Отменить заявку");
            return info;
        }

        LocalTime timeDuration; //проверка, можно ли еще записаться (связано с билетом и заявками на сегодня)
        try
        {
            SeasonTicket ticket = purchaseHistoryService.findActiveSeasonTicket(studentId, date);
            timeDuration = ticket.getTimeDuration();
        }
        catch (SeasonTicketNotFoundException e)
        {
            if (purchaseHistoryService.existByStudentId(studentId))
            {
                info.add("Нет активного абонемента на момент этой даты");
                return info;
            }
            timeDuration = lenghtOfMasterClass;
        }
        if (!timeDuration.equals(LocalTime.of(23, 59)))
        {
            int numberOfSessions = timeDuration.getHour() * 2;
            if (timeDuration.getMinute() >= 30)
            {
                ++numberOfSessions;
            }
            if (findByStudentIdAndDate(studentId, date).size() + 1 > numberOfSessions)
            {
                info.add("Число ваших заявок на сегодня достигло лимита");
                return info;
            }
        }

        Student student = studentService.findStudentById(studentId);
        switch (student.getRank_name().getRank_name())
        {
            case "juniors" -> ++numberOfJuniors;
            case "middles" -> ++numberOfMiddles;
            default -> ++numberOfSeniors;
        }
        numberOfOccupiedShiels = numberOfJuniors + numberOfMiddles / 2 + numberOfMiddles % 2 + numberOfSeniors / 2 + numberOfSeniors % 2;
        if (numberOfJuniors > wishedNumberOfJuniors || numberOfJuniors + numberOfMiddles > wishedNumberOfDemandingTrainer
                || numberOfOccupiedShiels > numberOfShields)
        {
            info.add("Нельзя записаться из-за превышенного числа учеников по рангу или по щитам");
            return info;
        }
        info.add("Записаться");
        return info;
    }











    public Boolean existsByStudentIdAndDate(Long studentId, LocalDate date)
    {
        return requestRepository.existsByStudentIdAndDayDate(studentId, date);
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
