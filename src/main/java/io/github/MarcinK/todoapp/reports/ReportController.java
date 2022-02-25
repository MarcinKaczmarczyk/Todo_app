package io.github.MarcinK.todoapp.reports;

import io.github.MarcinK.todoapp.model.Task;
import io.github.MarcinK.todoapp.model.TaskRepository;
import io.github.MarcinK.todoapp.model.event.TaskDone;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final TaskRepository taskRepository;
    private final PersistedTaskEventRepository eventRepository;

    public ReportController(TaskRepository taskRepository, PersistedTaskEventRepository eventRepository) {
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/count/{id}")
    ResponseEntity<TaskWithChangesCount> readTaskWithCount(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(task -> new TaskWithChangesCount(task, eventRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/before/{id}")
    ResponseEntity<IsDoneBeforeDeadline> readTaskDoneBeforeDeadline(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(task ->new IsDoneBeforeDeadline(task, eventRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private static class TaskWithChangesCount {
        public String description;
        public boolean done;
        public int changesCount;

        TaskWithChangesCount(final Task task, final List<PersistedTaskEvent> events) {
            description = task.getDescription();
            done = task.isDone();
            changesCount = events.size();
        }
    }

    private static class IsDoneBeforeDeadline {
        public String description;
        public LocalDateTime deadline;
        public PersistedTaskEvent lastChange;
        public boolean IsDoneBefore;

        IsDoneBeforeDeadline(final Task task, final List<PersistedTaskEvent> events) {
            description = task.getDescription();
            deadline = task.getDeadline();
            lastChange = events.stream()
                    .max(Comparator.comparing(event -> event.occurrence)).orElse(null);
            IsDoneBefore = checkTaskDoneBeforeDeadline();
        }

        public boolean checkTaskDoneBeforeDeadline() {
            if (lastChange == null) {
                return false;
            }
            if (lastChange.name.equals(TaskDone.class.getSimpleName())) {
                return deadline == null || lastChange.occurrence.isBefore(deadline);
            }
            return false;
        }
    }

}
