package io.github.MarcinK.todoapp.model;

import io.github.MarcinK.todoapp.model.event.TaskEvent;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "task's description must not be empty")
    private String description;
    private boolean done;
    private LocalDateTime deadline;
    @Embedded
    private Audit audit= new Audit();
    @ManyToOne()
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;




   public Task() {
    }

    public Task(String description, LocalDateTime deadline){
       this(description,deadline,null);
    }
    public Task(String description, LocalDateTime deadline, TaskGroup group){
        this.description=description;
        this.deadline=deadline;
        if (group!=null){
            this.group=group;
        }

    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public void updatedFrom(final Task source){
        description= source.description;
        done= source.done;
        deadline= source.deadline;
        group=source.group;
    }

    Audit getAudit() {
        return audit;
    }

    void setAudit(Audit audit) {
        this.audit = audit;
    }

    TaskGroup getGroup() {
        return group;
    }

    void setGroup(TaskGroup group) {
        this.group = group;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public TaskEvent toggle(){
       this.done=!this.done;
       return TaskEvent.changed(this);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && done == task.done && Objects.equals(description, task.description) && Objects.equals(deadline, task.deadline) && Objects.equals(audit, task.audit) && Objects.equals(group, task.group);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, done, deadline, audit, group);
    }
}
