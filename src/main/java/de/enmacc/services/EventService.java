package de.enmacc.services;

import de.enmacc.domain.Event;

import java.util.List;

public interface EventService
{
    void createEvent(Event event);

    List<Event> getAllEvents();

    Event findById(Long id);

    Event updateEvent(Long id, Event event);

    void deleteEvent(Long id);
}
