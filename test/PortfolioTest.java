import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

import model.IPortfolio;
import model.Portfolio;
import model.SingleStock;
import model.Stock;

import static org.junit.Assert.assertEquals;

/**
 * Represents tests for portfolio class, which
 * is used to store infromation held by each portfolio created
 * by the user.
 */
public class PortfolioTest {

  IPortfolio portfolio1;
  LocalDate date;
  SingleStock apple;
  Stock agilent;
  IPortfolio portfolio2;

  @Before
  public void setUp() throws IOException {
    portfolio1 = new Portfolio("P1");
    date = LocalDate.of(2024, 5, 31);
    apple = new SingleStock("AAPL",
            Files.readAllLines(Paths.get("res/stock data/" + "AAPL" + ".txt")));
    agilent = new SingleStock("A",
            Files.readAllLines(Paths.get("res/stock data/" + "A" + ".txt")));
    portfolio2 = new Portfolio("P2");
  }


  @Test
  public void testGetValue() throws IOException {
    Portfolio portfolio1 = new Portfolio("P1");
    LocalDate date = LocalDate.of(2024, 5, 31);
    assertEquals(0, portfolio1.getPortfolioValue(date), 0.001);
    IPortfolio updatedPortfolio1 = portfolio1.addStock(apple, 13);
    assertEquals(2499.25, updatedPortfolio1.getPortfolioValue(date), 0.001);
    IPortfolio updatedPortfolio2 = updatedPortfolio1.addStock(agilent, 2);
    assertEquals(2760.07, updatedPortfolio2.getPortfolioValue(date), 0.001);
  }

  @Test
  public void testAddStock() throws IOException {
    IPortfolio updatedPort = portfolio1.addStock(apple, 13);
    assertEquals(2499.25, updatedPort.getPortfolioValue(date), 0.001);
    IPortfolio updatedPort2 = updatedPort.addStock(agilent, 2);
    assertEquals(2760.07, updatedPort2.getPortfolioValue(date), 0.001);
  }

  @Test
  public void testRemoveStock() throws IOException {
    IPortfolio updatedPort = portfolio2.addStock(apple, 13);
    IPortfolio updatedPort2 = updatedPort.removeStock(apple, 13);
    assertEquals(0.0, updatedPort2.getPortfolioValue(date), 0.001);
    IPortfolio updatedPort3 = updatedPort2.addStock(agilent, 2);
    assertEquals(260.82, updatedPort3.getPortfolioValue(date), 0.001);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveStockThrows() {
    IPortfolio updatedPort = portfolio1.addStock(apple, 13);
    updatedPort.removeStock(apple, 14);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRemoveStockThrows2() {
    IPortfolio updatedPort = portfolio1.addStock(apple, 13);
    updatedPort.removeStock(apple, 14);
  }

  @Test
  public void testGetTicker() {
    assertEquals("AAPL", apple.getTicker());
    assertEquals("A", agilent.getTicker());
  }

  @Test
  public void testFindLine() {
    assertEquals("2020-02-10,314.1800,321.5500,313.8500,321.5500,27337215",
            apple.findLineDaysBefore("2020-02-12", 2));
    assertEquals("2001-12-07,22.4600,22.7100,22.0000,22.5400,3634200",
            apple.findLineDaysBefore("2001-12-12", 3));
    assertEquals("2003-10-01,20.7500,21.1000,20.1900,20.7900,4216300",
            apple.findLineDaysBefore("2003-10-15", 10));

  }
}














