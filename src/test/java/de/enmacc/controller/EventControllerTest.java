package de.enmacc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import de.enmacc.controllers.EventController;
import de.enmacc.controllers.EventControllerAdvice;
import de.enmacc.domain.Event;
import de.enmacc.services.EventService;
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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Unit Test for EventController
 *
 *  @author Cipriano Sanchez
 */

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

        when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/events")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(events.get(0).getName())))
                .andExpect(jsonPath("$[1].name", is(events.get(1).getName())));
    }

    @Test
    public void testCreateEvent() throws Exception
    {
        Event event = new Event("Event 1", "A description 1", new DateTime().plusDays(1), 90);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        String eventJson = mapper.writeValueAsString(event);

        when(eventService.createEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/events").contentType(contentType)
                .content(eventJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(event.getName())));
    }

    @Test
    public void testUpdateEvent() throws Exception
    {
        Event event = new Event("Event 1", "Modified description", new DateTime().plusDays(1), 9);
        Mockito.when(eventService.updateEvent(anyString(), any(Event.class))).thenReturn(event);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        String eventJson = mapper.writeValueAsString(event);

        mockMvc.perform(put("/events/{id}", "1234").contentType(contentType)
                .content(eventJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.name", is(event.getName())));
    }

//    @Test
//    public void testDeleteEvent() throws Exception
//    {
//        doNothing().when(eventService).deleteEvent("1234");
//
//        mockMvc.perform(delete("/events/{id}", "1234"))
//                .andExpect(status().isNoContent());
//    }

}
