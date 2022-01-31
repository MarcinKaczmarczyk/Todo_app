package io.github.MarcinK.todoapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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




    Task() {
    }

    public Task(String description, LocalDateTime deadline){
        this.description=description;
        this.deadline=deadline;

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

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }


}