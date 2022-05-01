package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.entity.Day;
import spring.entity.SeasonTicket;
import spring.exception.SeasonTicketNotFoundException;
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

import static spring.Application.lenghtOfMasterClass;

@RestController
@RequestMapping("/archery/{id}/timetable")
public class TimetableController
{
    @Autowired
    private DayService dayService;
    @Autowired
    private PurchaseHistoryService purchaseHistoryService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private StudentService studentService;

    @GetMapping("")
    public ResponseEntity<List<Day>> showTimetable()
    {
        LocalDate currentDate = LocalDate.now();
        int currentDayOfWeek = currentDate.getDayOfWeek().getValue();
        LocalDate startOfTimetable = currentDate.minusDays(currentDayOfWeek + 7);
        LocalDate endOfTimetable = currentDate.plusDays(7 - currentDayOfWeek + 21);
        return new ResponseEntity<>(dayService.findFromTo(startOfTimetable, endOfTimetable), HttpStatus.OK);
    }

    @GetMapping("/day")
    public ResponseEntity<String> showDay(@PathVariable Long id, @RequestBody LocalDate date)
    {
        Day day = dayService.findByDate(date);
        if (day.getDate().isBefore(LocalDate.now()))
        {
            return new ResponseEntity<>("День уже прошел", HttpStatus.OK);
        }
        if (!day.getAreLessons())
        {
            return new ResponseEntity<>("Занятий нет", HttpStatus.OK);
        }
        LocalTime lesson;
        try
        {
            lesson = purchaseHistoryService.findActiveSeasonTicket(id, date).getTimeDuration();
            if (lesson == LocalTime.MAX)
            {
                lesson = LocalTime.of(0, 30);
            }
        }
        catch (SeasonTicketNotFoundException e)
        {
            if (purchaseHistoryService.existByStudentId(id))
            {
                return new ResponseEntity<>("Нет активного абонемента. Продлите для записи", HttpStatus.OK);
            }
            lesson = lenghtOfMasterClass;
        }
        List<String> lessons = new ArrayList<>();
        LocalTime localStart = day.getTimeStart();
        LocalTime localEnd = day.getTimeStart().plusMinutes(lesson.getMinute());
        while (localEnd.isBefore(day.getTimeEnd()))
        {
            lessons.add(localStart + "-" + localEnd);
            localStart = localStart.plusMinutes(lesson.getMinute());
            localEnd = localEnd.plusMinutes(lesson.getMinute());
        }
        return new ResponseEntity<>(lessons.toString(), HttpStatus.OK); //пока так, но должен быть список ссылок на каждый урок
    }

    @GetMapping("/day/lesson")
    public ResponseEntity<String> showLesson(@PathVariable Long id, @RequestBody LocalDate date, @RequestBody LocalTime timeStart, @RequestBody LocalTime timeEnd)
    {
        if (timeStart.isBefore(LocalTime.now()) && date.isBefore(LocalDate.now()))
        {
            return new ResponseEntity<>("Занятие уже началось или прошло", HttpStatus.OK);
        }
        if (requestService.existsByStudentIdAndTime(id, date, timeStart, timeEnd))
        {
            return new ResponseEntity<>("Отменить заявку", HttpStatus.OK);
        }
        SeasonTicket ticket = purchaseHistoryService.findActiveSeasonTicket(id, date);
        if (ticket.getTimeDuration() == LocalTime.MAX || !requestService.existsByStudentIdAndDate(id, date))
        {
            return new ResponseEntity<>("Записаться", HttpStatus.OK);
        }
        return new ResponseEntity<>("Вы уже записались на другое время", HttpStatus.OK);
    }

    @PostMapping("/day/lesson/signup")
    public ResponseEntity<String> signUpLesson(@PathVariable Long id, @RequestBody LocalDate date, @RequestBody LocalTime timeStart, @RequestBody LocalTime timeEnd)
    {
        if (requestService.addRequest(id, date, timeStart, timeEnd))
        {
            return new ResponseEntity<>("Запись успешна", HttpStatus.OK);
        }
        return new ResponseEntity<>("Нельзя записаться из-за числа учеников", HttpStatus.OK);
    }

    @PostMapping("/day/lesson/remove")
    public ResponseEntity<String> removeRequest(@PathVariable Long id, @RequestBody LocalDate date, @RequestBody LocalTime timeStart, @RequestBody LocalTime timeEnd)
    {
        requestService.removeByStudentIdAndTime(id, date, timeStart, timeEnd);
        return new ResponseEntity<>("Удаление заявки успешно", HttpStatus.OK);
    }
}
