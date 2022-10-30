package br.com.api.projetoaudax.service;

import br.com.api.projetoaudax.dto.UsersDTO;
import br.com.api.projetoaudax.model.Articles;
import br.com.api.projetoaudax.model.Users;
import br.com.api.projetoaudax.repository.UsersRepository;
import br.com.api.projetoaudax.service.exceptions.DataIntegrityViolationException;
import br.com.api.projetoaudax.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
class UsersServiceTest {

    public static final String USERNAME = "Alfredo";
    public static final String PASSWORD = "123";
    public static final UUID uuid = UUID.randomUUID();
    public static final LocalDateTime REGISTERED = LocalDateTime.now();
    public static final List<Articles> ARTICLES = new ArrayList<>();
    public static final Long ID = 1L;
    public static final int INDEX = 0;
    public static final String OBJETO_NAO_ENCONTRADO = "Usuário não encontrado com a id: " + ID;
    public static final String USERNAME_JA_CADASTRADO_NO_SISTEMA = "Username já cadastrado no sistema";

    @InjectMocks
    private UsersService service;

    @Mock
    private UsersRepository repository;

    @Mock
    private ModelMapper mapper;

    private Users user;

    private UsersDTO userDTO;

    private Optional<Users> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsers();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        when(repository.findById(anyLong())).thenReturn(optionalUser);

        Users response = service.findById(ID);

        assertNotNull(response);
        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(uuid, response.getUuid());
        assertEquals(USERNAME, response.getUsername());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(REGISTERED, response.getRegisteredAt());
        assertEquals(ARTICLES, response.getArticles());
    }

    @Test
    void whenFindByUsernameThenReturnAnUsername() {
        when(repository.findByUsername(anyString())).thenReturn(optionalUser);

        Users response = service.findByName(USERNAME);

        assertNotNull(response);
        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(uuid, response.getUuid());
        assertEquals(USERNAME, response.getUsername());

    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            service.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFinAllThenReturnAnListOfArticles() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<Users> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Users.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(uuid, response.get(INDEX).getUuid());
        assertEquals(USERNAME, response.get(INDEX).getUsername());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
        assertEquals(REGISTERED, response.get(INDEX).getRegisteredAt());
        assertEquals(ARTICLES, response.get(INDEX).getArticles());

    }

    @Test
    void whenCreateThenReturnSucess() {
        when(repository.save(any())).thenReturn(user);

        Users response = service.create(userDTO);

        assertNotNull(response);
        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(uuid, response.getUuid());
        assertEquals(USERNAME, response.getUsername());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(REGISTERED, response.getRegisteredAt());
        assertEquals(ARTICLES, response.getArticles());
    }

    @Test
    void whenCreateThenReturnDataIntegrityViolationException() {
        when(repository.findByUsername(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2L);
            service.create(userDTO);
        } catch(Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(USERNAME_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }


    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        Users response = service.update(userDTO);

        assertNotNull(response);
        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(uuid, response.getUuid());
        assertEquals(USERNAME, response.getUsername());
        assertEquals(PASSWORD, response.getPassword());
        assertEquals(REGISTERED, response.getRegisteredAt());
        assertEquals(ARTICLES, response.getArticles());
    }

    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyLong())).thenReturn(optionalUser);
        doNothing().when(repository).deleteById(anyLong());
        service.delete(ID);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    void deleteWithObjectNotFoundException() {
        when(repository.findById(anyLong())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));
        try {
            service.delete(ID);

        } catch(Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }


    private void startUsers() {
        user = new Users(ID, uuid, USERNAME, PASSWORD, REGISTERED, ARTICLES);
        userDTO = new UsersDTO(ID, uuid, USERNAME, PASSWORD, REGISTERED);
        optionalUser = Optional.of(new Users(ID, uuid, USERNAME, PASSWORD, REGISTERED, ARTICLES));
    }
}