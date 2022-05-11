package spring.exception;

public class RequestNotFound extends RuntimeException {
    public RequestNotFound(String message)
    {
        super(message);
    }
}
