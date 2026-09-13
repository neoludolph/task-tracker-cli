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
    public void saveUpdatedTaskInJson(long id, String description) throws IOException {
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
    
    @Override
    public void deleteTaskInJson(long id) throws IOException {
        Path path = Path.of("src/main/resources/tasks.json");
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ArrayList<TaskModel> taskArrayList = objectMapper.readValue(
                path.toFile(),
                new TypeReference<ArrayList<TaskModel>>() {}
        );

        boolean found = false;

        for (int i = 0; i < taskArrayList.size(); i++) {
            TaskModel currentTaskModel = taskArrayList.get(i);
            if (currentTaskModel.getId() == id) {
                taskArrayList.remove(currentTaskModel);
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
    }

    @Override
    public void markTaskAsInProgress(long id) throws IOException {
        Path path = Path.of("src/main/resources/tasks.json");
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ArrayList<TaskModel> taskArrayList = objectMapper.readValue(
                path.toFile(),
                new TypeReference<ArrayList<TaskModel>>() {}
        );

        boolean found = false;

        for (int i = 0; i < taskArrayList.size(); i++) {
            TaskModel currentTaskModel = taskArrayList.get(i);
            if (currentTaskModel.getId() == id) {
                currentTaskModel.setStatus(TaskModel.Status.IN_PROGRESS);
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
    }

    @Override
    public void markTaskAsDone(long id) throws IOException {
        Path path = Path.of("src/main/resources/tasks.json");
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ArrayList<TaskModel> taskArrayList = objectMapper.readValue(
                path.toFile(),
                new TypeReference<ArrayList<TaskModel>>() {}
        );

        boolean found = false;

        for (int i = 0; i < taskArrayList.size(); i++) {
            TaskModel currentTaskModel = taskArrayList.get(i);
            if (currentTaskModel.getId() == id) {
                currentTaskModel.setStatus(TaskModel.Status.DONE);
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
    }

    @Override
    public void listAllTasks() throws IOException {
        Path path = Path.of("src/main/resources/tasks.json");
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ArrayList<TaskModel> taskArrayList = objectMapper.readValue(
                path.toFile(),
                new TypeReference<ArrayList<TaskModel>>() {}
        );

        StringBuilder stringBuilder = new StringBuilder();

        for (TaskModel currentTaskModel : taskArrayList) {
            stringBuilder
                    .append("\n")
                    .append("Task ")
                    .append(currentTaskModel.getId())
                    .append("\n\n")
                    .append("Id: ")
                    .append(currentTaskModel.getId())
                    .append("\n")
                    .append("Description: ")
                    .append(currentTaskModel.getDescription())
                    .append("\n")
                    .append("Created at: ")
                    .append(currentTaskModel.getCreatedAt())
                    .append("\n");
            if (currentTaskModel.getUpdatedAt() != null) {
               stringBuilder
                       .append("Updated at: ")
                       .append(currentTaskModel.getUpdatedAt())
                       .append("\n");
            }
            stringBuilder
                    .append("Status: ")
                    .append(currentTaskModel.getStatus())
                    .append("\n\n")
                    .append("---------------------------------------------------")
                    .append("\n");
        }
        System.out.println(stringBuilder);
    }

    @Override
    public void listDoneTasks() throws IOException {
        Path path = Path.of("src/main/resources/tasks.json");
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ArrayList<TaskModel> taskArrayList = objectMapper.readValue(
                path.toFile(),
                new TypeReference<ArrayList<TaskModel>>() {}
        );

        StringBuilder stringBuilder = new StringBuilder();

        for (TaskModel currentTaskModel : taskArrayList) {
            if (currentTaskModel.getStatus() == TaskModel.Status.DONE) {
                stringBuilder
                        .append("\n")
                        .append("Task ")
                        .append(currentTaskModel.getId())
                        .append("\n\n")
                        .append("Id: ")
                        .append(currentTaskModel.getId())
                        .append("\n")
                        .append("Description: ")
                        .append(currentTaskModel.getDescription())
                        .append("\n")
                        .append("Created at: ")
                        .append(currentTaskModel.getCreatedAt())
                        .append("\n");
                if (currentTaskModel.getUpdatedAt() != null) {
                    stringBuilder
                            .append("Updated at: ")
                            .append(currentTaskModel.getUpdatedAt())
                            .append("\n");
                }
                stringBuilder
                        .append("Status: ")
                        .append(currentTaskModel.getStatus())
                        .append("\n\n")
                        .append("---------------------------------------------------")
                        .append("\n");
            }
        }
        if (stringBuilder.isEmpty()) {
            System.out.println("There are no tasks with the Status \"DONE\"");
        }
        System.out.println(stringBuilder);
    }

    @Override
    public void listToDoTasks() throws IOException {
        Path path = Path.of("src/main/resources/tasks.json");
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ArrayList<TaskModel> taskArrayList = objectMapper.readValue(
                path.toFile(),
                new TypeReference<ArrayList<TaskModel>>() {}
        );

        StringBuilder stringBuilder = new StringBuilder();

        for (TaskModel currentTaskModel : taskArrayList) {
            if (currentTaskModel.getStatus() == TaskModel.Status.TODO) {
                stringBuilder
                        .append("\n")
                        .append("Task ")
                        .append(currentTaskModel.getId())
                        .append("\n\n")
                        .append("Id: ")
                        .append(currentTaskModel.getId())
                        .append("\n")
                        .append("Description: ")
                        .append(currentTaskModel.getDescription())
                        .append("\n")
                        .append("Created at: ")
                        .append(currentTaskModel.getCreatedAt())
                        .append("\n");
                if (currentTaskModel.getUpdatedAt() != null) {
                    stringBuilder
                            .append("Updated at: ")
                            .append(currentTaskModel.getUpdatedAt())
                            .append("\n");
                }
                stringBuilder
                        .append("Status: ")
                        .append(currentTaskModel.getStatus())
                        .append("\n\n")
                        .append("---------------------------------------------------")
                        .append("\n");
            }
        }
        if (stringBuilder.isEmpty()) {
            System.out.println("There are no tasks with the Status \"TODO\"");
        }
        System.out.println(stringBuilder);
    }

    @Override
    public void listInProgressTasks() throws IOException {
        Path path = Path.of("src/main/resources/tasks.json");
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        ArrayList<TaskModel> taskArrayList = objectMapper.readValue(
                path.toFile(),
                new TypeReference<ArrayList<TaskModel>>() {}
        );

        StringBuilder stringBuilder = new StringBuilder();

        for (TaskModel currentTaskModel : taskArrayList) {
            if (currentTaskModel.getStatus() == TaskModel.Status.IN_PROGRESS) {
                stringBuilder
                        .append("\n")
                        .append("Task ")
                        .append(currentTaskModel.getId())
                        .append("\n\n")
                        .append("Id: ")
                        .append(currentTaskModel.getId())
                        .append("\n")
                        .append("Description: ")
                        .append(currentTaskModel.getDescription())
                        .append("\n")
                        .append("Created at: ")
                        .append(currentTaskModel.getCreatedAt())
                        .append("\n");
                if (currentTaskModel.getUpdatedAt() != null) {
                    stringBuilder
                            .append("Updated at: ")
                            .append(currentTaskModel.getUpdatedAt())
                            .append("\n");
                }
                stringBuilder
                        .append("Status: ")
                        .append(currentTaskModel.getStatus())
                        .append("\n\n")
                        .append("---------------------------------------------------")
                        .append("\n");
            }
        }
        if (stringBuilder.isEmpty()) {
            System.out.println("There are no tasks with the Status \"IN_PROGRESS\"");
        }
        System.out.println(stringBuilder);
    }
}
