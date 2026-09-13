package de.neoludolph.task_tracker_cli.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.neoludolph.task_tracker_cli.Model.TaskModel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskRepositoryImpl implements TaskRepository {
    @Override
    public void loadJson() throws IOException {
        Path path = Path.of("src/main/resources/tasks.json");
        if (Files.notExists(path)) {
            Files.writeString(path, "[\n]");
        }
    }

    @Override
    public void saveNewTaskInJson(TaskModel task) throws IOException {
        loadJson();
        Path path = Path.of("src/main/resources/tasks.json");
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ArrayList<TaskModel> taskArrayList = objectMapper.readValue(
                path.toFile(),
                new TypeReference<ArrayList<TaskModel>>() {}
        );

        if (taskArrayList.isEmpty()) {
            task.setId(0);
            taskArrayList.add(task);
            objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(path.toFile(), taskArrayList);
        } else {
            TaskModel lastTask = taskArrayList.getLast();
            task.setId(lastTask.getId() + 1);
            taskArrayList.add(task);
            objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(path.toFile(), taskArrayList);
        }
    }

    @Override
    public void saveUpdatedTaskJson(long id, String description) throws IOException {
        Path path = Path.of("src/main/resources/tasks.json");
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ArrayList<TaskModel> taskArrayList = objectMapper.readValue(
                path.toFile(),
                new TypeReference<ArrayList<TaskModel>>() {}
        );

        try {

            boolean found = false;

            for (int i = 0; i < taskArrayList.size(); i++) {
                TaskModel currentTaskModel = taskArrayList.get(i);
                if (currentTaskModel.getId() == id) {
                    currentTaskModel.setUpdatedAt(LocalDateTime.now());
                    currentTaskModel.setDescription(description);
                    found = true;
                }
            }

            if (!found) {
                throw new IllegalArgumentException("Update failed: The task with the id \""
                        + id
                        + "\""
                        + " does not exist. "
                        + "Please check your tasks id's with \"task-cli list\" in order to enter a valid id.");
            }
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(path.toFile(), taskArrayList);
        } catch (FileNotFoundException e) {
            System.out.println("Update failed: You have to create a task first!");
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
