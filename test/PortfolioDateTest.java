import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.IPortfolioMax;
import model.Pair;
import model.PortfolioDate;
import model.SingleStock;
import model.Stock;

import static org.junit.Assert.assertEquals;

/**
 * Represnets tests for portfolioDate.
 */
public class PortfolioDateTest {
  SingleStock apple;
  IPortfolioMax portfolio;
  LocalDate date;
  LocalDate dateBefore;
  LocalDate dateAfter;
  SingleStock a;
  SingleStock abbv;
  LocalDate dateFar;
  
  @Before
  public void setUp() throws Exception {
    apple = new SingleStock("AAPL",
            Files.readAllLines(Paths.get("res/stock data/AAPL.txt")));
    portfolio = new PortfolioDate("Port1");
    date = LocalDate.of(2024, 5, 31);
    dateBefore = LocalDate.of(2024, 5, 30);
    dateAfter = LocalDate.of(2024, 6, 03);
    a = new SingleStock("A",
            Files.readAllLines(Paths.get("res/stock data/A.txt")));
    abbv = new SingleStock("ABBV",
            Files.readAllLines(Paths.get("res/stock data/ABBV.txt")));
    dateFar = LocalDate.of(2024, 6, 10);
  }

  //This test shows that rebalancing works because orginally apple had 10 stocks, menaing
  //it was not correctly balanced it should not error when removing 6 stocks
  //but since it does it shows that  apple was correctly balanced
  @Test(expected = IllegalArgumentException.class)
  public void RebalanceTest() {
    //unbalanced portfolio
    IPortfolioMax p1 = portfolio.addStockNew(date, apple, 10);
    IPortfolioMax p2 = p1.addStockNew(date, a, 10);
    Map<Stock, Double> goals = new HashMap<>();
    goals.put(apple, 0.30);
    goals.put(a, 0.70);
    assertEquals(3226.6, p2.getPortfolioValue(date), 0.001);
    IPortfolioMax p3 = p2.rebalance(goals, date);
    assertEquals(3226.6, p3.getPortfolioValue(date), 0.001);
    //stocks for a 10-> 17
    //stocks for appl 10-> 5
    IPortfolioMax p4 = p3.removeStockNew(date, apple, 6);
    assertEquals(3226.6, p4.getPortfolioValue(date), 0.001);
  }

  //This checks to see if it is alrready balanced and only rebalances it by a few decimals
  //it does not error when trying to remove the majority of the stocks
  @Test
  public void RebalanceAlreadyBalanced() {
    //balancaed portfolio
    IPortfolioMax p1 = portfolio.addStockNew(date, apple, 10);
    IPortfolioMax p2 = p1.addStockNew(date, a, 10);
    Map<Stock, Double> goals = new HashMap<>();
    goals.put(apple, 0.60);
    goals.put(a, 0.40);
    assertEquals(3226.6, p2.getPortfolioValue(date), 0.001);
    IPortfolioMax p3 = p2.rebalance(goals, date);
    assertEquals(3226.6, p3.getPortfolioValue(date), 0.001);
    IPortfolioMax p4 = p3.removeStockNew(date, apple, 9);
    IPortfolioMax p5 = p4.removeStockNew(date, a, 9);
    //can only remove 9 because only fractional shares are left
    assertEquals(322.6599999999999, p5.getPortfolioValue(date), 0.001);
  }

  //being able to remove abbv shows that it has boughten it
  @Test(expected = IllegalArgumentException.class)
  public void RebalanceHaveToSell() {
    //unbalanced portfolio
    IPortfolioMax p1 = portfolio.addStockNew(date, apple, 10);
    IPortfolioMax p2 = p1.addStockNew(date, a, 10);
    Map<Stock, Double> goals = new HashMap<>();
    goals.put(abbv, 1.0);
    assertEquals(3226.6, p2.getPortfolioValue(date), 0.001);
    IPortfolioMax p3 = p2.rebalance(goals, date);
    assertEquals(3226.6, p3.getPortfolioValue(date), 0.001);
    IPortfolioMax p4 = p3.removeStockNew(date, abbv, 19);
    assertEquals(163.04, p4.getPortfolioValue(date), 0.001);
  }


  //throw an illegeal argument because there is  no apple so it throw an illgeal arguement
  @Test(expected = IllegalArgumentException.class)
  public void RebalanceHaveToBuy() {
    //unbalanced portfolio
    IPortfolioMax p1 = portfolio.addStockNew(date, apple, 10);
    IPortfolioMax p2 = p1.addStockNew(date, a, 10);
    Map<Stock, Double> goals = new HashMap<>();
    goals.put(abbv, 1.0);
    assertEquals(3226.6, p2.getPortfolioValue(date), 0.001);
    IPortfolioMax p3 = p2.rebalance(goals, date);
    assertEquals(3226.6, p3.getPortfolioValue(date), 0.001);
    IPortfolioMax p4 = p3.removeStockNew(date, a, 19);
  }


  @Test
  public void Distrubtion() throws IOException {
    IPortfolioMax p1 = portfolio.addStockNew(date, apple, 10); //1922.5
    IPortfolioMax p2 = p1.addStockNew(date, a, 10); //1304.1
    ArrayList<Pair<String, Double>> p3 = p2.distrubtion(dateAfter);
    assertEquals("[AAPL ||1922.5, A ||1304.1]", p3.toString());
    IPortfolioMax p4 = p2.removeStockNew(date, a, 5);
    ArrayList<Pair<String, Double>> p5 = p4.distrubtion(dateAfter);
    assertEquals("[AAPL ||1922.5, A ||652.05]", p5.toString());
    IPortfolioMax p6 = p4.addStockNew(dateAfter, a, 5);
    ArrayList<Pair<String, Double>> p7 = p6.distrubtion(date);
    assertEquals("[AAPL ||1922.5, A ||652.05]", p7.toString());
  }


  @Test
  public void getValue() throws ParserConfigurationException, IOException, TransformerException {
    //no previous transactions
    assertEquals(0.0, portfolio.getPortfolioValue(date), 0.001);
    //one transaction on the day of
    IPortfolioMax p1 = portfolio.addStockNew(date, apple, 10);
    assertEquals(1922.5, p1.getPortfolioValue(date), 0.001);
    //new transaction on day with new stock purchase
    IPortfolioMax p2 = p1.addStockNew(date, a, 5);
    assertEquals(2574.55, p2.getPortfolioValue(date), 0.001);
    //purchase same stock
    IPortfolioMax p3 = p2.addStockNew(date, a, 5);
    assertEquals(3878.65, p3.getPortfolioValue(date), 0.001);
    //day before transactions
    assertEquals(0.0, p3.getPortfolioValue(dateBefore), 0.001);
    //day after transactions
    assertEquals(3226.60, p3.getPortfolioValue(dateAfter), 0.001);
    //day far after transactions
    assertEquals(3226.60, p3.getPortfolioValue(dateFar), 0.001);
    //remove stock on a day after transaction
    IPortfolioMax p4 = p3.removeStockNew(dateAfter, apple, 10);
    assertEquals(1314.0, p4.getPortfolioValue(dateFar), 0.001); //questins
    //add purchases on day after
    IPortfolioMax p5 = p4.addStockNew(dateAfter, apple, 10);
    assertEquals(3254.3, p5.getPortfolioValue(dateFar), 0.001);
    //check if dateBefore has changed after all these transactions
    assertEquals(0.0, p5.getPortfolioValue(dateBefore), 0.001);
    //test if day of has changed with all the transactions
    assertEquals(3226.6, p5.getPortfolioValue(date), 0.001);
  }


  @Test
  public void testAddStockNew() throws ParserConfigurationException,
          IOException, TransformerException {
    IPortfolioMax p1 = portfolio.addStockNew(date, apple, 10);
    assertEquals(0.0, p1.getPortfolioValue(dateBefore), 0.001);
    assertEquals(1922.5, p1.getPortfolioValue(date), 0.001);
    //if purchase on the date after the price stays the same
    IPortfolioMax p2 = p1.addStockNew(dateAfter, a, 5);
    assertEquals(0.0, p2.getPortfolioValue(dateBefore), 0.001);
    assertEquals(1922.5, p2.getPortfolioValue(date), 0.001);
    assertEquals(657.0, p2.getPortfolioValue(dateAfter), 0.001);
    //if unchoronlogical all other dates should after should be invaild
    IPortfolioMax p3 = portfolio.addStockNew(dateAfter, a, 5);
    assertEquals(657.0, p3.getPortfolioValue(dateAfter), 0.001);
    //invaild date
    IPortfolioMax p4 = p3.addStockNew(date, a, 6);
    assertEquals(1434.51, p4.getPortfolioValue(date), 0.001);
    //the 5 stocks got deleted and was invaildated
    assertEquals(657.0, p4.getPortfolioValue(dateAfter), 0.001);
    //if unchornlogical again
    IPortfolioMax p5 = p4.addStockNew(dateBefore, a, 5);
    assertEquals(1314.1999999999998, p5.getPortfolioValue(dateBefore), 0.001);
    IPortfolioMax p6 = p5.addStockNew(dateAfter, a, 5);
    assertEquals(1971.0, p6.getPortfolioValue(dateAfter), 0.001);
    IPortfolioMax p7 = p6.addStockNew(dateAfter, a, 5);
    assertEquals(1434.51, p7.getPortfolioValue(date), 0.001);
    assertEquals(1314.1999999999998, p7.getPortfolioValue(dateBefore), 0.001);
    //does not include pruchases made on that day because they were invaildated
    assertEquals(3285.0, p7.getPortfolioValue(dateAfter), 0.001);
  }


  @Test
  public void testSell() throws ParserConfigurationException, IOException, TransformerException {
    IPortfolioMax p1 = portfolio.addStockNew(date, apple, 10)
            .removeStockNew(date, apple, 10);
    assertEquals(0.0, p1.getPortfolioValue(date), 0.001);
    //removing some
    IPortfolioMax p2 = p1.addStockNew(date, a, 10).removeStockNew(date, a, 5);
    assertEquals(652.05, p2.getPortfolioValue(date), 0.001);
    //adding stock then removing
    IPortfolioMax p3 = p2.addStockNew(date, a, 10).removeStockNew(date, a, 5);
    assertEquals(1304.1, p3.getPortfolioValue(date), 0.001);
    //selling on a day after
    IPortfolioMax p4 = p3.removeStockNew(dateAfter, a, 4);
    //the date before stays the same
    assertEquals(1304.1, p4.getPortfolioValue(date), 0.001);
    //the date after shows the change
    assertEquals(788.4000000000001, p4.getPortfolioValue(dateAfter), 0.001);
    //another invaildating date
    IPortfolioMax p5 =
            p4.addStockNew(dateBefore, a, 5.0).removeStockNew(dateAfter, a, 2);
    assertEquals(525.6, p5.getPortfolioValue(dateAfter), 0.001);
  }
}
