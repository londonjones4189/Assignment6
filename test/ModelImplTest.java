import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.ModelImpl;
import model.SingleStock;

import static org.junit.Assert.assertEquals;

/**
 * Represents tests for the model class.
 */
public class ModelImplTest {
  ModelImpl model;
  SingleStock apple;
  LocalDate date = LocalDate.of(2024, 05, 31);
  LocalDate date2 = LocalDate.of(2024, 05, 30);

  @Before
  public void setUp() throws Exception {
    model = new ModelImpl();
    apple = new SingleStock("AAPL",
            Files.readAllLines(Paths.get("res/stock data/AAPL.txt")));
    model = new ModelImpl();
  }

  // EXAMINE GAIN LOSS
  // 2024-06-03
  @Test
  public void testExamineGainLoss0DayDiff() {
    assertEquals("model.Stock stayed the same \uD83D\uDFF0, value is $194.03",
            model.examineGainLossDate(
            "AAPL", LocalDate.of(2024, 06, 03),
            LocalDate.of(2024, 06, 03)));
  }

  @Test
  public void testExamineGainLoss0DayDiffAMZN() {
    assertEquals("model.Stock stayed the same \uD83D\uDFF0, value is $178.34",
            model.examineGainLossDate(
            "AMZN", LocalDate.of(2024, 06, 03),
            LocalDate.of(2024, 06, 03)));
  }


  @Test
  public void testExamineGainLossSameDay1DayDiff() {
    assertEquals("model.Stock gained value, stock value increased by $0.96 \uD83D\uDCC8",
            model.examineGainLossDate("AAPL",
                    LocalDate.of(2024, 05, 30),
                    LocalDate.of(2024, 05, 31)));
  }

  @Test
  public void testExamineGainLossSameDay1DayDiffAMZN() {
    assertEquals("model.Stock lost value, stock decreased by $-2.88 📉",
            model.examineGainLossDate("AMZN",
                    LocalDate.of(2024, 05, 30),
                    LocalDate.of(2024, 05, 31)));
  }


  @Test
  public void testExamineGainLoss7DayDiff() {
    assertEquals("model.Stock lost value, stock decreased by $-10.71 \uD83D\uDCC9",
            model.examineGainLossDate("AAPL",
                    LocalDate.of(2024, 04, 12),
                    LocalDate.of(2024, 04, 22)));
  }

  @Test
  public void testExamineGainLoss7DayDiffAMZN() {
    assertEquals("model.Stock lost value, stock decreased by $-8.90 \uD83D\uDCC9",
            model.examineGainLossDate("AMZN",
                    LocalDate.of(2024, 04, 12),
                    LocalDate.of(2024, 04, 22)));
  }

  @Test
  public void testExamineGainLossSameDay10DayDiff() {
    assertEquals("model.Stock gained value, stock value increased by $2.99 \uD83D\uDCC8",
            model.examineGainLossDate("AAPL",
                    LocalDate.of(2024, 05, 20),
                    LocalDate.of(2024, 06, 03)));
  }

  @Test
  public void testExamineGainLossSameDay10DayDiffAMZN() {
    assertEquals("model.Stock lost value, stock decreased by $-5.20 \uD83D\uDCC9",
            model.examineGainLossDate("AMZN",
                    LocalDate.of(2024, 05, 20),
                    LocalDate.of(2024, 06, 03)));
  }

  @Test
  public void testExamineGainLoss100DayDiff() {
    assertEquals("model.Stock gained value, stock value increased by $7.84 \uD83D\uDCC8",
            model.examineGainLossDate("AAPL",
                    LocalDate.of(2024, 01, 10),
                    LocalDate.of(2024, 06, 03)));
  }

  @Test
  public void testExamineGainLossSameDay100DayDiffAMZN() {
    assertEquals("model.Stock gained value, stock value increased by $24.61 \uD83D\uDCC8",
            model.examineGainLossDate("AMZN",
                    LocalDate.of(2024, 01, 10),
                    LocalDate.of(2024, 06, 03)));
  }

  @Test
  public void testExamineGainLossNetGainLargeRange() {
    assertEquals("model.Stock gained value, stock value increased by $158.67 \uD83D\uDCC8",
            model.examineGainLossDate("AAPL",
                    LocalDate.of(2005, 05, 17),
                    LocalDate.of(2024, 06, 03)));
  }

  @Test
  public void testExamineGainLossNetGainLargeRangeAMZN() {
    assertEquals("model.Stock gained value, stock value increased by $143.61 \uD83D\uDCC8",
            model.examineGainLossDate("AMZN",
                    LocalDate.of(2005, 05, 17),
                    LocalDate.of(2024, 06, 03)));
  }

  @Test
  public void testExamineGainLossNetLoss() {
    assertEquals("model.Stock lost value, stock decreased by $-25.38 \uD83D\uDCC9",
            model.examineGainLossDate("AAPL",
                    LocalDate.of(2019, 8, 1),
                    LocalDate.of(2024, 05, 10)));
  }

  @Test
  public void testExamineGainLossNeutral() {
    assertEquals("model.Stock stayed the same \uD83D\uDFF0, value is $194.03",
            model.examineGainLossDate("AAPL",
                    LocalDate.of(2009, 11, 05),
                    LocalDate.of(2024, 06, 03)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGLStockDoesntExist() throws IOException {
    model.examineGainLossDate("TSLA",
            LocalDate.of(2024, 01, 10),
            LocalDate.of(2024, 06, 03));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGLStockSpelledWrong() throws IOException {
    model.examineGainLossDate("APPL",
            LocalDate.of(2024, 01, 10),
            LocalDate.of(2024, 06, 03));
  }

  // date order reversed
  @Test(expected = IllegalArgumentException.class)
  public void testEndBeforeStart() throws IOException {
    model.examineGainLossDate("AAPL",
            LocalDate.of(2024, 06, 03),
            LocalDate.of(2024, 01, 10));
  }

  // invalid start date
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidStartDate() throws IOException {
    model.examineGainLossDate("AAPL",
            LocalDate.of(2024, 05, 25),
            LocalDate.of(2024, 06, 3));
  }

  // invalid end date
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEndDate() throws IOException {
    model.examineGainLossDate("AAPL",
            LocalDate.of(2024, 03, 04),
            LocalDate.of(2024, 05, 4));
  }

  // data missing
  @Test(expected = IllegalArgumentException.class)
  public void testDataForDateMissing() throws IOException {
    model.examineGainLossDate("AAPL",
            LocalDate.of(2000, 04, 17),
            LocalDate.of(2024, 01, 10));
  }

  // X DAY AVG tests
  @Test
  public void testXDayAvg1Day() {
    assertEquals("193.14", model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 03), 1));
  }

  @Test
  public void testXDayAvg1DayAMZN() {
    assertEquals("177.39", model.examXAvgOverDays("AMZN",
            LocalDate.of(2024, 06, 03), 1));
  }

  @Test
  public void testXDayAvg3Day() {
    assertEquals("192.52", model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 03), 3));
  }

  @Test
  public void testXDayAvg3DayAMZN() {
    assertEquals("178.03", model.examXAvgOverDays("AMZN",
            LocalDate.of(2024, 06, 03), 3));
  }

  @Test
  public void testXDayAvg7Day() {
    assertEquals("190.67", model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 03), 7));
  }

  @Test
  public void testXDayAvg7DayAMZN() {
    assertEquals("180.01", model.examXAvgOverDays("AMZN",
            LocalDate.of(2024, 06, 03), 7));
  }

  @Test
  public void testXDayAvg14Day() {
    assertEquals("190.42", model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 03), 14));
  }

  @Test
  public void testXDayAvg14DayAMZN() {
    assertEquals("182.23", model.examXAvgOverDays("AMZN",
            LocalDate.of(2024, 06, 03), 14));
  }

  @Test
  public void testXDayAvg30Day() {
    assertEquals("182.57", model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 03), 30));
  }

  @Test
  public void testXDayAvg30DayAMZN() {
    assertEquals("182.43", model.examXAvgOverDays("AMZN",
            LocalDate.of(2024, 06, 03), 30));
  }

  @Test
  public void testXDayAvg30DayGOOG() {
    assertEquals("169.48", model.examXAvgOverDays("GOOG",
            LocalDate.of(2024, 05, 29), 30));
  }

  @Test
  public void testXDayAvg100Day() {
    assertEquals("180.12", model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 03), 100));
  }

  @Test
  public void testXDayAvg100DayAMZN() {
    assertEquals("175.10", model.examXAvgOverDays("AMZN",
            LocalDate.of(2024, 06, 03), 100));
  }

  @Test
  public void testXDayAvgLargestRangePossible() {
    assertEquals("174.96", model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 03), 6048));
  }

  @Test
  public void testXDayAvgLargestRangePossibleAMZN() {
    assertEquals("609.87", model.examXAvgOverDays("AMZN",
            LocalDate.of(2024, 06, 03), 6074));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testXAvgIllegalDataMissingForStartDate() throws IOException {
    model.examXAvgOverDays("AAPL",
            LocalDate.of(2000, 05, 24), 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testXAvgIllegalStartDate() throws IOException {
    model.examXAvgOverDays("AAPL",
            LocalDate.of(1990, 04, 17), 10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testXAvgNegativeNumDays() throws IOException {
    model.examXAvgOverDays("AAPL",
            LocalDate.of(2025, 06, 13), -10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testXAvg0NumDays() throws IOException {
    model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 3), 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testXAvgInputGreaterThanData() throws IOException {
    model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 3), 10000);
  }

  // TESTING XDAYCROSSOVER
  // 1 Day Crossover
  @Test
  public void testXDayAvgCrossOver1Day() throws IOException {
    List<LocalDate> dateList = new ArrayList<LocalDate>(Arrays.asList(
            LocalDate.of(2024, 06, 03)));
    List<String> results = model.examCrossover("AAPL", LocalDate.of(2024, 06,
            03), 1);
    assertEquals(1, results.size());
    List<LocalDate> resultsDate = new ArrayList<LocalDate>();
    for (String result : results) {
      resultsDate.add(LocalDate.parse(result));
    }
    assertEquals(LocalDate.of(2024, 06, 03), resultsDate.get(0));
    assertEquals(dateList, resultsDate);
    assertEquals("193.14", model.examXAvgOverDays("AAPL", resultsDate.get(0),
            1));
  }

  // 3 Day crossover
  @Test
  public void testXDayAvgCrossOver3Day() throws IOException {
    List<LocalDate> dateList = new ArrayList<LocalDate>(Arrays.asList(
            LocalDate.of(2024, 05, 29),
            LocalDate.of(2024, 05, 28)));
    List<String> results = model.examCrossover("GOOG", LocalDate.of(2024, 05,
            29), 3);
    assertEquals(2, results.size());
    List<LocalDate> resultsDate = new ArrayList<LocalDate>();
    for (String result : results) {
      resultsDate.add(LocalDate.parse(result));
    }
    assertEquals(2, results.size());
    assertEquals(LocalDate.of(2024, 05, 29), resultsDate.get(0));
    assertEquals(LocalDate.of(2024, 05, 28), resultsDate.get(1));
    assertEquals(dateList, resultsDate);
    assertEquals("177.25", model.examXAvgOverDays("GOOG", resultsDate.get(0),
            3));
    assertEquals("176.47", model.examXAvgOverDays("GOOG", resultsDate.get(1),
            3));
  }

  // 30 Day Crossover
  @Test
  public void testXDayAvgCrossOver30Day() throws IOException {
    List<LocalDate> dateList = new ArrayList<LocalDate>(Arrays.asList(
            LocalDate.of(2024, 05, 29),
            LocalDate.of(2024, 05, 28),
            LocalDate.of(2024, 05, 24),
            LocalDate.of(2024, 05, 23),
            LocalDate.of(2024, 05, 22),
            LocalDate.of(2024, 05, 21),
            LocalDate.of(2024, 05, 20),
            LocalDate.of(2024, 05, 17),
            LocalDate.of(2024, 05, 16),
            LocalDate.of(2024, 05, 15),
            LocalDate.of(2024, 05, 14),
            LocalDate.of(2024, 05, 13),
            LocalDate.of(2024, 05, 10),
            LocalDate.of(2024, 05, 9),
            LocalDate.of(2024, 05, 8),
            LocalDate.of(2024, 05, 7),
            LocalDate.of(2024, 05, 6),
            LocalDate.of(2024, 05, 3),
            LocalDate.of(2024, 05, 2),
            LocalDate.of(2024, 05, 1),
            LocalDate.of(2024, 4, 30),
            LocalDate.of(2024, 4, 29),
            LocalDate.of(2024, 4, 26),
            LocalDate.of(2024, 4, 25),
            LocalDate.of(2024, 4, 24),
            LocalDate.of(2024, 4, 23),
            LocalDate.of(2024, 4, 22),
            LocalDate.of(2024, 4, 19),
            LocalDate.of(2024, 4, 18),
            LocalDate.of(2024, 4, 17)));
    List<String> results = model.examCrossover("GOOG", LocalDate.of(2024, 05,
            29), 30);
    assertEquals(30, results.size());
    List<LocalDate> resultsDate = new ArrayList<LocalDate>();
    for (String result : results) {
      resultsDate.add(LocalDate.parse(result));
    }
    assertEquals(dateList, resultsDate);
    assertEquals("169.48", model.examXAvgOverDays("GOOG", resultsDate.get(0),
            30));
    assertEquals("model.Stock stayed the same \uD83D\uDFF0, value is $177.4",
            model.examineGainLossDate("GOOG", resultsDate.get(0),
            resultsDate.get(0)));
  }

  // X greater than data available
  @Test(expected = IllegalArgumentException.class)
  public void testXDayCrossoverXTooLarge() throws IOException {
    model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 05, 28), 10000);
  }

  // illegal start date
  @Test(expected = IllegalArgumentException.class)
  public void testXDayCrossoverIllegalStart() throws IOException {
    model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 05, 27), 10);
  }

  // X=0
  @Test(expected = IllegalArgumentException.class)
  public void testXDayCrossover0Day() throws IOException {
    model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 03), 0);
  }

  // x = neg
  @Test(expected = IllegalArgumentException.class)
  public void testXDayCrossoverNegDay() throws IOException {
    model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 03), -10);
  }

  // data missing
  @Test(expected = IllegalArgumentException.class)
  public void testXDayCrossoverDataMissing() throws IOException {
    model.examXAvgOverDays("AAPL",
            LocalDate.of(2024, 06, 03), -10);
  }

  // test create portfolio
  @Test
  public void testCreatePortfolio() throws IOException, ParserConfigurationException,
          TransformerException {
    List<String> portfolioList = new ArrayList<String>();
    Assert.assertEquals(portfolioList, model.getPortfolioList());
    model.createPortfolio("model.Portfolio 1");
    portfolioList.add("model.Portfolio 1");
    Assert.assertEquals(portfolioList, model.getPortfolioList());
    model.createPortfolio("model.Portfolio 2");
    portfolioList.add(0, "model.Portfolio 2");
    Assert.assertEquals(portfolioList, model.getPortfolioList());
  }

  // public String getValue(String portfolioName, LocalDate date)
  @Test
  public void testgetValue() throws IOException, ParserConfigurationException,
          TransformerException {
    model.createPortfolio("model.Portfolio 1");
    assertEquals("$0.00", model.getValue("model.Portfolio 1", date));
    model.addStock("AAPL", 13, "model.Portfolio 1");
    model.createPortfolio("model.Portfolio 2");
    //two portfolio with same stock at different values
    model.addStock("ABBV", 200, "model.Portfolio 2");
    assertEquals("$31262.00", model.getValue("model.Portfolio 2", date2));
    model.createPortfolio("model.Portfolio 3");
    model.addStock("ABBV", 150, "model.Portfolio 3");
    assertEquals("$23446.50", model.getValue("model.Portfolio 3", date2));
    //two portfolios with same stock same value
    model.createPortfolio("model.Portfolio 4");
    model.addStock("ABBV", 200, "model.Portfolio 4");
    assertEquals("$31262.00", model.getValue("model.Portfolio 4", date2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testDateWeekend() {
    model.getValue("model.Portfolio 2",
            LocalDate.of(2024, 05, 18));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioNotFound() {
    model.getValue("model.Portfolio 2", date);
  }

  @Test
  public void testAddStock() throws ParserConfigurationException, IOException,
          TransformerException {
    //creates first portfolio
    model.createPortfolio("model.Portfolio 1");
    //creates second portfolio
    model.createPortfolio("model.Portfolio 2");
    model.createPortfolio("model.Portfolio 3");
    //starts off at zero value
    assertEquals("$0.00", model.getValue("model.Portfolio 1", date));
    //returns correct string for correct portfolio when added
    assertEquals("13 shares from AAPL added to model.Portfolio 1",
            model.addStock("AAPL", 13,
            "model.Portfolio 1"));
    //returns correct value after 1 stock added
    assertEquals("$2499.25", model.getValue("model.Portfolio 1", date));
    //adds onto a stock that is already in the folder
    assertEquals("13 shares from AAPL added to model.Portfolio 1",
            model.addStock("AAPL", 13,
            "model.Portfolio 1"));
    //adds onto the stock correctly
    assertEquals("$4998.50", model.getValue("model.Portfolio 1", date));
    //assertEquals("A added to model.Portfolio 1", model.addStock("A", 0,
    //  "model.Portfolio 1"));
    assertEquals("$4998.50", model.getValue("model.Portfolio 1", date));
    //still the same even with other portfolio
    assertEquals("$0.00", model.getValue("model.Portfolio 2", date));
    //add stock to this portilo and other portfolio unaffected
    assertEquals("10 shares from A added to model.Portfolio 2",
            model.addStock("A", 10,
            "model.Portfolio 2"));
    //coreect value of portfolio 2
    assertEquals("$1304.10", model.getValue("model.Portfolio 2", date));
    //portfolio 1 stays unaffected
    assertEquals("$4998.50", model.getValue("model.Portfolio 1", date));
    assertEquals("$0.00", model.getValue("model.Portfolio 3", date));
    assertEquals("$0.00", model.getValue("model.Portfolio 3", date));
    assertEquals("10 shares from A added to model.Portfolio 3",
            model.addStock("A", 10,
            "model.Portfolio 3"));
    assertEquals("$1304.10", model.getValue("model.Portfolio 3", date));
  }

  // PORTFOLIO TESTS
  //apple price 192.2500
  //a price 130.4100
  @Test
  public void testRemoveStockSome() throws ParserConfigurationException, IOException,
          TransformerException {
    model.createPortfolio("model.Portfolio 1");
    model.createPortfolio("model.Portfolio 2");
    //added to portfolio 1
    model.addStock("AAPL", 13, "model.Portfolio 2");
    model.addStock("A", 2, "model.Portfolio 2");
    //added to portfolio 2
    model.addStock("AAPL", 13, "model.Portfolio 1");
    model.addStock("A", 2, "model.Portfolio 1");
    assertEquals("$2760.07", model.getValue("model.Portfolio 1", date));
    //remove from portfolio 2
    model.removeStock("AAPL", 13, "model.Portfolio 2");
    assertEquals("$260.82", model.getValue("model.Portfolio 2", date));
    model.removeStock("A", 1, "model.Portfolio 2");
    assertEquals("$130.41", model.getValue("model.Portfolio 2", date));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotCorrectStock() throws ParserConfigurationException, IOException,
          TransformerException {
    model.createPortfolio("model.Portfolio 1");
    model.addStock("TSLA", 10, "model.Portfolio  5");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotCorrectPortfolio() throws ParserConfigurationException, IOException,
          TransformerException {
    model.createPortfolio("model.Portfolio 1");
    model.addStock("APPL", 10, "model.Portfolio  5");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotCorrectTickerRemove() throws ParserConfigurationException, IOException,
          TransformerException {
    model.createPortfolio("model.Portfolio 1");
    model.addStock("AMZN", 10, "model.Portfolio 1");
    model.removeStock("AAPL", 10, "model.Portfolio 1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNotCorrectPortoflioRemove() throws ParserConfigurationException, IOException,
          TransformerException {
    model.createPortfolio("model.Portfolio 1");
    model.addStock("APPL", 10, "model.Portfolio 1");
    model.removeStock("AAPL", 5, "model.Portfolio 2");
  }

  @Test
  public void testGetPortfolioList() throws ParserConfigurationException, IOException,
          TransformerException {
    // Add portfolios to the model
    model.createPortfolio("model.Portfolio 1");
    model.createPortfolio("model.Portfolio 2");
    model.createPortfolio("model.Portfolio 3");
    List<String> expectedPortfolios = new ArrayList<>(Arrays.asList("model.Portfolio 1",
            "model.Portfolio 2", "model.Portfolio 3"));
    List<String> actualPortfolios = model.getPortfolioList();
    Collections.sort(expectedPortfolios);
    Collections.sort(actualPortfolios);
    assertEquals(expectedPortfolios, actualPortfolios);
  }

  // TEST FAILS BECAUSE IT IS MISSING A DATE VALUE BEFORE
  // invalid date portfolio compute value

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioInvalidDate() throws ParserConfigurationException, IOException,
          TransformerException {
    model.createPortfolio("model.Portfolio 1");
    model.addStock("AAPL", 10, "model.Portfolio 1");
    model.getValue("model.Portfolio 1", LocalDate.of(1990, 05, 18));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPortfolioDateMissing() throws ParserConfigurationException, IOException,
          TransformerException {
    model.createPortfolio("model.Portfolio 1");
    model.addStock("AAPL", 10, "model.Portfolio 1");
    model.getValue("model.Portfolio 1", LocalDate.of(2024, 05, 18));
  }
}