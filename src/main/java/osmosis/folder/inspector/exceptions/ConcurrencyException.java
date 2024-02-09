package osmosis.folder.inspector.exceptions;

public class ConcurrencyException extends RuntimeException {
    public ConcurrencyException(Throwable cause) {
        super(cause);
    }
}
