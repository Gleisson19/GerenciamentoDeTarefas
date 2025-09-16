package Gleisson.GerenciamentoDeTarefas.repository;


import Gleisson.GerenciamentoDeTarefas.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByStatus(Task.Status status);

    List<Task> findByStatusAndUserId(Task.Status status, UUID userId);

    List<Task> findByUserId(UUID userId);
}