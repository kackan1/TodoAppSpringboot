package com.github.kackan1.springboot.logic;

import com.github.kackan1.springboot.model.Task;
import com.github.kackan1.springboot.model.TaskGroup;
import com.github.kackan1.springboot.model.TaskGroupRepository;
import com.github.kackan1.springboot.model.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {

    @Test
    @DisplayName("Should throw IllegalStateException when group has undone tasks")
    void toggleGroup_groupHasUndoneTask_throwsIllegalStateException() {
        // given
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(true);
        // system under test
        var toTest = new TaskGroupService(null, mockTaskRepository);
        // when
        var exception = catchThrowable(() -> toTest.toggleGroup(anyInt()));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("undone tasks");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when group with given id has not been found")
    void toggleGroup_groupHasNotBeenFound_throwsIllegalArgumentException() {
        // given
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(false);
        // and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        // system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        // when
        var exception = catchThrowable(() -> toTest.toggleGroup(anyInt()));
        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("id not found");
    }

    @Test
    @DisplayName("Should toggle TaskGroup to done when there are no undone tasks and group exists")
    void toggleGroup_noUndoneTasks_And_groupExists_togglesTaskGroup(){
        // given
        var taskGroup = new TaskGroup();
        var beforeToggle = taskGroup.isDone();
        // and
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(false);
        // and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(taskGroup));
        // system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        // when
        toTest.toggleGroup(anyInt());
        // then
        assertThat(taskGroup.isDone()).isNotEqualTo(beforeToggle);
    }

}