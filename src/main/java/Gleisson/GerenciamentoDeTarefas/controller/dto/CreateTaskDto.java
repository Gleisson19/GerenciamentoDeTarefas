package Gleisson.GerenciamentoDeTarefas.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import Gleisson.GerenciamentoDeTarefas.entity.Task.Status;

public record CreateTaskDto(
        @NotBlank(message = "O título da tarefa é obrigatório")
        String title,

        String description,

        @NotNull(message = "O ID do usuário é obrigatório")
        UUID userId,

        // Permiti que o status seja enviado
        Status status
) {}
