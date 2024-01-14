package osmosis.folder.inspector.calculation;

import osmosis.folder.inspector.constants.Constant;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.Callable;

public class DirectorySizeCalculator implements Callable<Long> {
    private final File file;

    public DirectorySizeCalculator(File file) {
        this.file = file;
    }

    @Override
    public Long call() {
        // TODO: Receive and return list of containers? Hmmmmm
        File[] files = Optional.ofNullable(file.listFiles()).orElse(Constant.EMPTY_FILES_ARRAY);
        if (files.length == 0) {
            return 0L;
        }

        return null;
    }
}
