package osmosis.folder.inspector.formatter;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DigitalFormatterTest {
    @ParameterizedTest
    @MethodSource("sizeFormattingProvider")
    public void formatsSizeWithThousandsSeparatorAndUnit(long size, String expected) {
        assertEquals(expected, DigitalFormatter.formatSize(size));
    }

    public static Stream<Arguments> sizeFormattingProvider() {
        return Stream.of(
                Arguments.of(0L, "0 (0B)"),
                Arguments.of(1L, "1 (1B)"),
                Arguments.of(1023L, "1,023 (1023B)"),
                Arguments.of(1024L, "1,024 (1KB)"),
                Arguments.of(1_048_575L, "1,048,575 (1023KB)"),
                Arguments.of(1_048_576L, "1,048,576 (1MB)"),
                Arguments.of(43_278_949L, "43,278,949 (41MB)"),
                Arguments.of(1_073_741_824L, "1,073,741,824 (1GB)"),
                Arguments.of(675_894_025_894L, "675,894,025,894 (629GB)"),
                Arguments.of(1_099_511_627_776L, "1,099,511,627,776 (1TB)"),
                Arguments.of(72_389_479_823_472L, "72,389,479,823,472 (65TB)"),
                Arguments.of(6_745_024_935_449_355L, "6,745,024,935,449,355 (6134TB)")
        );
    }
}
