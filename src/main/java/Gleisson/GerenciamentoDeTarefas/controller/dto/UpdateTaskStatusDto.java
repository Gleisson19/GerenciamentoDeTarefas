package Gleisson.GerenciamentoDeTarefas.controller.dto;


import Gleisson.GerenciamentoDeTarefas.entity.Task.Status;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusDto(
        @NotNull(message = "O status da tarefa é obrigatório")
        Status status
) {}
