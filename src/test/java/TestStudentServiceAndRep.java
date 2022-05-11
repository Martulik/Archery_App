import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.Application;
import spring.repositories.StudentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=Application.class)
public class TestStudentServiceAndRep
{
    @Autowired
    StudentRepository studentRepository;

    @Test
    void testDecreasingClasses()
    {
        studentRepository.decreaseAttendedClasses(28);
        assertEquals(studentRepository.findById(28L).get().getAttended_classes(), 0);
        studentRepository.decreaseAttendedClasses(31);
        assertEquals(studentRepository.findById(31L).get().getAttended_classes(), 29);
        studentRepository.increaseAttendedClasses(31);
    }
}
