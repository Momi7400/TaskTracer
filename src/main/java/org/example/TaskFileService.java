package org.example;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class TaskFileService {

    private final TaskRepository taskRepository;

    public TaskFileService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void saveTasksToFile(List<Task> tasks) {
        try (FileWriter writer = new FileWriter("tasks.txt")) {

            for (Task task : tasks) {
                writer.write(
                        task.getId() + ";" +
                                task.getTitle() + ";" +
                                task.getDescription() + ";" +
                                task.getStatus() + ";" +
                                task.getCreatedAt() + "\n"
                );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    public void saveTasksOnShutdown() {
        saveTasksToFile(taskRepository.findAll());
    }
}