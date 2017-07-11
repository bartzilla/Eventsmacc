package de.enmacc.services.impl;

import de.enmacc.data.EventRepository;
import de.enmacc.domain.Event;
import de.enmacc.services.EventService;
import de.enmacc.services.exceptions.EventNotFoundException;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class represents a concrete implementation of the {@link EventService} interface.
 *
 *  @author Cipriano Sanchez
 */

@Service
public class EventServiceImpl implements EventService
{
    @Autowired
    private EventRepository data;

    @Override
    public Event createEvent(Event event)
    {
        return data.save(event);
    }

    @Override
    public List<Event> getAllEvents()
    {
        return data.findAll();
    }

    @Override
    public Event findById(String id) throws EventNotFoundException
    {
        if (!NumberUtils.isNumber(id) || !data.exists(Long.parseLong(id)))
        {
            throw new EventNotFoundException();
        }

        return data.findOne(Long.parseLong(id));
    }

    @Override
    public Event updateEvent(String id, Event event) throws EventNotFoundException
    {
        if (!NumberUtils.isNumber(id) || !data.exists(Long.parseLong(id)))
        {
            throw new EventNotFoundException();
        }

        event.setId(Long.valueOf(id));

        return data.save(event);
    }

    @Override
    public void deleteEvent(String id) throws EventNotFoundException
    {
        if (!NumberUtils.isNumber(id) || !data.exists(Long.parseLong(id)))
        {
            throw new EventNotFoundException();
        }

        data.delete(Long.parseLong(id));
    }

}
