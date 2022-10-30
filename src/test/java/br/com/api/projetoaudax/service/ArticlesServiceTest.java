package br.com.api.projetoaudax.service;

import br.com.api.projetoaudax.dto.ArticlesDTO;
import br.com.api.projetoaudax.model.Articles;
import br.com.api.projetoaudax.model.Users;
import br.com.api.projetoaudax.repository.ArticlesRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ArticlesServiceTest {

    public static final UUID uuid = UUID.randomUUID();
    public static final String TITLE = "Titulo teste para não cair na exception de 30 caracters";
    public static final String RESUME = "Resumo teste para não cair na exception de 50 caracters tem que usar mais";
    public static final String TEXT = "Lorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem IpsumLorem Ipsum";
    public static final String SLUG = "Resume test";
    public static final LocalDateTime REGISTERED = LocalDateTime.now();
    public static final Users USERS = new Users();
    public static final Long ID = 1L;
    public static final int INDEX = 0;

    public static final String OBJETO_NAO_ENCONTRADO = "Artigo não encontrado com a id: " + ID;
    public static final String TITULO_JA_CADASTRADO_NO_SISTEMA = "Titulo já cadastrado no sistema";

    @InjectMocks
    private ArticlesService service;

    @Mock
    private ArticlesRepository repository;

    @Mock
    private ModelMapper mapper;

    private Articles article;

    private ArticlesDTO articleDTO;

    private Optional<Articles> optionalArticle;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startArticle();
    }

    @Test
    void whenFindByIdThenReturnAnArticleInstance() {
        when(repository.findById(anyLong())).thenReturn(optionalArticle);

        Articles response = service.findById(ID);

        assertNotNull(response);
        assertEquals(Articles.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(uuid, response.getUuid());
        assertEquals(TITLE, response.getTitle());
        assertEquals(RESUME, response.getResume());
        assertEquals(TEXT, response.getText());
        assertEquals(SLUG, response.getSlug());
        assertEquals(REGISTERED, response.getRegisteredAt());
        assertEquals(USERS, response.getUsers());
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
        when(repository.findAll()).thenReturn(List.of(article));

        List<Articles> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(Articles.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(uuid, response.get(INDEX).getUuid());
        assertEquals(TITLE, response.get(INDEX).getTitle());
        assertEquals(RESUME, response.get(INDEX).getResume());
        assertEquals(TEXT, response.get(INDEX).getText());
        assertEquals(SLUG, response.get(INDEX).getSlug());
        assertEquals(REGISTERED, response.get(INDEX).getRegisteredAt());
        assertEquals(USERS, response.get(INDEX).getUsers());

    }

    @Test
    void whenCreateThenReturnSucess() {
        when(repository.save(any())).thenReturn(article);

        Articles response = service.create(articleDTO);

        assertNotNull(response);
        assertEquals(Articles.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(uuid, response.getUuid());
        assertEquals(TITLE, response.getTitle());
        assertEquals(RESUME, response.getResume());
        assertEquals(TEXT, response.getText());
        assertEquals(SLUG, response.getSlug());
        assertEquals(REGISTERED, response.getRegisteredAt());
        assertEquals(USERS, response.getUsers());
    }

    @Test
    void whenCreateThenReturnDataIntegrityViolationException() {
        when(repository.findByTitle(anyString())).thenReturn(optionalArticle);

        try {
            optionalArticle.get().setId(2L);
            service.create(articleDTO);
        } catch(Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(TITULO_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }


    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(article);

        Articles response = service.update(articleDTO);

        assertNotNull(response);
        assertEquals(Articles.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(uuid, response.getUuid());
        assertEquals(TITLE, response.getTitle());
        assertEquals(RESUME, response.getResume());
        assertEquals(TEXT, response.getText());
        assertEquals(SLUG, response.getSlug());
        assertEquals(REGISTERED, response.getRegisteredAt());
        assertEquals(USERS, response.getUsers());
    }

    @Test
    void deleteWithSuccess() {
        when(repository.findById(anyLong())).thenReturn(optionalArticle);
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

    private void startArticle() {
        article = new Articles(ID, uuid, TITLE, RESUME, TEXT, SLUG, REGISTERED, USERS);
        articleDTO = new ArticlesDTO(ID, uuid, TITLE, RESUME, TEXT, SLUG, REGISTERED, USERS);
        optionalArticle = Optional.of(new Articles(ID, uuid, TITLE, RESUME, TEXT, SLUG, REGISTERED, USERS));
    }
}