package de.neoludolph.task_tracker_cli.Repository;

import de.neoludolph.task_tracker_cli.Model.TaskModel;

import java.io.IOException;

public interface TaskRepository {
    void loadJson() throws IOException;
    void saveNewTaskInJson(TaskModel task) throws IOException;
    void saveUpdatedTaskInJson(long id, String description) throws IOException;
    void deleteTaskInJson(long id) throws IOException;
    void markTaskAsInProgress(long id) throws IOException;
    void markTaskAsDone(long id) throws IOException;
    void listAllTasks() throws IOException;
}
