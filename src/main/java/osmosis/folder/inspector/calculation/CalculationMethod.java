package osmosis.folder.inspector.calculation;

import osmosis.folder.inspector.container.Container;

import java.util.concurrent.Future;

@FunctionalInterface
public interface CalculationMethod {
    FutureWrapper<Long> calculateSize(Container container);
}
