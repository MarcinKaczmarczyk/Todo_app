package io.github.MarcinK.todoapp.logic;

import io.github.MarcinK.todoapp.TaskConfigurationProperties;
import io.github.MarcinK.todoapp.model.ProjectRepository;
import io.github.MarcinK.todoapp.model.TaskGroupRepository;
import io.github.MarcinK.todoapp.model.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LogicConfiguration {

    @Bean
    ProjectService projectService(
            final ProjectRepository projectRepo,
            final TaskConfigurationProperties config,
            final TaskGroupRepository taskGroupRepo,
            final TaskGroupService taskGroupService) {
        return new ProjectService(projectRepo, config, taskGroupRepo, taskGroupService);
    }

    @Bean
    TaskGroupService taskGroupService(final TaskGroupRepository taskGroupRepo, final TaskRepository taskRepo) {
        return new TaskGroupService(taskGroupRepo, taskRepo);
    }
}
