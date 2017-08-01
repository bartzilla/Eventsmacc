package de.enmacc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class SecurityConfiguration
{
    @Configuration
    @EnableAuthorizationServer
    public static class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
    {
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception
        {
            clients.inMemory().withClient("trusted")
                    .authorizedGrantTypes("client_credentials")
                    .authorities("TRUSTED")
                    .scopes("trusted")
                    .secret("trustedsecret");
        }
    }

    @Configuration
    @EnableResourceServer
    public static class ResourceServerConfig extends ResourceServerConfigurerAdapter
    {
        @Override
        public void configure(HttpSecurity http) throws Exception
        {
            http.antMatcher("/**").authorizeRequests()
                    .antMatchers(HttpMethod.POST,"/events").access("#oauth2.hasScope('write') or #oauth2.hasScope('trusted')")
                    .antMatchers("/**").access("#oauth2.hasScope('read') or #oauth2.hasScope('write') or #oauth2.hasScope('trusted')")

                    .and()
                    .csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }
}
