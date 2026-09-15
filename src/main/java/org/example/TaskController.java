package org.example;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;

    }
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tasks successfully retrieved")
    })
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);

        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(task);
    }
    @GetMapping("/status/{status}")
    public List<Task> getTasksByStatus(@PathVariable TaskStatus status) {
        return taskService.getTasksByStatus(status);
    }
    @GetMapping("/sort/date/asc")
    public List<Task> getTasksByCreatedAtAsc(){
        return taskService.getTasksByCreatedAtAsc();
    }
    @GetMapping("/sort/date/desc")
    public List<Task> getTasksByCreatedAtDesc(){
        return taskService.getTasksByCreatedAtDesc();
    }
    @GetMapping("/sort/title/asc")
    public List<Task> getTasksByTitleAsc(){
        return taskService.getTasksByTitleAsc();
    }
    @GetMapping("sort/title/desc")
    public List<Task> getTasksByTitleDesc(){
        return taskService.getTasksByTitleDesc();
    }
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task successfully updated"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task task
    ) {
        boolean updated = taskService.updateTask(
                id,
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );

        if (!updated) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taskService.getTaskById(id));
    }
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Task successfully deleted"),
            @ApiResponse(responseCode = "404", description =  "Task not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        boolean deleted = taskService.deleteTask(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Task successfully created")
    })
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task createdTask = taskService.createTask(
                task.getTitle(),
                task.getDescription()
        );

        return ResponseEntity.status(201).body(createdTask);
    }
}