package osmosis.folder.inspector.formatter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DigitalFormatterTest {
    @Test
    public void givenSizeWhenFormatSizeThenReturnFormattedString() {
        assertEquals("0 (0B)", DigitalFormatter.formatSize(0));
        assertEquals("1 (1B)", DigitalFormatter.formatSize(1));
        assertEquals("1,000 (1000B)", DigitalFormatter.formatSize(1000));
        assertEquals("1,024 (1KB)", DigitalFormatter.formatSize(1024));
        assertEquals("43,278,949 (41MB)", DigitalFormatter.formatSize(43278949));
        assertEquals("675,894,025,894 (629GB)", DigitalFormatter.formatSize(675894025894L));
        assertEquals("72,389,479,823,472 (65TB)", DigitalFormatter.formatSize(72389479823472L));
        assertEquals("6,745,024,935,449,355 (6134TB)", DigitalFormatter.formatSize(6745024935449355L));
    }
}