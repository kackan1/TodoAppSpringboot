package com.github.kackan1.springboot.reports;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PeristedTaskEventRepository extends JpaRepository<PersistedTaskEvent, Integer> {
    List<PersistedTaskEvent> findByTaskId(int taskId);
}
