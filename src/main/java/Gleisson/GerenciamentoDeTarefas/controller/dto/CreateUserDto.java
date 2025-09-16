package Gleisson.GerenciamentoDeTarefas.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDto(
        @NotBlank(message = "O nome de usuário é obrigatório")
        @Size(max = 20, message = "O nome de usuário deve ter no máximo 20 caracteres")
        String username,

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 4, message = "A senha deve ter no mínimo 4 caracteres")
        String password
) {}
