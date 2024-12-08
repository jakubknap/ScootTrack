package pl.scoottrack.handler;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}