package osmosis.folder.inspector.calculation;

import osmosis.folder.inspector.container.Container;

public class AsyncCalculation implements CalculationMethod {
    @Override
    public FutureWrapper<Long> calculateSize(Container container) {
        return FutureWrapper.of(ThreadManager.getInstance().execute(container::calculateSize));
    }
}
