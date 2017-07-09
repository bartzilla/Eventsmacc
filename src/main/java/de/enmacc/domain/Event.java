package de.enmacc.domain;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Event
{
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull @Future
    private DateTime startTime;

    private Integer duration;

    public Event(){}

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public DateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(DateTime startTime)
    {
        this.startTime = startTime;
    }

    public Integer getDuration()
    {
        return duration;
    }

    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }
}
