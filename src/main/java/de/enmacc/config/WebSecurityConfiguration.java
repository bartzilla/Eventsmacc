package de.enmacc.config;

import de.enmacc.domain.User;
import de.enmacc.services.impl.DetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *  Configuration to provide basic authentication. Please be aware this is not production ready.
 *  It is just a sample. Further improvements need to be done. e.g auth2 with jwt's
 *
 *  @author Cipriano Sanchez
 */

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    DetailsService userDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic().and().csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailService).passwordEncoder(User.PASSWORD_ENCODER);
    }
}
