package com.github.kackan1.springboot.logic;

import com.github.kackan1.springboot.model.Task;
import com.github.kackan1.springboot.model.TaskGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TempService {
    @Autowired
    void temp(TaskGroupRepository repository){
        repository.findAll().stream()
                .flatMap(taskGroup -> taskGroup.getTasks().stream())
                .map(Task::getDescription)
                .collect(Collectors.toList());
    }
}

