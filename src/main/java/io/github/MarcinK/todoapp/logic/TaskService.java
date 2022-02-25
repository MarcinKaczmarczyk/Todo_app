//package io.github.MarcinK.todoapp.logic;
//
//import io.github.MarcinK.todoapp.model.Task;
//import io.github.MarcinK.todoapp.model.TaskRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//@Service
//public class TaskService {
//    public static final Logger logger= LoggerFactory.getLogger(TaskService.class);
//    private final TaskRepository repository;
//
//    public TaskService(TaskRepository repository) {
//        this.repository = repository;
//    }
//    @Async
//    public CompletableFuture<List<Task>> findAllAsync(){
//        logger.info("supply async!");
//        return CompletableFuture.supplyAsync(repository::findAll);
//    }
//}
