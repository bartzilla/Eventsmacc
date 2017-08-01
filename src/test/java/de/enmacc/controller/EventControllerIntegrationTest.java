package de.enmacc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import de.enmacc.controllers.EventController;
import de.enmacc.domain.Event;
import de.enmacc.services.exceptions.EventNotFoundException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.nio.charset.Charset;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Integration Test for EventController
 *
 *  @author Cipriano Sanchez
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class EventControllerIntegrationTest
{
    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Autowired
    private EventController eventController;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() throws Exception
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test//(expected = AuthenticationCredentialsNotFoundException.class)
    public void getMessageUnauthenticated() {
        eventController.getAllEvents();
    }

    @Test
//    @WithMockUser
    public void eventNotFound() throws Exception {

        mockMvc.perform(get("/events/{id}", "abc"))
                .andExpect(status().isNotFound());

        Event event = new Event("Event 1", "A description 1", new DateTime().plusMonths(1), 90);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        String eventJson = mapper.writeValueAsString(event);

        mockMvc.perform(put("/events/{id}", "abc").contentType(contentType)
                .content(eventJson))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/events/{id}", "abc").contentType(contentType)
                .content(eventJson))
                .andExpect(status().isNotFound());
    }

    @Test
//    @WithMockUser
    public void testCreateEvent() throws Exception
    {
        Event event = new Event("Event 1", "A description 1", new DateTime().plusMonths(1), 90);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        String eventJson = mapper.writeValueAsString(event);

        mockMvc.perform(post("/events").contentType(contentType)
                .content(eventJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(event.getName())));
    }

    @Test
//    @WithMockUser
    public void testGetAllEvents() throws Exception
    {
        this.testCreateEvent();

        mockMvc.perform(get("/events").contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
//    @WithMockUser
    public void testUpdateEvent() throws Exception
    {
        this.testCreateEvent();

        final Event eventToUpdate = eventController.getAllEvents().get(0);
        eventToUpdate.setDescription("Modified Event");

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        String eventToUpdateAsJson = mapper.writeValueAsString(eventToUpdate);

        mockMvc.perform(put("/events/{id}", eventToUpdate.getId()).contentType(contentType)
                .content(eventToUpdateAsJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.description", is(eventToUpdate.getDescription())));
    }

//    @WithMockUser
    @Test(expected = EventNotFoundException.class)
    public void testDeleteEvent() throws Exception
    {
        this.testCreateEvent();

        final Event eventToDelete = eventController.getAllEvents().get(0);
        String id = String.valueOf(eventToDelete.getId());

        mockMvc.perform(delete("/events/{id}", id))
                .andExpect(status().isNoContent());

        Event event = eventController.findById(id);

    }

}
