package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Mock class for testing the new model implemented.
 */
public class MockModelDate implements IModelMax {
  private Appendable log;
  private ModelDate delegate;

  public MockModelDate(Appendable log) throws IOException {
    this.log = log;
    this.delegate = new ModelDate();
  }

  public String getLog() {
    return log.toString();
  }

  @Override
  public IPortfolioMax reblance(LocalDate date, String portfolioName, Map<String, Double> goal) throws IOException, ParserConfigurationException, TransformerException {
    log.append("Reblancing " + portfolioName + " in " + date + "\n");
    return delegate.reblance(date,portfolioName, goal);
  }

  @Override
  public Pair<String, String> composition(LocalDate date, String portfolioName) {
    try {
      log.append("Composing " + portfolioName + " in " + date + "\n");
    } catch (IOException ignored) {
    }

    return null;
  }

  @Override
  public  Pair<String, String>  distrubtion(LocalDate date, String portfolioName) {
    try {
      log.append("Distributing " + portfolioName + " in " + date + "\n");
    } catch (IOException ignored) {

    }

    return null;
  }

  @Override
  public void sell(String portfolioname, String ticker, double numShares, LocalDate date) throws IOException {
    log.append("Selling " + ticker + " in " + date + "\n");
  }

  @Override
  public void buy(String portfolioname, String ticker, double numShares, LocalDate date) throws IOException {
    log.append("Buying " + ticker + " in " + date + "\n");
  }


  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException,
          ParserConfigurationException, IOException, TransformerException {
    // implemented in the main model
    log.append("Creating " + portfolioName + "\n");
    this.delegate.createPortfolio(portfolioName);
  }

  @Override
  public String addStock(String ticker, int numShares, String portfolioName)
          throws IllegalArgumentException {
    try {
      log.append("Adding " + portfolioName + " in " + ticker + "\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "";
  }

  @Override
  public String removeStock(String ticker, int numberOfShares, String portfolioName) {
    try {
      log.append("Removing " + portfolioName + " in " + ticker + "\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "";
  }

  @Override
  public String getValue(String portfolioName, LocalDate date) {
    try {
      log.append("getValue " + portfolioName + " in " + date + "\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "";
  }

  @Override
  public String examineGainLossDate(String ticker, LocalDate start, LocalDate end)
          throws IllegalArgumentException {
    try {
      log.append("gl" + ticker  + "/" + start + "/" + end + "\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "";
  }

  @Override
  public List<String> examCrossover(String ticker, LocalDate startingDate, int numDaysBefore) {
    try {
      log.append("crossover " + ticker + " in " + startingDate + "/" + numDaysBefore+  "\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return List.of();
  }

  @Override
  public String examXAvgOverDays(String ticker, LocalDate startingDate, int daysBefore) {
    try {
      log.append("Xdayavg " + ticker + " in " + startingDate + "/" + daysBefore + "\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return "";
  }

  @Override
  public List<String> getPortfolioList() {
    try {
      log.append("getPortfolioList\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return delegate.getPortfolioList();
  }


  @Override
  public Stock findTicker(String ticker) throws IOException {
    log.append("findTicker " + ticker + "\n");
    return null;
  }

  @Override
  public IPortfolioMax findPortfolio(String portfolioName) {
    try {
      log.append("findPortfolio " + portfolioName + "\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return (IPortfolioMax) this.delegate.findPortfolio(portfolioName);
  }
}