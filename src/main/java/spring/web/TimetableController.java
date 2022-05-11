package spring.web;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.entity.Day;
import spring.entity.Request;
import spring.entity.SeasonTicket;
import spring.entity.Student;
import spring.exception.DayNotFoundException;
import spring.exception.SeasonTicketNotFoundException;
import spring.repositories.RequestRepository;
import spring.service.DayService;
import spring.service.PurchaseHistoryService;
import spring.service.RequestService;
import spring.service.StudentService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static spring.Application.*;

@RestController
@RequestMapping("/archery/timetable/{id}")
@RequiredArgsConstructor
public class TimetableController
{
    private final DayService dayService;
    private final PurchaseHistoryService purchaseHistoryService;
    private final RequestService requestService;
    private final StudentService studentService;
    private final RequestRepository requestRepository;

    @GetMapping("")
    public ResponseEntity<List<LocalDate>> showTimetable()
    {
        return new ResponseEntity<>(dayService.showMonth(), HttpStatus.OK);
    }

    @GetMapping("/day")
    public ResponseEntity<Boolean> showDay(@PathVariable Long id, @RequestParam LocalDate date)
    {
        try {
            Day day = dayService.findByDate(date);
            if (!day.getAreLessons()) {
                return new ResponseEntity<>(false, HttpStatus.OK);
            }
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch (DayNotFoundException e)
        {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }

    @GetMapping("/day/lesson")
    public ResponseEntity<List<String>> showLesson(@PathVariable Long id, @RequestParam LocalDate date, @RequestParam LocalTime timeStart)
    {
        return new ResponseEntity<>(requestService.showInfoAboutSession(id, date, timeStart), HttpStatus.OK);
    }

    @PostMapping("/day/lesson/signup")
    public ResponseEntity<String> signUpLesson(@PathVariable Long id, @RequestParam LocalDate date, @RequestParam LocalTime timeStart, @RequestParam LocalTime timeEnd)
    {
        requestService.addRequest(id, date, timeStart, timeEnd);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/day/lesson/remove")
    public ResponseEntity<String> removeRequest(@PathVariable Long id, @RequestParam LocalDate date, @RequestParam LocalTime timeStart, @RequestParam LocalTime timeEnd)
    {
        requestService.removeByStudentIdAndTime(id, date, timeStart, timeEnd);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
