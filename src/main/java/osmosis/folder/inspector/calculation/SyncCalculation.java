package osmosis.folder.inspector.calculation;

import osmosis.folder.inspector.container.Container;

import java.util.concurrent.CompletableFuture;

public class SyncCalculation implements CalculationMethod {

    @Override
    public FutureWrapper<Long> calculateSize(Container container) {
        return FutureWrapper.of(CompletableFuture.completedFuture(container.calculateSize()));
    }
}
