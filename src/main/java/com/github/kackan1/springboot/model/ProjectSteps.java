package com.github.kackan1.springboot.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "project_steps")
public class ProjectSteps extends BaseProject{

    private LocalDateTime daysToDeadline;

    public LocalDateTime getDaysToDeadline() {
        return daysToDeadline;
    }

    void setDaysToDeadline(final LocalDateTime daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }
}
