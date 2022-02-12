package io.github.MarcinK.todoapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "project's description must not be empty")
    private String description;
    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> groups;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectStep> steps;

    public Project() {
    }

    public String getDescription() {
        return description;
    }

   public void setDescription(String description) {
        this.description = description;
    }

   public Set<TaskGroup> getGroups() {
        return groups;
    }

   public void setGroups(Set<TaskGroup> groups) {
        this.groups = groups;
    }

   public Set<ProjectStep> getSteps() {
        return steps;
    }

   public void setSteps(Set<ProjectStep> projectSteps) {
        this.steps = projectSteps;
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }
}
