package com.github.kackan1.springboot.adapter;

import com.github.kackan1.springboot.model.Project;
import com.github.kackan1.springboot.model.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SqlProjectRepository extends ProjectRepository, JpaRepository<Project, Integer> {
    @Override
    @Query("SELECT DISTINCT p from Project p join fetch p.steps")
    List<Project> findAll();
}
