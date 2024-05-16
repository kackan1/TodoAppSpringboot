package com.github.kackan1.springboot.reports;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.kackan1.springboot.model.Task;
import com.github.kackan1.springboot.model.TaskRepository;

@RestController
@RequestMapping("/reports")
public class ReportController {
    private final TaskRepository taskRepository;
    private final PeristedTaskEventRepository eventRepository;

    public ReportController(TaskRepository taskRepository, PeristedTaskEventRepository eventRepository) {
        this.taskRepository = taskRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/doneBeforeDeadline/{id}")
    public ResponseEntity<TaskWithDoneBeforeDeadline> readTaskWithDoneBeforeDeadline(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(task -> new TaskWithDoneBeforeDeadline(task, eventRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/count/{id}")
    public ResponseEntity<TaskWithChangesCount> readTaskWithCount(@PathVariable int id) {
        return taskRepository.findById(id)
                .map(task -> new TaskWithChangesCount(task, eventRepository.findByTaskId(id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private static class TaskWithDoneBeforeDeadline {
        public String description;
        public boolean done;
        public boolean isDoneBeforeDeadline;

        private TaskWithDoneBeforeDeadline(final Task task, List<PersistedTaskEvent> event) {
            description = task.getDescription();
            done = task.isDone();
            if (done) {
                isDoneBeforeDeadline = event.get(event.size() - 1).occurence.isBefore(task.getDeadline());
            } else {
                isDoneBeforeDeadline = false;
            }
        }
    }

    private static class TaskWithChangesCount {
        public String description;
        public boolean done;
        public int changesCount;

        private TaskWithChangesCount(final Task task, final List<PersistedTaskEvent> events) {
            description = task.getDescription();
            done = task.isDone();
            changesCount = events.size();
        }
    }
}
