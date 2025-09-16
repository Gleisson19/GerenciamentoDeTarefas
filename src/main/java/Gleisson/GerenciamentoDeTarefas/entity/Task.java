package Gleisson.GerenciamentoDeTarefas.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "tb_tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID taskId;

    @Column(nullable = false)
    @NotBlank(message = "O título da tarefa é obrigatório")
    private String title;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "O status da tarefa é obrigatório")
    private Status status = Status.PENDENTE; // Se não for informado nada vira pendente quando ir para o banco

    @CreationTimestamp
    private Instant creationDate;

    private Instant completionDate;

    @Column(nullable = false)
    @NotNull(message = "O ID do usuário é obrigatório")
    private UUID userId;

    public Task() {
    }

    public Task(UUID taskId, String title, String description, Status status, Instant creationDate, Instant completionDate, UUID userId) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.creationDate = creationDate;
        this.completionDate = completionDate;
        this.userId = userId;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setTaskId(UUID taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        if (status == Status.CONCLUIDA) {
            this.completionDate = Instant.now();
        } else {
            this.completionDate = null;
        }
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Instant completionDate) {
        this.completionDate = completionDate;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    // Faz com que o status tenha apenas esses três tipos
    public enum Status {
        PENDENTE,
        EM_ANDAMENTO,
        CONCLUIDA
    }
}