package com.github.kackan1.springboot.logic;

import com.github.kackan1.springboot.model.TaskGroup;
import com.github.kackan1.springboot.model.TaskGroupRepository;
import com.github.kackan1.springboot.model.TaskRepository;
import com.github.kackan1.springboot.model.projection.GroupReadModel;
import com.github.kackan1.springboot.model.projection.GroupWriteModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskGroupService {
    private TaskGroupRepository repository;
    private TaskRepository taskRepository;

    TaskGroupService(final TaskGroupRepository repository, final TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    public GroupReadModel createGroup(GroupWriteModel source){
        TaskGroup result = repository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll(){
        return repository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

    public void toggleGroup(int groupId){
        if (taskRepository.existsByDoneIsFalseAndGroup_Id(groupId)){
            throw new IllegalStateException("Group has undone tasks. Do all the tasks first");
        }
        TaskGroup result = repository.findById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("TaskGroup with given id not found"));
        result.setDone(!result.isDone());
    }

}

