import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.Application;
import spring.entity.Day;
import spring.repositories.DayRepository;
import spring.repositories.StudentRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=Application.class)
public class TestDaysServiceAndRep
{
    @Autowired
    DayRepository dayRepository;

    @Test
    void testFindFromTo()
    {
        List<Day> days = dayRepository.findFromTo(LocalDate.of(2030, 8, 8), LocalDate.of(2030, 9, 2));
        List<String> stringDays = days.stream().map(Day::getDate).map(LocalDate::toString).toList();
        List<String> waitedDays = Lists.newArrayList("2030-08-08", "2030-08-09", "2030-08-10", "2030-09-02");
        assertEquals(stringDays, waitedDays);
    }
}
