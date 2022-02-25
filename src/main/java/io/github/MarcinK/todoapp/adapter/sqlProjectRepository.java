package io.github.MarcinK.todoapp.adapter;

import io.github.MarcinK.todoapp.model.Project;
import io.github.MarcinK.todoapp.model.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface sqlProjectRepository extends JpaRepository<Project,Integer>, ProjectRepository {
    @Override
    @Query("select distinct p from Project p join fetch p.steps")
    List<Project> findAll();

}
