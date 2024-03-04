package osmosis.folder.inspector.container;

import java.io.File;

public class SymbolicLinkContainer extends Container {
    public SymbolicLinkContainer(File file, DirectoryContainer parent) {
        super(file, parent);
        setSize(0L);
    }

    @Override
    public String getName() {
        return "(Symbolic Link) " + super.getName();
    }
}
