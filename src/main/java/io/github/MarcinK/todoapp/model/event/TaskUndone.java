package io.github.MarcinK.todoapp.model.event;

import io.github.MarcinK.todoapp.model.Task;

import java.time.Clock;

public class TaskUndone extends TaskEvent{
    TaskUndone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
