package spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.entity.Day;
import spring.entity.SeasonTicket;
import spring.entity.Student;
import spring.service.DayService;
import spring.service.PurchaseHistoryService;
import spring.service.RequestService;
import spring.service.StudentService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/archery/admin/timetable")
public class AdminTimetableController
{
    /*@Autowired
    private DayService dayService;
    @Autowired
    private PurchaseHistoryService purchaseHistoryService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/day")
    public ResponseEntity<String> showDay(@RequestParam LocalDate date)
    {
        Day day = dayService.findByDate(date);
        if (!day.getAreLessons())
        {
            return new ResponseEntity<>("Занятий нет", HttpStatus.OK);
        }
        LocalTime lesson = LocalTime.of(0, 30);
        List<String> lessons = new ArrayList<>();
        LocalTime localStart = day.getTimeStart();
        LocalTime localEnd = day.getTimeStart();
        while (localEnd.isBefore(day.getTimeEnd()))
        {
            localEnd = localEnd.plusMinutes(lesson.getMinute());
            if (localEnd.isAfter(day.getTimeEnd()))
            {
                localEnd = day.getTimeEnd();
            }
            lessons.add(localStart.toString() + "-" + localEnd.toString() + "\n");
            localStart = localStart.plusMinutes(lesson.getMinute());
        }
        List<Student> students = requestService.findStudentsByDate(date);
        List<String> stringStudents = new ArrayList<>();
        for (Student student: students)
        {
            stringStudents.add(student.getFirst_name() + " " + student.getLast_name() + "\n");
        }
        return new ResponseEntity<>(lessons + "\n" + stringStudents, HttpStatus.OK); //пока так, но должны быть ссылки
    }

    @GetMapping("day/{id}")
    public ResponseEntity<String> showPresenceOfStudent(@RequestParam LocalDate date, @RequestParam Long id)
    {
        String action = "";
        if (!requestService.showStatusByStudentIdAndDate(id, date).getStatus().equals("HAS_COME"))
        {
            action += "Пришел/пришла ";
        }
        if (!requestService.showStatusByStudentIdAndDate(id, date).getStatus().equals("HAS_NOT_COME"))
        {
            action += "НЕ пришел/НЕ пришла";
        }
        return new ResponseEntity<>(action, HttpStatus.OK);
    }

    @PostMapping("/day/{id}")
    public void changePresenceOfStudent(@RequestParam LocalDate date, @RequestParam Long id, @RequestParam boolean hasCome)
    {
        requestService.changePresenceOfStudent(id, date, hasCome);
    }

    @GetMapping("/day/lesson")
    public ResponseEntity<List<Student>> showLesson(@RequestParam LocalDate date, @RequestParam LocalTime timeStart, @RequestParam LocalTime timeEnd)
    {
        return new ResponseEntity<>(requestService.findStudentsByTime(date, timeStart, timeEnd), HttpStatus.OK);
    }*/
}
