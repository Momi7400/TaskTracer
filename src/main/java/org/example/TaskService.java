package org.example;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title, String description) {
        Task task = new Task(null, title, description);

        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public List<Task> getTasksByStatus(TaskStatus status){
        return taskRepository.findByStatus(status);
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }
    public List<Task> getTasksByCreatedAtAsc(){
        return taskRepository.findAllByOrderByCreatedAtAsc();
    }
    public List<Task> getTasksByCreatedAtDesc(){
        return taskRepository.findAllByOrderByCreatedAtDesc();
    }
    public List<Task> getTasksByTitleAsc(){
        return taskRepository.findAllByOrderByTitleAsc();
    }
    public List<Task> getTasksByTitleDesc(){
        return taskRepository.findAllByOrderByTitleDesc();
    }

    public boolean updateTask(Long id, String title, String description, TaskStatus status) {
        Task task = taskRepository.findById(id).orElse(null);

        if (task == null) {
            return false;
        }

        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);

        taskRepository.save(task);

        return true;
    }

    public boolean deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }

        taskRepository.deleteById(id);

        return true;
    }
}