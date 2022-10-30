package br.com.api.projetoaudax.controller.exceptions;

import br.com.api.projetoaudax.service.exceptions.DataIntegrityViolationException;
import br.com.api.projetoaudax.service.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerExceptionHandlerTest {

    public static final String OBJETO_NAO_ENCONTRADO = "Object Not Found";
    public static final String DATA_VIOLATION = "Data Violation";

    public static final String ENTITY_NOTFOUND = "Entity Not Found";

    public static final String LIST_ERRORS = "Error in field validations!";

    @InjectMocks
    private ControllerExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> response = exceptionHandler
                .objectNotFoundException(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO), new MockHttpServletRequest());



        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(OBJETO_NAO_ENCONTRADO, response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());

        assertNotEquals("/users/2", response.getBody().getPath());
        assertNotEquals(System.currentTimeMillis(), response.getBody().getTimestamp());
    }

    @Test
    void dataIntegrityViolationException() {
        ResponseEntity<StandardError> response = exceptionHandler
                .dataIntegrity(
                        new DataIntegrityViolationException(DATA_VIOLATION),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(DATA_VIOLATION, response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    void entityNotFound() {
        ResponseEntity<StandardError> response = exceptionHandler
                .entityNotFound(
                        new EntityNotFoundException(ENTITY_NOTFOUND),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals(ENTITY_NOTFOUND, response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
    }

}