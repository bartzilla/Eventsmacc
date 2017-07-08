package de.enmacc.data;

import de.enmacc.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ciprianosanchez on 7/7/17.
 */
public interface EventRepository extends JpaRepository<Event, String>
{
}
