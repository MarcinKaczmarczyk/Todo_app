package io.github.MarcinK.todoapp.adapter;

import io.github.MarcinK.todoapp.model.Task;
import io.github.MarcinK.todoapp.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {

    @Override
    @Query(nativeQuery = true, value = "select count(*) >0 from tasks where id=?1")
    boolean existsById(Integer id);


}
