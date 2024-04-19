package com.github.kackan1.springboot.adapter;

import com.github.kackan1.springboot.model.Task;
import com.github.kackan1.springboot.model.TaskRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface SqlTaskRepository extends TaskRepository, JpaRepository<Task, Integer> {
    @Override
    @Query(nativeQuery = true, value = "SELECT count(*) > 0 FROM tasks WHERE id=:id")
    boolean existsById(@Param("id") Integer id);

    @Override
    @Query(nativeQuery = true, value = "SELECT * FROM tasks WHERE task_group_id=:groupid")
    List<Task> findAllByGroup_Id(Integer groupid);

    @Override
    boolean existsByDoneIsFalseAndGroup_Id(Integer groupid);
}
