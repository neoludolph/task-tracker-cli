package de.neoludolph.task_tracker_cli.Service;

import de.neoludolph.task_tracker_cli.Model.TaskModel;
import de.neoludolph.task_tracker_cli.Repository.TaskRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class TaskServiceImplTest {
    @Test
    void testTaskServiceAdd() throws IOException {
        String description = "Shopping";
        TaskRepositoryImpl taskRepository = mock(TaskRepositoryImpl.class);
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        taskService.add(description);

        ArgumentCaptor<TaskModel> captor = ArgumentCaptor.forClass(TaskModel.class);

        verify(taskRepository).saveNewTaskInJson(captor.capture());

        TaskModel savedTask = captor.getValue();

        assertEquals(description, savedTask.getDescription());
    }

    @Test
    void testTaskServiceUpdate() throws IOException {
        long id = 1;
        String description = "Shopping";
        TaskRepositoryImpl taskRepository = mock(TaskRepositoryImpl.class);
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        taskService.update(id, description);

        verify(taskRepository).saveUpdatedTaskInJson(id, description);
    }

    @Test
    void testTaskServiceDelete() throws IOException {
        long id = 1;
        TaskRepositoryImpl taskRepository = mock(TaskRepositoryImpl.class);
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        taskService.delete(id);

        verify(taskRepository).deleteTaskInJson(id);
    }

    @Test
    void testTaskServiceMarkInProgress() throws IOException {
        long id = 1;
        TaskRepositoryImpl taskRepository = mock(TaskRepositoryImpl.class);
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        taskService.markInProgress(id);

        verify(taskRepository).markTaskAsInProgress(id);
    }

    @Test
    void testTaskServiceMarkDone() throws IOException {
        long id = 1;
        TaskRepositoryImpl taskRepository = mock(TaskRepositoryImpl.class);
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        taskService.markDone(id);

        verify(taskRepository).markTaskAsDone(id);
    }

    @Test
    void testTaskServiceListAll() throws IOException {
        long id = 1;
        TaskRepositoryImpl taskRepository = mock(TaskRepositoryImpl.class);
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        taskService.listAll();

        verify(taskRepository).listAllTasks();
    }

    @Test
    void testTaskServiceListDone() throws IOException {
        long id = 1;
        TaskRepositoryImpl taskRepository = mock(TaskRepositoryImpl.class);
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        taskService.listDone();

        verify(taskRepository).listDoneTasks();
    }

    @Test
    void testTaskServiceListToDo() throws IOException {
        long id = 1;
        TaskRepositoryImpl taskRepository = mock(TaskRepositoryImpl.class);
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        taskService.listToDo();

        verify(taskRepository).listToDoTasks();
    }

    @Test
    void testTaskServiceListInProgress() throws IOException {
        long id = 1;
        TaskRepositoryImpl taskRepository = mock(TaskRepositoryImpl.class);
        TaskServiceImpl taskService = new TaskServiceImpl(taskRepository);
        taskService.listInProgress();

        verify(taskRepository).listInProgressTasks();
    }
}
