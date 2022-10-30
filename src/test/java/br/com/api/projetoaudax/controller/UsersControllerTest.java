package br.com.api.projetoaudax.controller;

import br.com.api.projetoaudax.dto.UsersDTO;
import br.com.api.projetoaudax.model.Articles;
import br.com.api.projetoaudax.model.Users;
import br.com.api.projetoaudax.service.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class UsersControllerTest {

    public static final String USERNAME = "Alfredo";
    public static final String PASSWORD = "123";
    public static final UUID uuid = UUID.randomUUID();
    public static final LocalDateTime REGISTERED = LocalDateTime.now();
    public static final List<Articles> ARTICLES = new ArrayList<>();
    public static final Long ID = 1L;
    public static final int INDEX = 0;


    @InjectMocks
    private UsersController controller;

    @Mock
    private UsersService service;

    @Mock
    private ModelMapper mapper;

    private Users user = new Users();

    private UsersDTO userDTO = new UsersDTO();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsers();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(service.findById(anyLong())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UsersDTO> response = controller.findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UsersDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(uuid, response.getBody().getUuid());
        assertEquals(USERNAME, response.getBody().getUsername());
        assertEquals(PASSWORD, response.getBody().getPassword());
        assertEquals(REGISTERED, response.getBody().getRegisteredAt());

    }

    @Test
    void whenFindByUsernameThenReturnSuccess() {
        when(service.findByName(anyString())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UsersDTO> response = controller.findByUsername(USERNAME);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UsersDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(uuid, response.getBody().getUuid());
        assertEquals(USERNAME, response.getBody().getUsername());

    }

    @Test
    void whenFindAllThenReturnListOfUserDTO() {
        when(service.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UsersDTO>> response = controller.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UsersDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(uuid, response.getBody().get(INDEX).getUuid());
        assertEquals(USERNAME, response.getBody().get(INDEX).getUsername());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
        assertEquals(REGISTERED, response.getBody().get(INDEX).getRegisteredAt());

    }

    @Test
    void whenCreateThenReturnCreated() {
        when(service.create(any())).thenReturn(user);

        ResponseEntity<UsersDTO> response = controller.create(userDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(service.update(userDTO)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UsersDTO> response = controller.update(ID, userDTO);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UsersDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(uuid, response.getBody().getUuid());
        assertEquals(USERNAME, response.getBody().getUsername());
        assertEquals(PASSWORD, response.getBody().getPassword());
        assertEquals(REGISTERED, response.getBody().getRegisteredAt());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(service).delete((anyLong()));

        ResponseEntity<UsersDTO> response = controller.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyLong());

    }

    private void startUsers() {
        user = new Users(ID, uuid, USERNAME, PASSWORD, REGISTERED, ARTICLES);
        userDTO = new UsersDTO(ID, uuid, USERNAME, PASSWORD, REGISTERED);
    }
}