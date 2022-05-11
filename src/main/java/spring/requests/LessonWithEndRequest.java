package spring.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LessonWithEndRequest {
    private String date;
    private String timeStart;
    private String timeEnd;
}
