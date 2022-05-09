package spring.exception;

public class RequestStatusNotFound extends RuntimeException {
    public RequestStatusNotFound(String message)
    {
        super(message);
    }
}
