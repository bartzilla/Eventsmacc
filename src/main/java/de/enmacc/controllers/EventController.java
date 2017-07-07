package de.enmacc.controllers;

import de.enmacc.domain.Event;
import de.enmacc.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController
{
    @Autowired
    EventService eventService;

    @RequestMapping("/events")
    public List<Event> getAllEvents()
    {
        return eventService.getAllEvents();
    }

    @RequestMapping("/events/{id}")
    public Event findById(@PathVariable("id") Long id)
    {
        return eventService.findById(id);
    }

    @RequestMapping(value = "/events", method = RequestMethod.POST)
    public ResponseEntity<Event> createEvent(@RequestBody Event event)
    {
        eventService.createEvent(event);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/events/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @PathVariable Long id)
    {
        Event updatedEvent = eventService.updateEvent(id, event);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    @RequestMapping(value = "/events/{id}", method = RequestMethod.DELETE)
    public void deleteEvent(@PathVariable Long id)
    {
        eventService.deleteEvent(id);
    }

}
