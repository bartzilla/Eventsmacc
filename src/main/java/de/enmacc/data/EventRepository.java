package de.enmacc.data;

import de.enmacc.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface inherits all the jpa repository's basic CRUD methods for Event.
 * define custom ones for the Event system here.
 *
 * @author Cipriano Sanchez
 */

public interface EventRepository extends JpaRepository<Event, Long>
{
}
