package osmosis.folder.inspector.container;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ContainerManagerTest {
    @AfterEach
    public void resetState() {
        ContainerManager.getInstance().clearContainer();
    }

    @Test
    public void getInstanceReturnsSingleton() {
        assertSame(ContainerManager.getInstance(), ContainerManager.getInstance());
    }

    @Test
    public void initialCurrentContainerIsNull() {
        assertNull(ContainerManager.getInstance().getCurrentContainer());
    }

    @Test
    public void setCurrentContainerStoresContainer() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);

        ContainerManager.getInstance().setCurrentContainer(container);

        assertSame(container, ContainerManager.getInstance().getCurrentContainer());
    }

    @Test
    public void clearContainerResetsToNull() {
        File file = TestUtils.getInstance().getFile("folder1");
        DirectoryContainer container = ContainerFactory.createDirectoryContainer(file);
        ContainerManager.getInstance().setCurrentContainer(container);

        ContainerManager.getInstance().clearContainer();

        assertNull(ContainerManager.getInstance().getCurrentContainer());
    }
}
