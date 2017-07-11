package de.enmacc.domain;

import java.util.Collections;
import java.util.List;

/**
 * Concrete class to represent Error objects to be sent as custom responses.
 *
 *  @author Cipriano Sanchez
 */

public class Error
{
    private static String MORE_INFO = "mailto:support@eventsmacc.com";
    private int code;
    private List<String> errors;
    private String moreInfo;

    public Error()
    {
        this.moreInfo = MORE_INFO;
    }

    public Error(int code, String error)
    {
        this(code, Collections.singletonList(error));
    }

    public Error(int code, List<String> errors)
    {
        this();
        this.errors = errors;
        this.code = code;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMoreInfo()
    {
        return moreInfo;
    }

    public List<String> getErrors()
    {
        return errors;
    }

    public void setErrors(List<String> errors)
    {
        this.errors = errors;
    }
}
