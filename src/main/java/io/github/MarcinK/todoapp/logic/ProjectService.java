package io.github.MarcinK.todoapp.logic;

import io.github.MarcinK.todoapp.TaskConfigurationProperties;
import io.github.MarcinK.todoapp.model.*;
import io.github.MarcinK.todoapp.model.projection.GroupReadModel;
import io.github.MarcinK.todoapp.model.projection.GroupTaskWriteModel;
import io.github.MarcinK.todoapp.model.projection.GroupWriteModel;
import io.github.MarcinK.todoapp.model.projection.ProjectWriteModel;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {
    private ProjectRepository projectRepository;
    private TaskConfigurationProperties config;
    private TaskGroupRepository groupRepository;
    private TaskGroupService taskGroupService;


    public ProjectService(ProjectRepository projectRepository, TaskConfigurationProperties config, TaskGroupRepository groupRepository, TaskGroupService taskGroupService) {
        this.projectRepository = projectRepository;
        this.config = config;
        this.groupRepository = groupRepository;
        this.taskGroupService = taskGroupService;
    }

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public Project save(final ProjectWriteModel toSave) {
        return projectRepository.save(toSave.toProject());
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
        if (!config.getTemplate().isAllowMultipleTasks() && groupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        return projectRepository.findById(projectId)
                .map(project -> {
                    var targetGroup = new GroupWriteModel();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> {
                                                var task = new GroupTaskWriteModel();
                                                task.setDescription(projectStep.getDescription());
                                                task.setDeadline(deadline.plusDays(projectStep.getDaysToDeadline()));
                                                return task;
                                            }
                                    ).collect(Collectors.toList())
                    );
                   return taskGroupService.createGroup(targetGroup,project);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
    }

}
