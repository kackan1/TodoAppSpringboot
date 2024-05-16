package com.github.kackan1.springboot.reports;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.github.kackan1.springboot.model.event.TaskEvent;

@Entity
@Table(name = "task_events")
class PersistedTaskEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int taskId;
    String name;
    LocalDateTime occurence;

    public PersistedTaskEvent() {
    }

    public PersistedTaskEvent(TaskEvent source) {
        this.taskId = source.getTaskId();
        this.name = source.getClass().getSimpleName();
        this.occurence = LocalDateTime.ofInstant(source.getOccurrence(), ZoneId.systemDefault());
    }
}
