package de.enmacc.domain;

import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

/**
 * Concrete class to represent Event objects.
 *
 *  @author Cipriano Sanchez
 */

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
    @NotNull
    private Integer duration;

    public Event(){}

    public Event(String name, String description, DateTime startTime, Integer duration)
    {
        this.id = null;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.duration = duration;
    }

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event event = (Event) o;

        return id != null ? id.equals(event.id) : event.id == null;
    }

    @Override
    public int hashCode()
    {
        return id != null ? id.hashCode() : 0;
    }
}
