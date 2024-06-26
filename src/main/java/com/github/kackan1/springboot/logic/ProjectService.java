package com.github.kackan1.springboot.logic;

import com.github.kackan1.springboot.TaskConfigurationProperties;
import com.github.kackan1.springboot.model.*;
import com.github.kackan1.springboot.model.projection.GroupReadModel;
import com.github.kackan1.springboot.model.projection.GroupTaskWriteModel;
import com.github.kackan1.springboot.model.projection.GroupWriteModel;
import com.github.kackan1.springboot.model.projection.ProjectWriteModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository taskGroupRepository;
    private TaskGroupService service;
    private TaskConfigurationProperties config;

    ProjectService(final ProjectRepository repository, final TaskGroupRepository groupRepository,
            final TaskConfigurationProperties config, final TaskGroupService service) {
        this.repository = repository;
        this.taskGroupRepository = groupRepository;
        this.service = service;
        this.config = config;
    }

    public List<Project> readAll() {
        return repository.findAll();
    }

    public Project save(final ProjectWriteModel project) {
        return repository.save(project.toProject());
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline) {
        if (!config.getTemplate().isAllowMultipleTasks()
                && taskGroupRepository.existsByDoneIsFalseAndProject_Id(projectId)) {
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        TaskGroup result = repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new TaskGroup();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> new Task(
                                            projectStep.getDescription(),
                                            deadline.plusDays(projectStep.getDaysToDeadline())))
                                    .collect(Collectors.toSet()));
                    targetGroup.setProject(project);
                    return taskGroupRepository.save(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return new GroupReadModel(result);
    }

}
