import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.Application;
import spring.entity.Request;
import spring.entity.RequestStatus;
import spring.entity.Student;
import spring.repositories.RequestRepository;
import spring.service.RequestService;
import spring.service.StudentService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static spring.Application.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=Application.class)
public class TestRequestServiceAndRep
{
    @Autowired
    RequestService requestService;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    StudentService studentService;


    @Test
    void testExistingByTimeAndStudent()
    {
        assertTrue(requestService.existsByStudentIdAndTime(30L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30)));
        assertFalse(requestService.existsByStudentIdAndTime(30L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 0), LocalTime.of(21, 0)));
    }

    @Test
    void testExistingByDateAndStudent()
    {
        assertTrue(requestService.existsByStudentIdAndDate(30L, LocalDate.of(2030, 8, 10)));
        assertFalse(requestService.existsByStudentIdAndDate(30L, LocalDate.of(2030, 8, 8)));
    }

    /*@Test
    void testRemovingByStudentAndTime()
    {
        assertTrue(requestService.existsByStudentIdAndTime(29L, LocalDate.of(2030, 8, 9), LocalTime.of(18, 30), LocalTime.of(20, 30)));
        requestService.removeByStudentIdAndTime(29L, LocalDate.of(2030, 8, 9), LocalTime.of(18, 30), LocalTime.of(20, 30));
        assertFalse(requestService.existsByStudentIdAndTime(29L, LocalDate.of(2030, 8, 9), LocalTime.of(18, 30), LocalTime.of(20, 30)));
    }*/

    @Test
    void testFindingOfIntersect()
    {
        Set<String> requestsTime = requestRepository.findIfIntersectByTime(LocalDate.of(2030, 8, 10),
                LocalTime.of(18, 30), LocalTime.of(20, 30)).stream().map(r ->
            r.getTimeStart().toString() + "-" + r.getTimeEnd().toString()
        ).collect(Collectors.toSet());
        Set<String> waitedRequestsTime = new HashSet<>(Arrays.asList("18:30-20:30", "18:00-20:00", "19:00-21:00", "18:00-21:00"));
        assertTrue(requestsTime.size() == 4 && requestsTime.equals(waitedRequestsTime));
    }

    @Test
    void testAddingOfRequest()
    {
        wishedNumberOfJuniors = 1;
        wishedNumberOfDemandingTrainer = 2;
        assertFalse(requestService.addRequest(32L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30)));
        assertFalse(requestService.addRequest(33L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30)));
        assertTrue(requestService.addRequest(37L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30)));
        requestService.removeByStudentIdAndTime(37L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30));

        wishedNumberOfJuniors = 1;
        assertFalse(requestService.addRequest(32L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30)));

        wishedNumberOfDemandingTrainer = 3;
        assertTrue(requestService.addRequest(33L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30)));
        requestService.removeByStudentIdAndTime(33L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30));
        wishedNumberOfJuniors = 5;
        wishedNumberOfDemandingTrainer = 7;

        numberOfShields = 3;
        assertFalse(requestService.addRequest(37L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30)));
        assertTrue(requestService.addRequest(33L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30)));
        requestService.removeByStudentIdAndTime(33L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30));

        numberOfShields = 4;
        assertTrue(requestService.addRequest(32L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30)));
        requestService.removeByStudentIdAndTime(32L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30));
        assertTrue(requestService.addRequest(37L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30)));
        requestService.removeByStudentIdAndTime(37L, LocalDate.of(2030, 8, 10), LocalTime.of(18, 30), LocalTime.of(20, 30));
    }

    @Test
    void testFindingStudentsByDate()
    {
        Set<Long> studentsId = requestService.findStudentsByDate(LocalDate.of(2030, 8, 10)).
                stream().map(Student::getId).collect(Collectors.toSet());
        Set<Long> waitedStudentsId = new HashSet<>(Arrays.asList(30L, 28L, 34L, 29L, 36L));
        assertTrue(studentsId.size() == 5 && studentsId.equals(waitedStudentsId));
    }

    @Test
    void testCheckingStatusByStudent()
    {
        RequestStatus requestStatus = requestService.showStatusByStudentIdAndDate(29L, LocalDate.of(2030, 8, 10));
        assertEquals("HAS_COME", requestStatus.getStatus());

        requestStatus = requestService.showStatusByStudentIdAndDate(34L, LocalDate.of(2030, 8, 10));
        assertEquals("HAS_NOT_COME", requestStatus.getStatus());

        requestStatus = requestService.showStatusByStudentIdAndDate(28L, LocalDate.of(2030, 8, 10));
        assertEquals("IS_VIEWED", requestStatus.getStatus());

        requestStatus = requestService.showStatusByStudentIdAndDate(29L, LocalDate.of(2030, 8, 11));
        assertEquals("IS_VIEWED", requestStatus.getStatus());
    }

    @Test
    void testChangingPresence()
    {
       requestService.changePresenceOfStudent(28L, LocalDate.of(2030, 8, 10), true);
       RequestStatus requestStatus = requestService.showStatusByStudentIdAndDate(28L, LocalDate.of(2030, 8, 10));
       assertEquals("HAS_COME", requestStatus.getStatus());
       assertEquals(studentService.findStudentById(28L).getAttended_classes(), 1);

       requestService.changePresenceOfStudent(28L, LocalDate.of(2030, 8, 10), false);
       assertEquals(studentService.findStudentById(28L).getAttended_classes(), 0);
       requestService.updateStatus("IS_VIEWED", 2L);

       requestService.changePresenceOfStudent(29L, LocalDate.of(2030, 8, 11), true);
       requestStatus = requestService.showStatusByStudentIdAndDate(29L, LocalDate.of(2030, 8, 11));
       assertEquals("HAS_COME", requestStatus.getStatus());
       requestService.changePresenceOfStudent(29L, LocalDate.of(2030, 8, 11), false);
       requestService.updateStatus("IS_VIEWED", 9L);
        requestService.updateStatus("IS_VIEWED", 10L);
    }

    @Test
    void testFindingByStatus()
    {
        Set<Long> requestsId = requestService.findByStatus("IS_VIEWED").
                stream().map(Request::getRequestId).collect(Collectors.toSet());
        Set<Long> waitedRequestsId = new HashSet<>(Arrays.asList(2L, 9L, 10L));
        assertTrue(requestsId.size() == 3 && requestsId.equals(waitedRequestsId));
    }
}
