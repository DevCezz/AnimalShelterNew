package pl.devcezz.tests;

public class FailTestException extends RuntimeException {
    public FailTestException(final String message) {
        super(message);
    }
}
