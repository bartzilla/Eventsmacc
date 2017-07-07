package de.enmacc.services.impl;

import de.enmacc.data.EventRepository;
import de.enmacc.domain.Event;
import de.enmacc.services.EventService;
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
    public Event findById(Long id)
    {
        return data.findOne(id);
    }

    @Override
    public Event updateEvent(Long id, Event event)
    {
        return data.save(event);
    }

    @Override
    public void deleteEvent(Long id)
    {
        data.delete(id);
    }


}
