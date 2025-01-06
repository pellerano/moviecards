// package com.lauracercas.moviecards.unittest.service;

// import com.lauracercas.moviecards.model.Actor;
// import com.lauracercas.moviecards.service.actor.ActorService;
// import com.lauracercas.moviecards.service.actor.ActorServiceImpl;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.springframework.web.client.RestTemplate;

// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.when;
// import static org.mockito.MockitoAnnotations.openMocks;

// /**
//  * Autor: Laura Cercas Ramos
//  * Proyecto: TFM Integración Continua con GitHub Actions
//  * Fecha: 04/06/2024
//  */
// class ActorServiceImplTest {

//     @Mock
//     private RestTemplate template;

//     @InjectMocks
//     private ActorService sut = new ActorServiceImpl();
//     private AutoCloseable closeable;

//     @BeforeEach
//     void setUp() {
//         closeable = openMocks(this);
//     }

//     @AfterEach
//     void tearDown() throws Exception {
//         closeable.close();
//     }

//     @Test
//     public void shouldGetAllActors() {
//         Actor actors[] = new Actor[2];
//         actors[0] = new Actor();
//         actors[1] = new Actor();

//         when(template.getForObject(anyString(), any())).thenReturn(actors);

//         List<Actor> result = sut.getAllActors();

//         assertEquals(2, result.size());
//     }

//     @Test
//     public void shouldGetActorById() {
//         Actor actor = new Actor();
//         actor.setId(1);
//         actor.setName("Sample Actor");

//         when(template.getForObject(anyString(), any())).thenReturn(actor);

//         Actor result = sut.getActorById(1);

//         assertEquals(1, result.getId());
//         assertEquals("Sample Actor", result.getName());
//     }
// }

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.List;

/**
 * Autor: Laura Cercas Ramos
 * Proyecto: TFM Integración Continua con GitHub Actions
 * Fecha: 04/06/2024
 */
class ActorServiceImplTest {

    @Mock
    private RestTemplate template;

    @InjectMocks
    private ActorService sut = new ActorServiceImpl();
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldGetAllActors() {
        Actor actors[] = new Actor[2];
        actors[0] = new Actor();
        actors[1] = new Actor();

        when(template.getForObject(anyString(), any())).thenReturn(actors);

        List<Actor> result = sut.getAllActors();

        assertEquals(2, result.size());
    }

    @Test
    public void shouldGetActorById() {
        Actor actor = new Actor();
        actor.setId(1);
        actor.setName("Sample Actor");

        when(template.getForObject(anyString(), any())).thenReturn(actor);

        Actor result = sut.getActorById(1);

        assertEquals(1, result.getId());
        assertEquals("Sample Actor", result.getName());
    }

    @Test
    public void shouldSaveActor() {
        Actor actor = new Actor();
        actor.setId(0); // Simulate a new actor with no ID

        when(template.postForObject(anyString(), any(), any())).thenReturn("Success");

        Actor result = sut.save(actor);

        assertEquals(0, result.getId()); // The actor's ID should remain 0 (since it's a new actor)
    }

    @Test
    public void shouldUpdateActor() {
        Actor actor = new Actor();
        actor.setId(1);
        doNothing().when(template).put(anyString(), any());

        Actor result = sut.save(actor);

        assertEquals(1, result.getId());
    }

    @Test
    public void shouldHandleNullActor() {
        Actor result = sut.save(null);
        // Ensure the result is null, or handle as per the business logic (you may want
        // to throw an exception)
        assertEquals(null, result);
    }

    @Test
    public void shouldHandleActorWithInvalidId() {
        Actor actor = new Actor();
        actor.setId(-1); // Simulate an invalid ID

        doNothing().when(template).put(anyString(), any());

        try {
            sut.save(actor);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid ID", e.getMessage());
        }
    }
}
