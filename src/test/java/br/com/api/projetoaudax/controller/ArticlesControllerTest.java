package br.com.api.projetoaudax.controller;

import br.com.api.projetoaudax.dto.ArticlesDTO;
import br.com.api.projetoaudax.model.Articles;
import br.com.api.projetoaudax.model.Users;
import br.com.api.projetoaudax.service.ArticlesService;
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
class ArticlesControllerTest {

    public static final UUID uuid = UUID.randomUUID();
    public static final String TITLE = "Titulo teste para não cair na exception de 30 caracters";
    public static final String RESUME = "Resumo teste para não cair na exception de 50 caracters tem que usar mais";
    public static final String TEXT = "Lorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem Ipsum";
    public static final String SLUG = "Resume test";

    public static final String SLUGPARAMETER = "Slug get parameter";
    public static final LocalDateTime REGISTERED = LocalDateTime.now();
    public static final Users USERS = new Users();
    public static final Long ID = 1L;
    public static final int INDEX = 0;

    @InjectMocks
    private ArticlesController controller;

    @Mock
    private ArticlesService service;

    @Mock
    private ModelMapper mapper;

    private Articles article = new Articles();

    private ArticlesDTO articleDTO = new ArticlesDTO();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startArticle();
    }


    @Test
    void whenFindByIdThenReturnSuccess() {
        when(service.findById(anyLong())).thenReturn(article);
        when(mapper.map(any(), any())).thenReturn(articleDTO);

        ResponseEntity<ArticlesDTO> response = controller.findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArticlesDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(uuid, response.getBody().getUuid());
        assertEquals(TITLE, response.getBody().getTitle());
        assertEquals(RESUME, response.getBody().getResume());
        assertEquals(TEXT, response.getBody().getText());
        assertEquals(SLUG, response.getBody().getSlug(SLUGPARAMETER));
        assertEquals(REGISTERED, response.getBody().getRegisteredAt());
        assertEquals(USERS, response.getBody().getUsers());

    }

    @Test
    void whenFindAllThenReturnLitOfUserDTO() {
        when(service.findAll()).thenReturn(List.of(article));
        when(mapper.map(any(), any())).thenReturn(articleDTO);

        ResponseEntity<List<ArticlesDTO>> response = controller.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(ArticlesDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(uuid, response.getBody().get(INDEX).getUuid());
        assertEquals(TITLE, response.getBody().get(INDEX).getTitle());
        assertEquals(RESUME, response.getBody().get(INDEX).getResume());
        assertEquals(TEXT, response.getBody().get(INDEX).getText());
        assertEquals(SLUG, response.getBody().get(INDEX).getSlug(SLUGPARAMETER));
        assertEquals(REGISTERED, response.getBody().get(INDEX).getRegisteredAt());
        assertEquals(USERS, response.getBody().get(INDEX).getUsers());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(service.create(any())).thenReturn(article);

        ResponseEntity<ArticlesDTO> response = controller.create(articleDTO);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(service.update(articleDTO)).thenReturn(article);
        when(mapper.map(any(), any())).thenReturn(articleDTO);

        ResponseEntity<ArticlesDTO> response = controller.update(ID, articleDTO);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArticlesDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(uuid, response.getBody().getUuid());
        assertEquals(TITLE, response.getBody().getTitle());
        assertEquals(RESUME, response.getBody().getResume());
        assertEquals(TEXT, response.getBody().getText());
        assertEquals(SLUG, response.getBody().getSlug(SLUGPARAMETER));
        assertEquals(REGISTERED, response.getBody().getRegisteredAt());
        assertEquals(USERS, response.getBody().getUsers());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(service).delete((anyLong()));

        ResponseEntity<ArticlesDTO> response = controller.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).delete(anyLong());

    }


    private void startArticle() {
        article = new Articles(ID, uuid, TITLE, RESUME, TEXT, SLUG, REGISTERED, USERS);
        articleDTO = new ArticlesDTO(ID, uuid, TITLE, RESUME, TEXT, SLUG, REGISTERED, USERS);
    }
}