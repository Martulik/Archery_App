package spring.exception;

public class InvalidJwtAutenticationException extends RuntimeException {
    public InvalidJwtAutenticationException(String message){
        super(message);
    }
}
