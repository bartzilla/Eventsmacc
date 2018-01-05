package de.enmacc.services.impl;

import de.enmacc.data.UserRepository;
import de.enmacc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * This class represents a concrete implementation of the {@link UserDetailsService} interface.
 *
 *  @author Cipriano Sanchez
 */

@Component
public class DetailsService implements UserDetailsService
{
    @Autowired
    UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String username)
    {
        User user = users.findByUsername(username);

        if (user == null)
        {
            throw new UsernameNotFoundException(username + " was not found.");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles()));
    }
}
