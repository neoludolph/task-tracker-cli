package de.neoludolph.task_tracker_cli.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskModelTest {
    @Test
    void constructorSetsAttributes() {
        TaskModel taskModel = new TaskModel("Shopping");

        assertEquals("Shopping", taskModel.getDescription());
        assertEquals(TaskModel.Status.TODO, taskModel.getStatus());
        assertTrue(taskModel.getCreatedAt().isBefore(LocalDateTime.now()));
    }
}
