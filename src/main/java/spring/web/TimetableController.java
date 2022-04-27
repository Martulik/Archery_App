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

import java.sql.Time;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/archery/{id}/timetable")
public class TimetableController
{
    @Autowired
    private DayService dayService;
    @Autowired
    private PurchaseHistoryService purchaseHistoryService;

    @GetMapping("/day")
    public ResponseEntity<String> findDay(@RequestBody Date date, @PathVariable Long id)
    {
        //проверка, что не раньше текущего дня
        if (!dayService.areLessons(date))
        {
            return new ResponseEntity<>("Занятий нет", HttpStatus.OK);
        }
        try
        {
            Time time = purchaseHistoryService.findActiveSeasonTicket(id, date).getTimeDuration();
            //if (time.)не закончено
            return new ResponseEntity<>( "fsdfsdf", HttpStatus.OK);
        }
        catch (SeasonTicketNotFoundException e)
        {
            return new ResponseEntity<>("Занятий нет", HttpStatus.OK);
        }
    }
}
