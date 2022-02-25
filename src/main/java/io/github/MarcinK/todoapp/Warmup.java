package io.github.MarcinK.todoapp;

import io.github.MarcinK.todoapp.model.Task;
import io.github.MarcinK.todoapp.model.TaskGroup;
import io.github.MarcinK.todoapp.model.TaskGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    public static final Logger logger= LoggerFactory.getLogger(Warmup.class);

    private final TaskGroupRepository groupRepository;

    Warmup(TaskGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application warmup after context refreshed");
        final String description="ApplicationContextEvent";
        if(!groupRepository.existsByDescription(description)){
            logger.info("no required group found! adding it!");
            var group=new TaskGroup();
            group.setDescription(description);
            group.setTasks(Set.of(
                new Task("ContextClosedEvent",null, group),
                new Task("ContextRefreshedEvent",null, group),
                new Task("ContextStoppedEvent",null, group),
                new Task("ContextStartedEvent",null, group)
            ));
            groupRepository.save(group);
        }
    }
}
