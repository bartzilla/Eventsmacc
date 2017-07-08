package de.enmacc.services.exceptions;

/**
 * This class represents a generic unchecked exception. It is thrown whenever a resource has not been found.
 *
 * @author Cipriano Sanchez
 */
public class EventNotFoundException extends Exception
{
    private static final String MESSAGE = "The requested resource does not exist.";

    /**
     * Constructs a <code>EventNotFoundException</code> with a default MESSAGE.
     */
    public EventNotFoundException()
    {
        this(MESSAGE);
    }

    /**
     * Constructs a <code>EventNotFoundException</code> with the given detail message.
     *
     * @param message The detail message of the <code>EventNotFoundException</code>.
     */
    public EventNotFoundException(final String message)
    {
        super(message);
    }

    /**
     * Constructs a <code>EventNotFoundException</code> with the given detail message and root cause.
     *
     * @param message The detail message of the <code>EventNotFoundException</code>.
     * @param cause   The root cause of the <code>EventNotFoundException</code>.
     */
    public EventNotFoundException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

}
