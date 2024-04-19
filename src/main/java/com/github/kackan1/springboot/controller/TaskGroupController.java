package com.github.kackan1.springboot.controller;

import com.github.kackan1.springboot.logic.TaskGroupService;
import com.github.kackan1.springboot.model.Task;
import com.github.kackan1.springboot.model.TaskGroup;
import com.github.kackan1.springboot.model.TaskRepository;
import com.github.kackan1.springboot.model.projection.GroupReadModel;
import com.github.kackan1.springboot.model.projection.GroupWriteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/groups")
public class TaskGroupController {
    private final TaskGroupService service;
    private final TaskRepository taskRepository;

    public TaskGroupController(TaskGroupService service, TaskRepository taskRepository) {
        this.service = service;
        this.taskRepository = taskRepository;
    }

    @PostMapping
    public ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toCreate){
        GroupReadModel result = service.createGroup(toCreate);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping
    public ResponseEntity<List<GroupReadModel>> readAllGroups(){
        return ResponseEntity.ok(service.readAll());
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> readAllTasksFromGroup(@PathVariable int id){
        return ResponseEntity.ok().body(taskRepository.findAllByGroup_Id(id));
    }

    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<?> toggleGroup(@PathVariable int id){
        service.toggleGroup(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<String> handleIllegalState(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
