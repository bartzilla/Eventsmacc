package de.enmacc.domain;

public class ClientErrorInformation
{
    private static String MORE_INFO = "mailto:support@eventsmacc.com";

    private int code;
    private String message;
    private String moreInfo;

    public ClientErrorInformation()
    {
        this.moreInfo = MORE_INFO;
    }

    public ClientErrorInformation(int code, String message)
    {
        this();
        this.message = message;
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

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getMoreInfo()
    {
        return moreInfo;
    }
}
