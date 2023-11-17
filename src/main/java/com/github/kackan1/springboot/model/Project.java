package com.github.kackan1.springboot.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "projects")
public class Project extends BaseProject{

    @OneToMany(mappedBy = "project")
    private Set<TaskGroup> groups;
    @OneToMany(cascade = CascadeType.ALL, mappedBy= "project")
    private Set<ProjectStep> steps;

    Set<TaskGroup> getGroups() {
        return groups;
    }

    void setGroups(final Set<TaskGroup> groups) {
        this.groups = groups;
    }

    public Set<ProjectStep> getSteps() {
        return steps;
    }

    void setSteps(final Set<ProjectStep> steps) {
        this.steps = steps;
    }
}
