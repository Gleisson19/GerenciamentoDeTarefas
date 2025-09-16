package Gleisson.GerenciamentoDeTarefas.controller;


import Gleisson.GerenciamentoDeTarefas.controller.dto.CreateTaskDto;
import Gleisson.GerenciamentoDeTarefas.controller.dto.UpdateTaskStatusDto;
import Gleisson.GerenciamentoDeTarefas.entity.Task;
import Gleisson.GerenciamentoDeTarefas.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) { // Chamando a TaskService
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<UUID> createTask(@RequestBody @Valid CreateTaskDto createTaskDto) {
        UUID taskId = taskService.createTask(createTaskDto);
        return ResponseEntity.ok(taskId);
    }

    @GetMapping
    public ResponseEntity<List<Task>> listTasks(
            @RequestParam Optional<Task.Status> status,
            @RequestParam Optional<UUID> userId) {
        return ResponseEntity.ok(taskService.listTasks(status, userId));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable String taskId) {
        return taskService.getTaskById(taskId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Void> updateTaskStatus(
            @PathVariable String taskId,
            @RequestBody @Valid UpdateTaskStatusDto statusDto) {
        taskService.updateStatus(taskId, statusDto);
        return ResponseEntity.noContent().build();
    }
}