package io.github.MarcinK.todoapp.controller;

import io.github.MarcinK.todoapp.logic.TaskGroupService;
import io.github.MarcinK.todoapp.model.Task;
import io.github.MarcinK.todoapp.model.TaskRepository;
import io.github.MarcinK.todoapp.model.projection.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@IllegalExceptionProcessing
@Controller
@RequestMapping("/groups")
public class TaskGroupController {
    private static final Logger logger = LoggerFactory.getLogger(TaskGroupController.class);
    private final TaskGroupService service;
    private final TaskRepository taskRepository;

     TaskGroupController(final TaskGroupService service,final TaskRepository taskRepository) {
        this.service = service;
         this.taskRepository = taskRepository;
     }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model){
        model.addAttribute("group",new GroupWriteModel());

        return "groups";
    }
    @PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    String addGroup(
            @ModelAttribute("group") @Valid GroupWriteModel current,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "groups";
        }
        service.createGroup(current);
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", getGroups());
        model.addAttribute("message", "Dodano grupę!");
        return "groups";
    }

    @PostMapping(params = "addTask", produces = MediaType.TEXT_HTML_VALUE)
    String addGroupTask(@ModelAttribute("group") GroupWriteModel current) {
        current.getTasks().add(new GroupTaskWriteModel());
        return "groups";
    }

    @ResponseBody
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate) {
        logger.info("Creating Group");
        GroupReadModel result=service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/"+result.getId())).body(result);
    }
    @ResponseBody
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GroupReadModel>> readAllTasksGroup() {
        logger.info("readingAllTasks");
        return ResponseEntity.ok(service.readAll());
    }
    @ResponseBody
    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Task>> readAllTasksForGroup(@PathVariable int id) {
//        if (!service.isExists(id)){
//            logger.warn("tasksGroup with id " +id+ "does not exist");
//            return ResponseEntity.notFound().build();
//        }
//        logger.info("Read all tasks for id"+id);
        return ResponseEntity.ok(taskRepository.findAllByGroup_Id(id));
    }
    @ResponseBody
    @Transactional
    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> toggle(@PathVariable int id){
        if (!service.isExists(id)){
            logger.warn("tasksGroup with id " +id+ "does not exist");
            return ResponseEntity.notFound().build();
        }
        logger.info("toggle group with id "+ id);
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ModelAttribute("groups")
    List<GroupReadModel>getGroups(){
        return service.readAll();
    }

}
