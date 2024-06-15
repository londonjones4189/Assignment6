package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Mock class for testing the new model implemented.
 */
public class MockModelDate implements IModelMax {
  StringBuilder log = new StringBuilder();

  public String getLog() {
    return log.toString();
  }

  @Override
  public IPortfolioMax reblance(LocalDate date, String portfolioName, Map<String, Double> goal) {
    log.append("Reblancing " + portfolioName + " in " + date + "\n");
    return null;
  }

  @Override
  public Pair<String, String> composition(LocalDate date, String portfolioName) {
    log.append("Composing " + portfolioName + " in " + date + "\n");
    return null;
  }

  @Override
  public Pair<String, String> distrubtion(LocalDate date, String portfolioName) {
    log.append("Distributing " + portfolioName + " in " + date + "\n");
    return null;
  }

  @Override
  public void sell(String portfolioname, String ticker, double numShares, LocalDate date) {
    log.append("Selling " + ticker + " in " + date + "\n");
  }

  @Override
  public void buy(String portfolioname, String ticker, double numShares, LocalDate date) {
    log.append("Buying " + ticker + " in " + date + "\n");
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException,
          ParserConfigurationException, IOException, TransformerException {
    // implemented in the main model
  }

  @Override
  public String addStock(String ticker, int numShares, String portfolioName)
          throws IllegalArgumentException {
    return "";
  }

  @Override
  public String removeStock(String ticker, int numberOfShares, String portfolioName) {
    return "";
  }

  @Override
  public String getValue(String portfolioName, LocalDate date) {
    return "";
  }

  @Override
  public String examineGainLossDate(String ticker, LocalDate start, LocalDate end)
          throws IllegalArgumentException {
    return "";
  }

  @Override
  public List<String> examCrossover(String ticker, LocalDate startingDate, int numDaysBefore) {
    return List.of();
  }

  @Override
  public String examXAvgOverDays(String ticker, LocalDate startingDate, int daysBefore) {
    return "";
  }

  @Override
  public List<String> getPortfolioList() {
    return List.of();
  }


  @Override
  public Stock findTicker(String ticker) {
    return null;
  }

  @Override
  public IPortfolio findPortfolio(String portfolioName) {
    return null;
  }
}
