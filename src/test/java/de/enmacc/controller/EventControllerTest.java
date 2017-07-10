package de.enmacc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import de.enmacc.controllers.EventController;
import de.enmacc.controllers.EventControllerAdvice;
import de.enmacc.domain.Event;
import de.enmacc.services.EventService;
import de.enmacc.services.exceptions.EventNotFoundException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class EventControllerTest
{
    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Before
    public void setup()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(eventController)
                .setControllerAdvice(new EventControllerAdvice()).build();
    }


    @Test
    public void eventNotFound() throws Exception {

        when(eventService.findById("abc")).thenThrow(new EventNotFoundException());
        mockMvc.perform(get("/events/{id}", "abc"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getEventById() throws Exception {
        Event event = new Event("Event 1", "A description 1", new DateTime(), 90);

        when(eventService.findById("1")).thenReturn(event);
        mockMvc.perform(get("/events/{id}", "1"))
                .andExpect(content().contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(event.getName())));
    }

    @Test
    public void testGettingAllEvents() throws Exception
    {

        Event event = new Event("Event 1", "A description 1", new DateTime(), 90);
        Event event2 = new Event("Event 2", "A description 2", new DateTime(), 90);
        List<Event> events = Arrays.asList(event, event2);

        Mockito.when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/events")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(events.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(events.get(1).getName())));
    }

    @Test
    public void testCreateEvent() throws Exception
    {
        Event event = new Event("Event 1", "A description 1", new DateTime().plusMonths(1), 90);
        doNothing().when(eventService).createEvent(event);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        String eventJson = mapper.writeValueAsString(event);

        mockMvc.perform(post("/events").contentType(contentType)
                .content(eventJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(event.getName())));
    }
}
