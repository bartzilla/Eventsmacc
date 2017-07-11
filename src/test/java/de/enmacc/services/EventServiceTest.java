package de.enmacc.services;

import de.enmacc.data.EventRepository;
import de.enmacc.domain.Event;
import de.enmacc.services.exceptions.EventNotFoundException;
import de.enmacc.services.impl.EventServiceImpl;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * Unit Test for EventService
 *
 *  @author Cipriano Sanchez
 */

@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest
{
    @Mock
    private EventRepository data;

    @InjectMocks
    private EventServiceImpl eventService;


    @Test(expected = EventNotFoundException.class)
    public void testFindByIdIdWithInvalidId() throws EventNotFoundException
    {
        eventService.findById("abc");
    }

    @Test
    public void testCreateEvent()
    {
        Event event = new Event("Event 1", "A description 1", new DateTime(), 90);
        Mockito.when(data.save(event)).thenReturn(event);
        Event savedEvent = eventService.createEvent(event);

        assertTrue(savedEvent.equals(event));
    }

    @Test
    public void testGetAllEvents()
    {
        Event event = new Event("Event 1", "A description 1", new DateTime(), 90);
        Event event2 = new Event("Event 2", "A description 2", new DateTime(), 90);
        List<Event> events = Arrays.asList(event, event2);

        Mockito.when(data.findAll()).thenReturn(events);

        List<Event> foundEvents = eventService.getAllEvents();

        assertTrue(foundEvents.contains(event));
        assertTrue(foundEvents.contains(event2));
    }


    @Test(expected = EventNotFoundException.class)
    public void testFindById() throws EventNotFoundException
    {
        Event event = new Event("Event 1", "A description 1", new DateTime(), 90);

        Mockito.when(data.exists(Long.parseLong("1"))).thenReturn(true);
        Mockito.when(data.findOne(Long.parseLong("1"))).thenReturn(event);

        Event foundEvent = eventService.findById("1");
        assertTrue(foundEvent.equals(event));

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
        Event event = new Event("Modified!", "A description 1", new DateTime(), 90);

        Mockito.when(data.exists(Long.parseLong("1"))).thenReturn(true);

        Event  mockEvent = mock(Event.class);
        Mockito.when(data.save(mockEvent)).thenReturn(event);

        doAnswer(new Answer<Void>()
        {
            public Void answer(InvocationOnMock invocation)
            {
                event.setId(1L);
                return null;
            }
        }).when(mockEvent).setId(anyLong());

        eventService.updateEvent("1", mockEvent);

        assertTrue(event.getId().equals(1L));
        assertTrue(event.equals(event));

        // A none existing valid id should throw an EventNotFoundException
        eventService.updateEvent("1234", mockEvent);
    }

    @Test(expected = EventNotFoundException.class)
    public void testDeleteByIdIdWithInvalidId() throws EventNotFoundException
    {
        eventService.deleteEvent("abc");
    }

    @Test(expected = EventNotFoundException.class)
    public void testDeleteEvent() throws EventNotFoundException
    {
        Event event = new Event("Let's delete !", "A description 1", new DateTime(), 90);
        List<Event> events = new ArrayList<>();
        events.add(event);

        Mockito.when(data.exists(Long.parseLong("1"))).thenReturn(true);

        doAnswer(new Answer<Void>()
        {
            public Void answer(InvocationOnMock invocation)
            {
                events.remove(event);
                return null;
            }
        }).when(data).delete(anyLong());

        eventService.deleteEvent("1");

        assertTrue(events.isEmpty());
        // A none existing valid id should throw an EventNotFoundException
        eventService.updateEvent("1234", event);
    }

}
