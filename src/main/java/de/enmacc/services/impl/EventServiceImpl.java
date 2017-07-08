package de.enmacc.services.impl;

import de.enmacc.data.EventRepository;
import de.enmacc.domain.Event;
import de.enmacc.services.EventService;
import de.enmacc.services.exceptions.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventServiceImpl implements EventService
{

    @Autowired
    private EventRepository data;

    @Override
    public void createEvent(Event event)
    {
        data.save(event);
    }

    @Override
    public List<Event> getAllEvents()
    {
        return data.findAll();
    }

    @Override
    public Event findById(String id) throws EventNotFoundException
    {
        if (!data.exists(id))
        {
            throw new EventNotFoundException();
        }

        return data.findOne(id);
    }

    @Override
    public Event updateEvent(String id, Event event) throws EventNotFoundException
    {
        if(!data.exists(id))
        {
            throw new EventNotFoundException();
        }

        event.setId(id);

        return data.save(event);
    }

    @Override
    public void deleteEvent(String id) throws EventNotFoundException
    {
        if(!data.exists(id))
        {
            throw new EventNotFoundException();
        }

        data.delete(id);
    }

}
