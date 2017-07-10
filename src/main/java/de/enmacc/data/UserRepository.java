package de.enmacc.data;

import de.enmacc.domain.Event;
import de.enmacc.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);
}
