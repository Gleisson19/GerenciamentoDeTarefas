package Gleisson.GerenciamentoDeTarefas.service;


import Gleisson.GerenciamentoDeTarefas.controller.dto.CreateTaskDto;
import Gleisson.GerenciamentoDeTarefas.controller.dto.UpdateTaskStatusDto;
import Gleisson.GerenciamentoDeTarefas.entity.Task;
import Gleisson.GerenciamentoDeTarefas.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskRepository taskRepository; // Simula o repositório de task
    private TaskService taskService; // Classe que será testada

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class); // Criar "fake" do repository
        taskService = new TaskService(taskRepository); // Injetar fake no service
    }

    @Test
    void createTask_ShouldReturnSavedTaskId() {
        // Arrange
        CreateTaskDto dto = new CreateTaskDto("Tarefa1", "Descricao", UUID.randomUUID(), Task.Status.PENDENTE);

        Task savedTask = new Task();
        savedTask.setTaskId(UUID.randomUUID()); // Simula ID gerado pelo banco
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask); // Simula salvar no banco

        // Act
        UUID result = taskService.createTask(dto);

        // Assert
        assertThat(result).isEqualTo(savedTask.getTaskId()); // Verifica se retornou o ID correto
        verify(taskRepository, times(1)).save(any(Task.class)); // Confirma que o save foi chamado
    }

    @Test
    void listTasks_ShouldReturnAllTasks() {
        // Arrange
        Task task1 = new Task();
        Task task2 = new Task();
        List<Task> tasks = List.of(task1, task2);
        when(taskRepository.findAll()).thenReturn(tasks); // Simula findAll

        // Act
        List<Task> result = taskService.listTasks(Optional.empty(), Optional.empty());

        // Assert
        assertEquals(2, result.size()); // Verifica que retornou 2 tarefas
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void listTasks_WithStatusFilter_ShouldReturnFilteredTasks() {
        // Arrange
        Task task = new Task();
        task.setStatus(Task.Status.PENDENTE);
        when(taskRepository.findByStatus(Task.Status.PENDENTE)).thenReturn(List.of(task)); // Simula filtro por status

        // Act
        List<Task> result = taskService.listTasks(Optional.of(Task.Status.PENDENTE), Optional.empty());

        // Assert
        assertEquals(1, result.size()); // Verifica que retornou só 1 tarefa
        assertEquals(Task.Status.PENDENTE, result.get(0).getStatus()); // Verifica o status
    }

    @Test
    void getTaskById_WhenExists_ShouldReturnTask() {
        // Arrange
        UUID id = UUID.randomUUID();
        Task task = new Task();
        task.setTaskId(id);
        when(taskRepository.findById(id)).thenReturn(Optional.of(task)); // Simula busca pelo ID

        // Act
        Optional<Task> result = taskService.getTaskById(id.toString());

        // Assert
        assertTrue(result.isPresent()); // Deve existir
        assertEquals(id, result.get().getTaskId()); // ID correto
    }

    @Test
    void updateStatus_ShouldChangeStatus() {
        // Arrange
        UUID id = UUID.randomUUID();
        Task task = new Task();
        task.setTaskId(id);
        task.setStatus(Task.Status.PENDENTE); // Status inicial
        when(taskRepository.findById(id)).thenReturn(Optional.of(task)); // Simula busca pelo ID

        UpdateTaskStatusDto dto = new UpdateTaskStatusDto(Task.Status.CONCLUIDA); // Novo status

        // Act
        taskService.updateStatus(id.toString(), dto);

        // Assert
        ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class); // Captura tarefa salva
        verify(taskRepository).save(captor.capture());
        Task updated = captor.getValue();

        assertEquals(Task.Status.CONCLUIDA, updated.getStatus()); // Verifica se mudou o status
        assertNotNull(updated.getCompletionDate()); // Verifica se a data de conclusão foi preenchida
    }
}

