import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.IModelMax;
import model.IPortfolioMax;
import model.ModelDate;
import model.Pair;
import model.PortfolioDate;
import model.SingleStock;

import static org.junit.Assert.assertEquals;

/**
 * Represents tests for the model date class.
 */
public class ModelDateTest {

  SingleStock apple = new SingleStock("AAPL",
          Files.readAllLines(Paths.get("res/stock data/AAPL.txt")));
  IPortfolioMax  portfolio = new PortfolioDate("Port1");
  LocalDate date = LocalDate.of(2024, 5, 31);
  LocalDate dateBefore = LocalDate.of(2024,5,30);
  LocalDate dateAfter = LocalDate.of(2024,6,03);
  LocalDate dateFar = LocalDate.of(2024,6,10);
  SingleStock a = new SingleStock("A",
          Files.readAllLines(Paths.get("res/stock data/A.txt")));
  IModelMax modelMax;

  public ModelDateTest() throws IOException {
    modelMax = new ModelDate();
  }

  //AAPL 192.2500
  //A 130.4100

  @Test
  public void rebalanceTestCompositionDecomposition() throws IOException,
          ParserConfigurationException, TransformerException {
    //test for day of
    modelMax.createPortfolio("Bye");
    modelMax.buy("Bye", "AAPL", 10.0, date);
    modelMax.buy("Bye", "A", 5.0, date);
    Map<String, Double> goals = new HashMap<>();
    goals.put("AAPL", 0.3);
    goals.put("A", 0.7);
    modelMax.reblance(date,"Bye",goals);
    Pair<String,String> newPair = modelMax.composition(date, "Bye");
    assertEquals(" 1.)A 2.)AAPL || 1.)13.819377348362856 2.)4.017503250975293",
            newPair.toString());
    Pair<String, String> newPair2 = modelMax.distrubtion(date, "Bye");
    assertEquals(" 1.)A 2.)AAPL || 1.)1802.185 2.)772.365", newPair2.toString());
    //test for day before buying
    //test day not in list, nothing has changed
    modelMax.reblance(dateAfter, "Bye", goals);
    Pair<String, String> newPair3 = modelMax.distrubtion(dateAfter, "Bye");
    Pair<String, String> newPair4 = modelMax.distrubtion(dateAfter, "Bye");
    assertEquals(" 1.)A 2.)AAPL || 1.)1816.7676375531305 2.)778.6147018084846",
            newPair4.toString());
    assertEquals(" 1.)A 2.)AAPL || 1.)1816.7676375531305 2.)778.6147018084846",
            newPair3.toString());
    //test for date in list but things have chnaged
    modelMax.createPortfolio("Hello");
    modelMax.buy("Hello", "AAPL", 10.0, date);
    Map<String, Double> goals2 = new HashMap<>();
    goals2.put("ABBV", 1.0);
    modelMax.reblance(dateAfter, "Hello", goals2);
    Pair<String,String> newPair5 = modelMax.composition(dateAfter, "Hello");
    Pair<String,String> newPair6 = modelMax.composition(dateAfter, "Hello");
    assertEquals(" 1.)ABBV || 1.)12.112491416442975", newPair6.toString());

  }

  @Test
  public void getValue() throws ParserConfigurationException, IOException, TransformerException {
    //no previous transactions
    modelMax.createPortfolio("test");
    assertEquals("0.0", modelMax.getValue("test", date));
    //one transaction on the day of
    modelMax.buy("test", "AAPL", 10.0, date);
    assertEquals("1922.5", modelMax.getValue("test", date));
    //new transaction on day with new stock purchase
    modelMax.buy("test", "A", 5.0, date);
    assertEquals("2574.55", modelMax.getValue("test", date));
    //purchase same stock
    modelMax.buy("test", "A", 5.0, date);
    assertEquals("3226.6", modelMax.getValue("test", date));
    //day before transactions
    assertEquals("0.0", modelMax.getValue("test", dateBefore));
    //day after transactions
    assertEquals("3226.6", modelMax.getValue("test", dateAfter));
    //day far after transactions
    assertEquals("3226.6", modelMax.getValue("test", dateFar));
    //remove stock on a day after transaction
    modelMax.sell("test", "AAPL", 10.0, dateAfter);
    assertEquals("3254.3", modelMax.getValue("test", dateFar));
    //add purchases on day after
    modelMax.buy("test", "AAPL", 10.0, dateAfter);
    //AAPL has a different stock value on this day
    assertEquals("5194.6", modelMax.getValue("test", dateFar));
    //check if dateBefore has changed after all these transactiosn
    assertEquals("0.0", modelMax.getValue("test", dateBefore));
    //test if day of has changed with all the transactins
    assertEquals("3226.6", modelMax.getValue("test", date));
    //add purchase on date after when there is more AAPL stock on another day
    modelMax.buy("test", "AAPL", 12.0, dateFar);
    assertEquals("7510.14", modelMax.getValue("test", dateFar));
  }

  @Test
  public void testBuy() throws ParserConfigurationException, IOException, TransformerException {
    modelMax.createPortfolio("test");
    modelMax.buy("test", "AAPL", 10.0, date);
    assertEquals("0.0", modelMax.getValue("test", dateBefore));
    assertEquals("1922.5", modelMax.getValue("test", date));
    //if purchase on the date after the price stays the same
    modelMax.buy("test", "A", 5.0, dateAfter);
    assertEquals("0.0", modelMax.getValue("test", dateBefore));
    assertEquals("1922.5", modelMax.getValue("test", date));
    assertEquals("2597.3", modelMax.getValue("test", dateAfter));
    //if unchoronlogical all other dates should after should be invaild
    modelMax.createPortfolio("test2");
    modelMax.buy("test2", "A", 5.0, dateAfter);
    assertEquals("657.0", modelMax.getValue("test2", dateAfter));
    //invaild dare
    modelMax.buy("test2", "A", 6.0, date);
    assertEquals("782.46", modelMax.getValue("test2", date));
    //the 5 stocks got deleted and was invaildated
    assertEquals("782.46", modelMax.getValue("test2", dateAfter));
    //if unchornlogical again
    modelMax.createPortfolio("test3");
    modelMax.buy("test3", "A", 5.0, dateBefore);
    assertEquals("657.0999999999999",
            modelMax.getValue("test3", dateBefore));
    modelMax.buy("test3", "A", 5.0, dateAfter);
    assertEquals("1314.0", modelMax.getValue("test3", dateAfter));
    modelMax.buy("test3", "A", 3.0, date);
    assertEquals("1043.28", modelMax.getValue("test3", date));
    assertEquals("657.0999999999999",
            modelMax.getValue("test3", dateBefore));
    //does not include pruchases made on that day because they were invaildated
    assertEquals("1043.28", modelMax.getValue("test3", dateAfter));
  }

  //not enough to remove on that day
  @Test
  public void testSell() throws ParserConfigurationException, IOException, TransformerException {
    modelMax.createPortfolio("test");
    //removing until zero
    modelMax.createPortfolio("test");
    modelMax.buy("test", "AAPL", 10.0, date);
    modelMax.sell("test", "AAPL", 10.0, date);
    assertEquals("0.0", modelMax.getValue("test", date));
    //removing some
    modelMax.buy("test", "AAPL", 10.0, date);
    modelMax.sell("test", "AAPL", 5.0, date);
    assertEquals("961.25", modelMax.getValue("test", date));
    //adding stock then removing
    modelMax.buy("test", "A", 10.0, date);
    modelMax.sell("test", "AAPL", 5.0, date);
    assertEquals("1304.1", modelMax.getValue("test", date));
    //selling on a day after
    modelMax.sell("test", "A", 7.0, dateAfter);
    //the date before stays the same
    assertEquals("1304.1", modelMax.getValue("test", date));
    //the date after shows the change
    assertEquals("394.20000000000005", modelMax.getValue("test", dateAfter));
    //how invalidating date works
    modelMax.createPortfolio("test2");
    modelMax.buy("test2", "A", 10.0, date);
    modelMax.buy("test2", "AAPL", 10.0, dateAfter);
    assertEquals("394.20000000000005", modelMax.getValue("test", dateAfter));
    //this date will invaildate all days before it
    modelMax.sell("test2", "A", 10.0, date);
    assertEquals("0.0", modelMax.getValue("test2", dateAfter));
    assertEquals("1304.1", modelMax.getValue("test", date));
    //another invaildating date
    modelMax.buy("test2", "A", 5.0, dateBefore);
    modelMax.sell("test2", "A", 2.0, dateBefore);
    assertEquals("394.20000000000005", modelMax.getValue("test", dateAfter));
  }


  //not the right stock on that date
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveStockNotRightStock() throws ParserConfigurationException, IOException,
          TransformerException {
    //no previous transactions
    modelMax.createPortfolio("test");
    modelMax.buy("tets", "A", 5.0, date);
    modelMax.sell("test", "AAPL", 10.0, date);
  }

  //wrong stock stock wrong date
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveStockNotEnoughStock2() throws ParserConfigurationException, IOException,
          TransformerException {
    modelMax.createPortfolio("test");
    modelMax.buy("tets", "A", 5.0, date);
    modelMax.sell("test", "AAPL", 10.0, dateAfter);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testwrongDayRightStock() throws ParserConfigurationException, IOException,
          TransformerException {
    modelMax.createPortfolio("test");
    modelMax.buy("test", "A", 5.0, date);
    modelMax.sell("test", "A", 4.0, dateBefore);
  }

}
