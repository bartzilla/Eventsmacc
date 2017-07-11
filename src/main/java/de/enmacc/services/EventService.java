package de.enmacc.services;

import de.enmacc.domain.Event;
import de.enmacc.services.exceptions.EventNotFoundException;

import java.util.List;

/**
 * This interface represents the contract to follow for all classes that
 * want to implement functionality related to Event Service.
 *
 * @author Cipriano Sanchez
 */

public interface EventService
{
    /**
     * Returns a particular <code>Event</code> for a given <code>Event</code>'s id.
     *
     * @param id The id the <code>Event</code> to be retrieved.
     * @return <code>Event</code> object if found.
     * @throws EventNotFoundException if there are no <code>Event</code>s with the given <code>id</code>
     * or if <code>id</code> is a valid format.
     */
    Event findById(String id) throws EventNotFoundException;

    /**
     * Update a particular <code>Event</code> for a given <code>Event</code>'s id.
     *
     * @param id The id the <code>Event</code> to be updated.
     * @return <code>Event</code> object which was updated.
     * @throws EventNotFoundException if there are no <code>Event</code>s with the given <code>id</code>
     * or if <code>id</code> is a valid format.
     */
    Event updateEvent(String id, Event event) throws EventNotFoundException;

    /**
     * Delete <code>Event</code> with given <code>Event</code>'s id.
     *
     * @param id The id the <code>Event</code> to be deleted.
     * @throws EventNotFoundException if there are no <code>Event</code>s with the given <code>id</code>
     * or if <code>id</code> is a valid format.
     */
    void deleteEvent(String id) throws EventNotFoundException;

    /**
     * Create an <code>Event</code>
     *
     * @param event The <code>Event</code> object to be created.
     * @return <code>Event</code> if success the new created object is returned.
     */
    Event createEvent(Event event);

    /**
     * Get a list of available <code>Event</code>'s in the system.
     *
     * @return <code>List</code> of available <code>Event</code>'s.
     */
    List<Event> getAllEvents();
}
