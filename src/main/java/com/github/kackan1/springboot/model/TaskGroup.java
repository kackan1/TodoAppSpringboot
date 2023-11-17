package com.github.kackan1.springboot.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "task_groups")
public class TaskGroup extends BaseTask {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Set<Task> tasks;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public TaskGroup() {
    }

    @Override
    public void setDescription(final String description) {
        super.setDescription(description);
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(final Set<Task> tasks) {
        this.tasks = tasks;
    }

    Project getProject() {
        return project;
    }

    void setProject(final Project project) {
        this.project = project;
    }
}
