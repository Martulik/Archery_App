package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.Day;
import spring.entity.Rank;
import spring.entity.Request;
import spring.entity.Student;
import spring.repositories.RequestRepository;

import java.time.LocalTime;
import java.util.Date;
import java.sql.Time;
import java.util.List;
import java.util.Objects;

@Service
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;
    private StudentService studentService;

    public List<Request> findByStatus(String status)
    {
        return requestRepository.findByStatusStatus(status);
    }

    public void updateStatus(String status, long requestId)
    {
        requestRepository.updateStatus(status, requestId);
    }

    public Boolean existsByStudentAndTime(Long studentId, Date date, LocalTime timeStart, LocalTime timeEnd)
    {
        return requestRepository.existsByStudentIdAndDayDateAndTimeStartAndTimeEnd(studentId, date, timeStart, timeEnd);
    }

    public Boolean existsByStudentAndDate(Long studentId, Date date)
    {
        return requestRepository.existsByStudentIdAndDayDate(studentId, date);
    }

    public void removeRequest(Long requestId)
    {
        requestRepository.removeByRequestId(requestId);
    }

    public void removeByDate(Date date)
    {
        requestRepository.removeByDayDate(date);
    }

    public void removeByStudentAndTime(Long studentId, Date date, LocalTime timeStart, LocalTime timeEnd)
    {
        requestRepository.removeByStudentIdAndDayDateAndTimeStartAndTimeEnd(studentId, date, timeStart, timeEnd);
    }

    public void removeActiveRequestsByStudent(Long studentId, Date date, LocalTime time)
    {
        requestRepository.removeActiveRequestsByStudentId(studentId, date, time);
    }

    public void changeIfHasCome(Long studentId, Date date, Boolean hasCome)
    {
        //через репозиторий обновить статус всех заявок в этот день на "пришел" или "не пришел" (зависит от hasCome)
        if (hasCome)
        {
            studentService.changeAttendedClasses(studentId, true);
        }
    }

    public Boolean addRequest(Student student, Day day, LocalTime timeStart, LocalTime timeEnd)
    {
        final int NUMBER_OF_JUNIORS = 5; //должны быть глобальными, где хранить?
        final int NUMBER_OF_REQUIRING_TRAINER = 7;
        final int NUMBER_OF_SHILDS = 12;

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
            if (numberOfJuniors > NUMBER_OF_JUNIORS)
            {
                return false;
            }
            if (numberOfJuniors + numberOfMiddles > NUMBER_OF_REQUIRING_TRAINER)
            {
                return false;
            }
            if (numberOfJuniors + numberOfMiddles / 2 + numberOfMiddles % 2 + numberOfSeniors / 2 + numberOfSeniors % 2 > NUMBER_OF_SHILDS)
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

    @Autowired
    public void setRequestRepository(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
    @Autowired
    public void setStudentService(StudentService studentService)
    {
        this.studentService = studentService;
    }
}
