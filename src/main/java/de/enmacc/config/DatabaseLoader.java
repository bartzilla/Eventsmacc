package de.enmacc.config;

import de.enmacc.data.EventRepository;
import de.enmacc.domain.Event;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *  Configuration to provide some sample data.
 *
 *  @author Cipriano Sanchez
 */

@Component
public class DatabaseLoader implements ApplicationRunner
{
    private final EventRepository events;
//    private final UserRepository users;

    @Autowired
    public DatabaseLoader(EventRepository events) //, UserRepository users)
    {
//        this.users = users;
        this.events = events;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        Event event = new Event("Wolf Biermann und das Zentral Quartett - Wolf Biermann & das Zentral Quartett",
                "Mit der Ankündigung der Sommer-Tour schließt der Brite nahtlos an seinen gefeierten Auftritt beim...",
                new DateTime().plusMonths(1), 90);

        events.save(event);

//        String[] roles = {"ADMIN"};
//        User user = new User("test", "1234", roles);

//        users.save(user);
    }
}
