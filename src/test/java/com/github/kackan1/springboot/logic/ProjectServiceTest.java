package com.github.kackan1.springboot.logic;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.kackan1.springboot.TaskConfigurationProperties;
import com.github.kackan1.springboot.model.TaskGroupRepository;

public class ProjectServiceTest {
    @Test
    @DisplayName("Should throw IllegalStateException when configured to allow just 1 group and the other undone group exists")
    void createGroup_noMultipleGroupsConfig_And_openGroups_throwsIllegalStateException() {
        // given
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);
        // and
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(false);
        // and
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        // system under test
        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);
        // when 
        var exception = catchThrowable(() -> toTest.createGroup(0, LocalDateTime.now()));
        // then
        assertThat(exception)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("one undone group");
    }
}
