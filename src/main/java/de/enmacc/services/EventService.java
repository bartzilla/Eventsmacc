package de.enmacc.services;

import de.enmacc.domain.Event;
import de.enmacc.services.exceptions.EventNotFoundException;
import java.util.List;

public interface EventService
{
    void createEvent(Event event);

    List<Event> getAllEvents();

    Event findById(String id) throws EventNotFoundException;

    Event updateEvent(String id, Event event) throws EventNotFoundException;

    void deleteEvent(String id) throws EventNotFoundException;
}
