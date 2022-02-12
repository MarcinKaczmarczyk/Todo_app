package io.github.MarcinK.todoapp.adapter;

import io.github.MarcinK.todoapp.model.TaskGroup;
import io.github.MarcinK.todoapp.model.TaskGroupRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlTaskGroupRepository extends JpaRepository<TaskGroup,Integer>, TaskGroupRepository {

    @Override
    @Query("select distinct g from TaskGroup g join fetch g.tasks")
    List<TaskGroup> findAll();





}
