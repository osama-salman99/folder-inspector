package osmosis.folder.inspector.container;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import osmosis.folder.inspector.test.TestUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void setRootContainerStoresContainerForFile() {
        File file = TestUtils.getInstance().getFile("folder1");

        ContainerManager.getInstance().setRootContainer(file);

        DirectoryContainer current = ContainerManager.getInstance().getCurrentContainer();
        assertEquals(file.getAbsolutePath(), current.getPath());
    }

    @Test
    public void navigateToReplacesCurrentContainer() {
        File rootFile = TestUtils.getInstance().getFile("folder1");
        ContainerManager.getInstance().setRootContainer(rootFile);
        DirectoryContainer child = ContainerFactory.createDirectoryContainer(
                TestUtils.getInstance().getFile("folder1/folder2"));

        ContainerManager.getInstance().navigateTo(child, () -> { });

        assertSame(child, ContainerManager.getInstance().getCurrentContainer());
    }

    @Test
    public void clearContainerResetsToNull() {
        ContainerManager.getInstance().setRootContainer(TestUtils.getInstance().getFile("folder1"));

        ContainerManager.getInstance().clearContainer();

        assertNull(ContainerManager.getInstance().getCurrentContainer());
    }
}
