package com.lauracercas.moviecards.unittest.service;

import com.lauracercas.moviecards.model.Actor;
import com.lauracercas.moviecards.service.actor.ActorService;
import com.lauracercas.moviecards.service.actor.ActorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.openMocks;

public class ActorServiceImplTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ActorService sut = new ActorServiceImpl();
    private AutoCloseable closeable;

    private Actor actor;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
        actor = new Actor();
        actor.setId(1);
        actor.setName("John Doe");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void testGetAllActors() {
        Actor[] actorsArray = { actor };
        when(restTemplate.getForObject(anyString(), eq(Actor[].class))).thenReturn(actorsArray);
        List<Actor> result = sut.getAllActors();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    public void testSaveNewActor() {
        when(restTemplate.postForObject(anyString(), eq(actor), eq(String.class))).thenReturn("Actor Created");
        Actor result = sut.save(actor);
        assertNotNull(result);
        assertEquals(0, result.getId());
        verify(restTemplate, times(1)).postForObject(anyString(), eq(actor), eq(String.class));
    }

    @Test
    public void testSaveExistingActor() {
        actor.setId(1);
        doNothing().when(restTemplate).put(anyString(), eq(actor));
        Actor result = sut.save(actor);
        assertNotNull(result);
        assertEquals(1, result.getId());
        verify(restTemplate, times(1)).put(anyString(), eq(actor));
    }

    @Test
    public void testGetActorById() {
        when(restTemplate.getForObject(anyString(), eq(Actor.class))).thenReturn(actor);
        Actor result = sut.getActorById(1);
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
    }
}

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
// class ActorServiceImplTest {

// @Mock
// private RestTemplate template;

// @InjectMocks
// private ActorService sut = new ActorServiceImpl();
// private AutoCloseable closeable;

// @BeforeEach
// void setUp() {
// closeable = openMocks(this);
// }

// @AfterEach
// void tearDown() throws Exception {
// closeable.close();
// }

// @Test
// public void shouldGetAllActors() {
// Actor actors[] = new Actor[2];
// actors[0] = new Actor();
// actors[1] = new Actor();

// when(template.getForObject(anyString(), any())).thenReturn(actors);

// List<Actor> result = sut.getAllActors();

// assertEquals(2, result.size());
// }

// @Test
// public void shouldGetActorById() {
// Actor actor = new Actor();
// actor.setId(1);
// actor.setName("Sample Actor");

// when(template.getForObject(anyString(), any())).thenReturn(actor);

// Actor result = sut.getActorById(1);

// assertEquals(1, result.getId());
// assertEquals("Sample Actor", result.getName());
// }

// @Test
// public void shouldSaveNewActor() {
// Actor actor = new Actor();
// actor.setId(0);
// when(template.postForObject(anyString(), any(),
// any())).thenReturn("Success");
// Actor result = sut.save(actor);
// verify(template).postForObject(anyString(), any(), any());
// assertEquals(0, result.getId());
// }

// @Test
// public void shouldUpdateExistingActor() {
// Actor actor = new Actor();
// actor.setId(1);
// doNothing().when(template).put(anyString(), any());
// Actor result = sut.save(actor);
// verify(template).put(anyString(), any());
// assertEquals(1, result.getId());
// }
// }
