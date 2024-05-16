package com.github.kackan1.springboot.model.event;

import java.time.Clock;

import com.github.kackan1.springboot.model.Task;

public class TaskUndone extends TaskEvent {
    TaskUndone(final Task source) {
        super(source.getId(), Clock.systemDefaultZone());
    }
}
