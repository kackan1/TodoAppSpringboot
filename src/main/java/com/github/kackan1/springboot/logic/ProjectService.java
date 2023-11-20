package com.github.kackan1.springboot.logic;

import com.github.kackan1.springboot.TaskConfigurationProperties;
import com.github.kackan1.springboot.model.*;
import com.github.kackan1.springboot.model.projection.GroupReadModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectService {
    private ProjectRepository repository;
    private TaskGroupRepository groupRepository;
    private TaskConfigurationProperties config;

    ProjectService(final ProjectRepository repository, final TaskGroupRepository groupRepository, final TaskConfigurationProperties config) {
        this.repository = repository;
        this.groupRepository = groupRepository;
        this.config = config;
    }

    public List<Project> readAll(){
        return repository.findAll();
    }

    public Project save(final Project project){
        return repository.save(project);
    }

    public GroupReadModel createGroup(int projectId, LocalDateTime deadline){
        if (!config.getTemplate().isAllowMultipleTasks() && groupRepository.existsByDoneIsFalseAndProject_Id(projectId)){
            throw new IllegalStateException("Only one undone group from project is allowed");
        }
        if (repository.findById(projectId).isEmpty()){
            throw new IllegalArgumentException("projectId points to non existing Project");
        }
        TaskGroup result = repository.findById(projectId)
                .map(project -> {
                    var targetGroup = new TaskGroup();
                    targetGroup.setDescription(project.getDescription());
                    targetGroup.setTasks(
                            project.getSteps().stream()
                                    .map(projectStep -> new Task(
                                            projectStep.getDescription(),
                                            deadline.plusDays(projectStep.getDaysToDeadline())
                                    )).collect(Collectors.toSet())
                    );
                    return targetGroup;
                }).orElseThrow(() -> new IllegalArgumentException("Project with given id not found"));
        return new GroupReadModel(result);
    }

}
