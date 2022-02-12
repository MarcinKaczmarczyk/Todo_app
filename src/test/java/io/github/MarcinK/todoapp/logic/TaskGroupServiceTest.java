package io.github.MarcinK.todoapp.logic;

import io.github.MarcinK.todoapp.model.TaskGroup;
import io.github.MarcinK.todoapp.model.TaskGroupRepository;
import io.github.MarcinK.todoapp.model.TaskRepository;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    void toggleGroupShouldThrowIllegalStateExceptionWhenExistsByDoneIsFalseReturnTrue() {
        //given
        TaskRepository taskRepo = taskRepositoryReturning(true);
        //system under test
        TaskGroupService toTest=new TaskGroupService(null,taskRepo);
        //when
        var exception= catchThrowable(()->toTest.toggleGroup(1));
        //then
        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("Group has undone tasks");
    }
    @Test
    void toggleGroupShouldThrowIllegalArgumentExceptionWhenOptionalIsEmpty(){
        //given
        TaskRepository taskRepo = taskRepositoryReturning(false);
        //and
        TaskGroupRepository taskGroupRepo=taskGroupRepositoryReturning(Optional.empty());
        //system under test
        TaskGroupService toTest=new TaskGroupService(taskGroupRepo,taskRepo);
        //when
        var exception= catchThrowable(()->toTest.toggleGroup(1));
        //then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("given id not found");
    }

    @Test
    void toggleGroupShouldChangeFlagIsDone(){
        //given
        TaskRepository taskRepo = taskRepositoryReturning(false);
        //and
        TaskGroup entityTest=new TaskGroup();
        //and
        boolean testingFlag=entityTest.isDone();
        TaskGroupRepository taskGroupRepo=taskGroupRepositoryReturning(Optional.of(entityTest));
        //system under test
        TaskGroupService toTest=new TaskGroupService(taskGroupRepo,taskRepo);
        //when
        toTest.toggleGroup(1);
        //then
        assertThat(testingFlag).isNotEqualTo(entityTest.isDone());
    }

    private TaskRepository taskRepositoryReturning(boolean param) {
        var taskRepo=mock(TaskRepository.class);
        when(taskRepo.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(param);
        return taskRepo;
    }

    private TaskGroupRepository taskGroupRepositoryReturning(Optional<TaskGroup> param) {
        var taskRepo=mock(TaskGroupRepository.class);
        when(taskRepo.findById(anyInt())).thenReturn(param);
        return taskRepo;
    }
}