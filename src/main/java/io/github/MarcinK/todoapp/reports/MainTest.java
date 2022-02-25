package io.github.MarcinK.todoapp.reports;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;

public class MainTest {
    public static void main(String[] args) {
        List<LocalDateTime>x= Arrays.asList(LocalDateTime.of(2018,12,12,12,12),LocalDateTime.of(2021,12,15,12,12),LocalDateTime.of(2022,12,12,12,12)
        ,LocalDateTime.of(2017,12,12,12,12),LocalDateTime.of(2021,12,12,12,12),LocalDateTime.of(2021,3,12,12,12)
        ,LocalDateTime.of(2017,9,12,12,12),LocalDateTime.of(2018,9,12,12,12),LocalDateTime.of(2021,10,12,12,12));
        IsDoneBeforeDeadline done=new IsDoneBeforeDeadline(LocalDateTime.now(),x);

        System.out.println(done.deadline);
        System.out.println(done.lastChange);
    }
//    public static List<LocalDateTime> sortDate(final List<LocalDateTime> date) {
//
//       List<LocalDateTime>y= new ArrayList<>(date);
//               Collections.sort(sortDate(y));
//        return y;
//    }
    private static class IsDoneBeforeDeadline{
        public LocalDateTime deadline;
        public LocalDateTime lastChange;

        IsDoneBeforeDeadline(final LocalDateTime task, final List<LocalDateTime> events) {
            deadline = task;
//            Collections.sort(events);
            lastChange=events.stream().max(Comparator.comparing(event->event)).orElse(null);
        }

    }
}
