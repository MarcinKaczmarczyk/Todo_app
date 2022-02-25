package io.github.MarcinK.todoapp.logic;


import io.github.MarcinK.todoapp.model.Project;
import io.github.MarcinK.todoapp.model.TaskGroup;
import io.github.MarcinK.todoapp.model.TaskGroupRepository;
import io.github.MarcinK.todoapp.model.TaskRepository;
import io.github.MarcinK.todoapp.model.projection.GroupReadModel;
import io.github.MarcinK.todoapp.model.projection.GroupTaskReadModel;
import io.github.MarcinK.todoapp.model.projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;


public class TaskGroupService {
    private TaskGroupRepository taskGroupRepository;
    private TaskRepository taskRepository;


    TaskGroupService(final TaskGroupRepository repository, TaskRepository taskRepository){
       this.taskGroupRepository=repository;
        this.taskRepository = taskRepository;
    }
    public GroupReadModel createGroup(GroupWriteModel source){
       return createGroup(source,null);
    }
    GroupReadModel createGroup(final GroupWriteModel source,final Project project) {
        TaskGroup result=taskGroupRepository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }
    public List<GroupReadModel> readAll(){
        return taskGroupRepository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }
    public void toggleGroup(int groupId){
        if(taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
            throw new IllegalStateException("Group has undone tasks. Done all the tasks first");
        }
       TaskGroup result = taskGroupRepository.findById(groupId)
                .orElseThrow(()->new IllegalArgumentException("Task group with given id not found"));
        result.setDone(!result.isDone());
        taskGroupRepository.save(result);
    }
//    public List<GroupTaskReadModel> readTasksForGroup(Integer id){
//        return taskRepository.findAllByGroup_Id(id).stream()
//                .map(GroupTaskReadModel::new)
//                .collect(Collectors.toList());
//    }
    public boolean isExists(int id){
        return taskGroupRepository.existsById(id);
    }


}
