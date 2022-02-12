package io.github.MarcinK.todoapp.controller;

import io.github.MarcinK.todoapp.logic.TaskGroupService;
import io.github.MarcinK.todoapp.model.projection.GroupReadModel;
import io.github.MarcinK.todoapp.model.projection.GroupTaskReadModel;
import io.github.MarcinK.todoapp.model.projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupService.class);
    private final TaskGroupService service;

    public TaskGroupController(TaskGroupService service) {
        this.service = service;
    }

    @PostMapping()
    public ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
        logger.info("Creating Group");
        GroupReadModel result=service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }

    @GetMapping()
    public ResponseEntity<List<GroupReadModel>> readAllTasksGroup() {
        logger.info("readingAllTasks");
        return ResponseEntity.ok(service.readAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readAllTasksForGroup(@PathVariable int id) {
        if (!service.isExists(id)){
            logger.warn("tasksGroup with id " +id+ "does not exist");
            return ResponseEntity.notFound().build();
        }
        logger.info("Read all tasks for id"+id);
        return ResponseEntity.ok(service.readTasksForGroup(id));
    }
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggle(@PathVariable int id){
        if (!service.isExists(id)){
            logger.warn("tasksGroup with id " +id+ "does not exist");
            return ResponseEntity.notFound().build();
        }
        logger.info("toggle group with id "+ id);
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?>handleIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String>handleIllegalState(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
