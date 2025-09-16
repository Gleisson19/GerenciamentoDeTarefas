package Gleisson.GerenciamentoDeTarefas.service;

import Gleisson.GerenciamentoDeTarefas.controller.dto.CreateUserDto;
import Gleisson.GerenciamentoDeTarefas.controller.dto.UpdateUserDto;
import Gleisson.GerenciamentoDeTarefas.entity.User;
import Gleisson.GerenciamentoDeTarefas.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository; // Simula o repositório de usuários
    private UserService userService; // Classe que será testada

    @BeforeEach
    void setUp() {
        // Arrange: Configurar mocks e injetar no service
        userRepository = mock(UserRepository.class); // Criar "fake" do repository
        userService = new UserService(userRepository); // Injetar o fake no service
    }

    @Test
    void createUser_ShouldReturnSavedUserId() {
        // Arrange: Preparar dados de teste
        CreateUserDto dto = new CreateUserDto("Pedro", "pedro@test.com", "1234"); // Dados de teste

        User savedUser = new User();
        savedUser.setUserId(UUID.randomUUID()); // Simula ID gerado pelo banco

        when(userRepository.save(any(User.class))).thenReturn(savedUser); // Quando salvar, retorna o fake

        // Act: Executar ação
        UUID result = userService.createUser(dto); // Chama o método que esta testando

        // Assert: Verificar resultados
        assertThat(result).isEqualTo(savedUser.getUserId()); // Verifica se o ID retornado é o correto
        verify(userRepository, times(1)).save(any(User.class)); // Confirma que o save foi chamado 1 vez
    }

    @Test
    void getUserById_WhenUserExists_ShouldReturnOptionalUser() {
        // Arrange
        UUID id = UUID.randomUUID();
        User user = new User();
        user.setUserId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user)); // Simula busca no banco

        // Act
        Optional<User> result = userService.getUserById(id.toString()); // Chama o método

        // Assert
        assertTrue(result.isPresent()); // Verifica que retornou um usuário
        assertEquals(id, result.get().getUserId()); // Verifica se é o usuário certo
    }

    @Test
    void getUserById_WhenUserDoesNotExist_ShouldReturnEmptyOptional() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty()); // Nenhum usuário encontrado

        // Act
        Optional<User> result = userService.getUserById(id.toString());

        // Assert
        assertTrue(result.isEmpty()); // Deve retornar vazio
    }

    @Test
    void listUsers_ShouldReturnAllUsers() {
        // Arrange
        List<User> users = List.of(new User(), new User()); // Lista simulada
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.listUsers(); // Chama o método

        // Assert
        assertEquals(2, result.size()); // Verifica se retornou 2 usuários
        verify(userRepository, times(1)).findAll(); // Confirma que o findAll foi chamado
    }

    @Test
    void updateById_ShouldUpdateFieldsIfPresent() {
        // Arrange
        UUID id = UUID.randomUUID();
        User existing = new User();
        existing.setUserId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(existing)); // Usuário existe

        UpdateUserDto dto = new UpdateUserDto("Carlos", "4321"); // Dados para atualizar

        // Act
        userService.updateById(id.toString(), dto); // Chama o método

        // Assert
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class); // Captura o usuário salvo
        verify(userRepository).save(captor.capture());
        User updated = captor.getValue();

        assertEquals("Carlos", updated.getUsername()); // Verifica o nome atualizado
        assertEquals("4321", updated.getPassword()); // Verifica a senha atualizada
    }

    @Test
    void deleteById_WhenUserExists_ShouldDeleteUser() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(userRepository.existsById(id)).thenReturn(true); // Usuário existe

        // Act
        userService.deleteById(id.toString()); // Chama o método

        // Assert
        verify(userRepository, times(1)).deleteById(id); // Confirma que deletou
    }

    @Test
    void deleteById_WhenUserDoesNotExist_ShouldDoNothing() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(userRepository.existsById(id)).thenReturn(false); // Usuário não existe

        // Act
        userService.deleteById(id.toString());

        // Assert
        verify(userRepository, never()).deleteById(any()); // Confirma que não tentou deletar
    }
}