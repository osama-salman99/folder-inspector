package osmosis.folder.inspector.controllers;

import osmosis.folder.inspector.constants.Constant;
import osmosis.folder.inspector.constants.ErrorMessages;
import osmosis.folder.inspector.exceptions.InvalidDirectoryException;

import java.io.File;

public final class PathInputResolver {
    private PathInputResolver() {
    }

    public static File resolveDirectory(String input) throws InvalidDirectoryException {
        String path = appendSeparatorAfterDriveLetter(input);
        validatePath(path);
        File file = new File(path);
        validateFile(file);
        return file;
    }

    private static String appendSeparatorAfterDriveLetter(String path) {
        if (path.endsWith(Constant.COLON)) {
            return path + Constant.FILE_SEPARATOR;
        }
        return path;
    }

    private static void validatePath(String path) throws InvalidDirectoryException {
        if (path.isBlank()) {
            throw new InvalidDirectoryException(ErrorMessages.PATH_CANNOT_BE_EMPTY);
        }
    }

    private static void validateFile(File file) throws InvalidDirectoryException {
        if (!file.exists()) {
            throw new InvalidDirectoryException(ErrorMessages.DIRECTORY_DOES_NOT_EXIST);
        }
        if (!file.isDirectory()) {
            throw new InvalidDirectoryException(ErrorMessages.PATH_DOES_NOT_POINT_TO_A_DIRECTORY);
        }
    }
}
