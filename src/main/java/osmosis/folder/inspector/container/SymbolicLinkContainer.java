package osmosis.folder.inspector.container;

import java.io.File;

public class SymbolicLinkContainer extends Container {
    public SymbolicLinkContainer(File file, DirectoryContainer parent) {
        super(file, parent);
        this.size = file.length();
    }

    @Override
    public String getName() {
        return super.getName() + " (Symbolic Link)";
    }
}
