import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import model.SingleStock;

import static org.junit.Assert.assertEquals;

/**
 * Represents tests for the single stock class.
 */
public class SingleStockTest {
  SingleStock apple;

  @Before
  public void setUp() throws IOException {
    apple = new SingleStock("AAPL",
            Files.readAllLines(Paths.get("res/stock data/AAPL.txt")));
  }

  @Test
  public void testGetStockValues() {
    assertEquals(192.9000,
            apple.getStockValues("2024-06-03", "open"), 0.01);
    assertEquals(194.9900,
            apple.getStockValues("2024-06-03", "high"), 0.01);
    assertEquals(192.5200,
            apple.getStockValues("2024-06-03", "low"), 0.01);
    assertEquals(194.0300,
            apple.getStockValues("2024-06-03", "close"), 0.01);
  }

  //321.4700,321.4700,321.4700,327.2000
  @Test
  public void testGetStockValues2() {
    assertEquals(321.4700,
            apple.getStockValues("2020-02-12", "open"), 0.01);
    assertEquals(321.4700,
            apple.getStockValues("2020-02-12", "high"), 0.01);
    assertEquals(321.4700,
            apple.getStockValues("2020-02-12", "low"), 0.01);
    assertEquals(327.2000,
            apple.getStockValues("2020-02-12", "close"), 0.01);
  }
}