package spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.entity.Request;
import spring.repositories.RequestRepository;

import java.util.Date;
import java.sql.Time;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    private RequestRepository requestRepository;

    public List<Request> findByStatus(String status)
    {
        return requestRepository.findByStatus(status);
    }

    public void updateStatus(String status, long requestId)
    {
        requestRepository.updateStatus(status, requestId);
    }

    public Boolean existsByStudentAndTime(Long studentId, Date date, Time timeStart, Time timeEnd)
    {
        return requestRepository.existsByStudentIdAndDateAndTimeStartAndTimeEnd(studentId, date, timeStart, timeEnd);
    }

    public Boolean existsByStudentAndDate(Long studentId, Date date)
    {
        return requestRepository.existsByStudentIdAndDate(studentId, date);
    }

    public void removeRequest(Long requestId)
    {
        requestRepository.removeByRequestId(requestId);
    }

    public void removeByDate(Date date)
    {
        requestRepository.removeByDate(date);
    }

    public void removeByStudentAndTime(Long studentId, Date date, Time timeStart, Time timeEnd)
    {
        requestRepository.removeByStudentIdAndDateAndTimeStartAndTimeEnd(studentId, date, timeStart, timeEnd);
    }

    @Autowired
    public void setRequestRepository(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }
}
