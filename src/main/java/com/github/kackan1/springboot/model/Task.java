package com.github.kackan1.springboot.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task extends BaseTask {

    private LocalDateTime deadline;
    @ManyToOne
    @JoinColumn(name = "task_group_id")
    private TaskGroup group;

    Task() {
    }

    public Task(String description, LocalDateTime deadline) {
        this.setDescription(description);
        this.deadline = deadline;
    }

    public Task(String description, LocalDateTime deadline, TaskGroup group) {
        this.setDescription(description);
        this.setDeadline(deadline);
        if (group != null) {
            this.setGroup(group);
        }
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

    TaskGroup getGroup() {
        return group;
    }

    void setGroup(final TaskGroup group) {
        this.group = group;
    }

    public void updateFrom(final Task source) {
        this.setDescription(source.getDescription());
        this.setDone(source.isDone());
        deadline = source.deadline;
        group = source.group;
    }

}
