package de.neoludolph.task_tracker_cli.Service;

import de.neoludolph.task_tracker_cli.Model.TaskModel;
import de.neoludolph.task_tracker_cli.Repository.TaskRepositoryImpl;

import java.io.IOException;

public class TaskServiceImpl implements TaskService {

    private final TaskRepositoryImpl taskRepository;

    public TaskServiceImpl(TaskRepositoryImpl taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void add(String description) {
        try {
            taskRepository.saveNewTaskInJson(new TaskModel(description));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(long id, String description) {
        try {
            taskRepository.saveUpdatedTaskInJson(id, description);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {
        try {
            taskRepository.deleteTaskInJson(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void markInProgress(long id) {
        try {
            taskRepository.markTaskAsInProgress(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void markDone(long id) {

    }

    @Override
    public void listAll() {

    }

    @Override
    public void listDone() {

    }

    @Override
    public void listToDo() {

    }

    @Override
    public void listInProgress() {

    }
}
