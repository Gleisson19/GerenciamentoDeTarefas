package Gleisson.GerenciamentoDeTarefas.service;

import Gleisson.GerenciamentoDeTarefas.controller.dto.CreateTaskDto;
import Gleisson.GerenciamentoDeTarefas.controller.dto.UpdateTaskStatusDto;
import Gleisson.GerenciamentoDeTarefas.entity.Task;
import Gleisson.GerenciamentoDeTarefas.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public UUID createTask(CreateTaskDto createTaskDto) {
        Task task = new Task();
        task.setTitle(createTaskDto.title());
        task.setDescription(createTaskDto.description());
        task.setUserId(createTaskDto.userId());

        // Se o status for enviado, define; senão, usa o padrão PENDENTE
        if (createTaskDto.status() != null) {
            task.setStatus(createTaskDto.status());
        }

        Task saved = taskRepository.save(task);
        return saved.getTaskId();
    }

    // Método para filtrar as tarefas
    public List<Task> listTasks(Optional<Task.Status> status, Optional<UUID> userId) {
        if (status.isPresent() && userId.isPresent()) {
            return taskRepository.findByStatusAndUserId(status.get(), userId.get());
        } else if (status.isPresent()) {
            return taskRepository.findByStatus(status.get());
        } else if (userId.isPresent()) {
            return taskRepository.findByUserId(userId.get());
        } else {
            return taskRepository.findAll();
        }
    }

    // Método para buscar tarefa pelo taskId
    public Optional<Task> getTaskById(String taskId) {
        return taskRepository.findById(UUID.fromString(taskId));
    }

    // Método para atualizar tarefa pelo taskId
    public void updateStatus(String taskId, UpdateTaskStatusDto statusDto) {
        var taskOpt = taskRepository.findById(UUID.fromString(taskId)); // Busca a tarefa no banco pelo ID recebido em formato String (converte para UUID)
        if (taskOpt.isPresent()) { // Verifica se a tarefa existe no banco.
            Task task = taskOpt.get(); // Se existir, pega a entidade task
            task.setStatus(statusDto.status()); // Atualiza o status da tarefa com o valor recebido no DTO
            taskRepository.save(task);
        }
    }
}
