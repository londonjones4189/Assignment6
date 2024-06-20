package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Represents the mock Model which is used
 * to the test the controller and if it is properly
 * receiving and sendingn information.
 */

public class MockModel implements IModel {
  private Appendable log;

  public MockModel(Appendable log) {
    this.log = log;
  }

  public Appendable getLog() {
    return log;
  }



  @Override
  public void createPortfolio(String portfolioName) {
    try {
      log.append(" (createPortfolio " + portfolioName + ")");
    } catch (IOException e) {
      e.printStackTrace();
    }

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
    try {
      log.append(" (getValue " + portfolioName + "," + date + ")");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  @Override
  public String examineGainLossDate(String ticker, LocalDate start, LocalDate end) {
    try {
      log.append(" (examineGainLossDate " + ticker + "," + start + "," + end + ")");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  @Override
  public List<String> examCrossover(String ticker, LocalDate startingDate, int numDaysBefore) {
    try {
      log.append("(examineCrosserover " + ticker + "," + startingDate + "," + numDaysBefore + ")");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return List.of();
  }

  @Override
  public String examXAvgOverDays(String ticker, LocalDate startingDate, int daysBefore) {
    try {
      log.append("(examXAvgOverDays " + ticker + "," + startingDate + "," + daysBefore + ")");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }



  @Override
  public List<String> getPortfolioList() {
    try {
      log.append(" (getPortfolioList) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return List.of();
  }


  @Override
  public Stock findTicker(String ticker) throws IOException {
    log.append("(findTicker");
    return null;
  }

  @Override
  public IPortfolio findPortfolio(String portfolioName) {
    return null;
  }
}
