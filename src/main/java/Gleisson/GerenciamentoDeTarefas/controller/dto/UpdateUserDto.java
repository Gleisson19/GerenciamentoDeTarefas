package Gleisson.GerenciamentoDeTarefas.controller.dto;

import jakarta.validation.constraints.Size;

public record UpdateUserDto(
        @Size(max = 20, message = "O nome de usuário deve ter no máximo 20 caracteres")
        String username,

        @Size(min = 4, message = "A senha deve ter no mínimo 4 caracteres")
        String password
) {}
