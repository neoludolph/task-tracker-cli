package de.neoludolph.task_tracker_cli.Service;

public interface TaskService {
    void add(String description);
    void update(long id, String description);
    void delete(long id);
    void markInProgress(long id);
    void markDone(long id);
    void listAll();
    void listDone();
    void listToDo();
    void listInProgress();
}
