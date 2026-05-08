package osmosis.folder.inspector.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InvalidDirectoryExceptionTest {
    @Test
    public void exceptionExposesProvidedMessage() {
        InvalidDirectoryException exception = new InvalidDirectoryException("not a directory");

        assertEquals("not a directory", exception.getMessage());
    }

    @Test
    public void exceptionCanBeThrownAndCaught() {
        InvalidDirectoryException thrown = assertThrows(
                InvalidDirectoryException.class,
                () -> {
                    throw new InvalidDirectoryException("bad");
                }
        );

        assertEquals("bad", thrown.getMessage());
    }
}
