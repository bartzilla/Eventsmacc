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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
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
    public void setup()
    {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();

    }

    @Test
    public void eventNotFound() throws Exception {

        mockMvc.perform(get("/events/{id}", "abc"))
                .andExpect(status().isNotFound());
    }

    @Test
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
    public void testUpdateEvent() throws Exception
    {
        Event event = new Event("Event 1", "A description which has been modified for event 1", new DateTime().plusMonths(1), 90);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        String eventJson = mapper.writeValueAsString(event);

        mockMvc.perform(post("/events").contentType(contentType)
                .content(eventJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.description", is(event.getDescription())));
    }

}
