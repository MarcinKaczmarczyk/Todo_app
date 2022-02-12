package io.github.MarcinK.todoapp.controller;

import io.github.MarcinK.todoapp.model.Task;
import io.github.MarcinK.todoapp.model.TaskRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository repo;

    @Test
    void httpGet_returnsAllTasks() {
        //given
        int initial=repo.findAll().size();
        repo.save(new Task("foo", LocalDateTime.now()));
        repo.save(new Task("bar", LocalDateTime.now()));

        //when
        Task[] result = restTemplate.getForObject("http://localhost:" + port + "/tasks", Task[].class);

        //then
        assertThat(result).hasSize(initial+2);
    }
    @Test
    void httpPost_createTask(){
        //given
        var task=new Task("foo2",LocalDateTime.now());
        int afterPost=repo.findAll().size();

        //when
        Task result=restTemplate.postForObject("http://localhost:" + port + "/tasks",task,Task.class);

        //then
        assertThat(afterPost+1).isEqualTo(repo.findAll().size());
    }

}