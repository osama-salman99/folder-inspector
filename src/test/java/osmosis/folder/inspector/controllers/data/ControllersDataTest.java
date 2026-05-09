package osmosis.folder.inspector.controllers.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ControllersDataTest {
    @Test
    public void getInstanceReturnsSingleton() {
        assertSame(ControllersData.getInstance(), ControllersData.getInstance());
    }

    @Test
    public void mainPathRoundtrips() {
        ControllersData data = ControllersData.getInstance();

        data.setMainPath("/some/path");

        assertEquals("/some/path", data.getMainPath().orElseThrow());
    }

    @Test
    public void mainPathIsEmptyWhenUnset() {
        ControllersData data = ControllersData.getInstance();
        data.setMainPath(null);

        assertTrue(data.getMainPath().isEmpty());
    }
}
