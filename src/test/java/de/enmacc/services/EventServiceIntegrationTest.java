package de.enmacc.services;

import de.enmacc.domain.Event;
import de.enmacc.services.exceptions.EventNotFoundException;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static junit.framework.TestCase.assertTrue;


/**
 * Integration Test for EventService
 *
 *  @author Cipriano Sanchez
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EventServiceIntegrationTest
{
    @Autowired
    private EventService eventService;

    @Test(expected = EventNotFoundException.class)
    public void testFindByIdIdWithInvalidId() throws EventNotFoundException
    {
        eventService.findById("abc");
    }

    @Test
    public void testCreateEvent()
    {
        Event event = new Event("Event 1", "A description 1", new DateTime().plusDays(1), 90);
        Event savedEvent = eventService.createEvent(event);

        assertTrue(savedEvent.equals(event));
    }

    @Test
    public void testGetAllEvents()
    {
        Event event = new Event("Event 1", "A description 1", new DateTime().plusDays(1), 90);
        Event event2 = new Event("Event 2", "A description 2", new DateTime().plusDays(1), 90);

        eventService.createEvent(event);
        eventService.createEvent(event2);

        List<Event> foundEvents = eventService.getAllEvents();

        assertTrue(foundEvents.contains(event));
        assertTrue(foundEvents.contains(event2));
        assertTrue(foundEvents.size() >= foundEvents.size());

    }

    @Test(expected = EventNotFoundException.class)
    public void testFindById() throws EventNotFoundException
    {
        Event event = new Event("Event 1", "A description 1", new DateTime().plusDays(1), 90);
        eventService.createEvent(event);

        List<Event> events = eventService.getAllEvents();

        Event foundEvent = eventService.findById(String.valueOf(event.getId()));
        assertTrue(events.contains(event));

        // A none existing valid id should throw an EventNotFoundException
        eventService.findById("1234");
    }

    @Test(expected = EventNotFoundException.class)
    public void testUpdateByIdIdWithInvalidId() throws EventNotFoundException
    {
        eventService.updateEvent("abc", new Event());
    }

    @Test(expected = EventNotFoundException.class)
    public void testUpdateEvent() throws EventNotFoundException
    {
        Event event = new Event("Update event", "A description for update", new DateTime().plusDays(1), 90);
        Event updateEvent = eventService.createEvent(event);
        updateEvent.setDescription("Modified update event");
        eventService.updateEvent(String.valueOf(updateEvent.getId()), updateEvent);

        assertTrue(updateEvent.getId().equals(event.getId()));

        // A none existing valid id should throw an EventNotFoundException
        eventService.updateEvent("1234", event);
    }

    @Test(expected = EventNotFoundException.class)
    public void testDeleteByIdIdWithInvalidId() throws EventNotFoundException
    {
        eventService.deleteEvent("abc");
    }

    @Test(expected = EventNotFoundException.class)
    public void testDeleteEvent() throws EventNotFoundException
    {
        Event event = new Event("Let's delete !", "A description for object to be deleted", new DateTime().plusDays(1), 90);
        List<Event> events = eventService.getAllEvents();

        assertTrue(!events.contains(event));

        eventService.createEvent(event);
        events = eventService.getAllEvents();
        assertTrue(events.contains(event));

        eventService.deleteEvent(String.valueOf(event.getId()));
        events = eventService.getAllEvents();
        assertTrue(!events.contains(event));

        // A none existing valid id should throw an EventNotFoundException
        eventService.deleteEvent("1234");
    }

    @Test(expected = ConstraintViolationException.class)
    public void testValidationDateInFuture()
    {
        Event event = new Event("Event 1", "A description 1", new DateTime(), 90);
        Event savedEvent = eventService.createEvent(event);

        assertTrue(savedEvent.equals(event));
    }

}