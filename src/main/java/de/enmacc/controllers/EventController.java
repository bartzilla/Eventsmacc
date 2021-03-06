package de.enmacc.controllers;

import de.enmacc.domain.Event;
import de.enmacc.services.EventService;
import de.enmacc.services.exceptions.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller in charge of routing Event endpoints.
 *
 *  @author Cipriano Sanchez
 */

@RestController
public class EventController
{
    @Autowired
    EventService eventService;

    @PreAuthorize("authenticated")
    @RequestMapping("/events")
    public List<Event> getAllEvents()
    {
        return eventService.getAllEvents();
    }

    @PreAuthorize("authenticated")
    @RequestMapping(value = "/events/{id}", method = RequestMethod.GET)
    public Event findById(@PathVariable("id") String id) throws EventNotFoundException
    {
        return eventService.findById(id);
    }

    @PreAuthorize("authenticated")
    @RequestMapping(value = "/events", method = RequestMethod.POST)
    public ResponseEntity<Event> createEvent(@RequestBody @Valid Event event)
    {
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PreAuthorize("authenticated")
    @RequestMapping(value = "/events/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Event> updateEvent(@RequestBody @Valid Event event, @PathVariable String id)throws EventNotFoundException
    {
        Event updatedEvent = eventService.updateEvent(id, event);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    @RequestMapping(value = "/events/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT) @PreAuthorize("authenticated")
    public void deleteEvent(@PathVariable String id) throws EventNotFoundException
    {
        eventService.deleteEvent(id);
    }

}
