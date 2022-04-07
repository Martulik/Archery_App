package spring.exception;

public class InvalidEnterValueException extends RuntimeException{
    public InvalidEnterValueException(String message) {
        super(message);
    }
}
