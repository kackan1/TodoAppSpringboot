package com.github.kackan1.springboot;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.github.kackan1.springboot.model.Task;
import com.github.kackan1.springboot.model.TaskGroup;
import com.github.kackan1.springboot.model.TaskGroupRepository;

@Component
public class Warmup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(Warmup.class);

    private final TaskGroupRepository groupRepository;

    Warmup(final TaskGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Apllication warmup after context refreshed");
        final String description = "ApplicationContextEvent";
        if (!groupRepository.existsByDescription(description)) {
            logger.info("No required group found! Adding it");
            var group = new TaskGroup();
            group.setDescription(description);
            group.setTasks(Set.of(
                    new Task("ContextClosedEvent", null, group),
                    new Task("ContextRefreshedEvent", null, group),
                    new Task("ContextStoppedEvent", null, group),
                    new Task("ContextStartedEvent", null, group)));
            groupRepository.save(group);
        }
    }
}
