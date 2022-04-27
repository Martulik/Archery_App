package spring.exception;

public class StartAfterEndException extends RuntimeException
{
    public StartAfterEndException(String message)
    {
        super(message);
    }
}
