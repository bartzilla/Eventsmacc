package de.enmacc.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by ciprianosanchez on 7/10/17.
 */
@Entity
public class User
{
    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @NotNull
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String[] roles;

    protected User()
    {
        super();
    }

    public User(String username, String password, String[] roles)
    {
        this();
        this.username = username;
        setPassword(password);
        this.roles = roles;
    }

    public void setPassword(String password)
    {
        this.password = PASSWORD_ENCODER.encode(password);
    }

    public static PasswordEncoder getPasswordEncoder()
    {
        return PASSWORD_ENCODER;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public String[] getRoles()
    {
        return roles;
    }

    public void setRoles(String[] roles)
    {
        this.roles = roles;
    }
}
