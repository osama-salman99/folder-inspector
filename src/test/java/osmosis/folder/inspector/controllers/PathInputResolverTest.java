package osmosis.folder.inspector.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import osmosis.folder.inspector.constants.ErrorMessages;
import osmosis.folder.inspector.exceptions.InvalidDirectoryException;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PathInputResolverTest {
    @Test
    public void resolvesValidDirectoryPath() throws Exception {
        File expected = TestUtils.getInstance().getFile("folder1");

        File resolved = PathInputResolver.resolveDirectory(expected.getAbsolutePath());

        assertEquals(expected.getAbsolutePath(), resolved.getAbsolutePath());
    }

    @Test
    public void blankInputThrowsPathCannotBeEmpty() {
        InvalidDirectoryException exception = assertThrows(
                InvalidDirectoryException.class,
                () -> PathInputResolver.resolveDirectory("   ")
        );
        assertEquals(ErrorMessages.PATH_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    public void emptyInputThrowsPathCannotBeEmpty() {
        InvalidDirectoryException exception = assertThrows(
                InvalidDirectoryException.class,
                () -> PathInputResolver.resolveDirectory("")
        );
        assertEquals(ErrorMessages.PATH_CANNOT_BE_EMPTY, exception.getMessage());
    }

    @Test
    public void nonExistingPathThrowsDirectoryDoesNotExist() {
        InvalidDirectoryException exception = assertThrows(
                InvalidDirectoryException.class,
                () -> PathInputResolver.resolveDirectory("/this/path/should/never/exist/xyzzy")
        );
        assertEquals(ErrorMessages.DIRECTORY_DOES_NOT_EXIST, exception.getMessage());
    }

    @Test
    public void filePathThrowsPathDoesNotPointToDirectory(@TempDir Path tempDir) throws Exception {
        Path file = Files.createFile(tempDir.resolve("file.txt"));

        InvalidDirectoryException exception = assertThrows(
                InvalidDirectoryException.class,
                () -> PathInputResolver.resolveDirectory(file.toAbsolutePath().toString())
        );
        assertEquals(ErrorMessages.PATH_DOES_NOT_POINT_TO_A_DIRECTORY, exception.getMessage());
    }

    @Test
    public void appendsFileSeparatorAfterDriveLetter() {
        InvalidDirectoryException exception = assertThrows(
                InvalidDirectoryException.class,
                () -> PathInputResolver.resolveDirectory("Z:")
        );
        assertEquals(ErrorMessages.DIRECTORY_DOES_NOT_EXIST, exception.getMessage());
    }
}
