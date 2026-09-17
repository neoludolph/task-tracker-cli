package de.neoludolph.task_tracker_cli.Service;

import de.neoludolph.task_tracker_cli.Model.TaskModel;
import de.neoludolph.task_tracker_cli.Repository.TaskRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TaskServiceImplTest {
    @Test
    void testTaskServiceAdd() throws IOException {
        String description = "Shopping";
        TaskRepositoryImpl taskRepository = mock(TaskRepositoryImpl.class);
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        taskService.add(description);
        verify(taskRepository).saveNewTaskInJson(any(TaskModel.class));
    }
}
