package view;

import java.io.IOException;

import model.ModelDate;

/**
 * Represents a mock view used for testing.
 */
public class MockViewMax implements IViewMax {
  private Appendable log;
  private ModelDate delegate;

  public MockViewMax(Appendable log){
    this.log = log;
  }

  public void printChart(String chart) {
    try {
      log.append("(print chart)" + chart);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void rebalance() {
    try {
      log.append("(rebalance)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void enterPercent()  {
    try {
      log.append("(enter Percent)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void heading(String message)  {
    try {
      log.append("(heading" + message +")");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void barChartMenu()  {
    try {
      log.append("(barChartMenu)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void goals()  {
    try {
      log.append("(goals)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void comps(String left, String right)  {
    try {
      log.append("(comps" + left + "/" + right + ")");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void removing() {
    try {
      log.append("(removing)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Appendable getLog() {
    return log;
  }

  @Override
  public void printResults(String function, String result, int num) {
    try {
      log.append("(print results" + function + " " + result + ")" + num);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void welcomeMessage()  {
    try {
      log.append("(welcome)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void farewellMessage() {
    try {
      log.append("(farewell)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void printMenu() {
    try {
      log.append("(printMenu)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void examineStocks()  {
    try {
      log.append("(examineStocks)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void examineGLStart()  {
    try {
      log.append("(examineGLStart)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void examineXDayCrossStart()  {
    try {
      log.append("(exmaineXDayCrossStart)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void enterTickerName()  {
    try {
      log.append("(enterTickerName)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void examineEnterDate() {
    try {
      log.append("(examineEnterDate)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void examineEnterEarlierDate() {
    try {
      log.append("(examineEnterEarlierDate)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void examineEnterLaterDate() {
    try {
      log.append("(examineEnterLaterDate)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void cePortolio()  {
    try {
      log.append("(cePortolio)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void makePortfolio()  {
    try {
      log.append("(makePortfolio)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void enterPortfolioName()  {
    try {
      log.append("(enterPortfolioName)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void editPortfolio()  {
    try {
      log.append("(editPortfolio)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void editingExistingPortfolio()  {
    try {
      log.append("(editingExistingPortfolio)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void xDays()  {
    try {
      log.append("(xDays)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void undefinedInstructions(String str)  {
    try {
      log.append("(undefinedInstructions)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void invalidInput() {
    try {
      log.append("(invaildInput)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void enterNumDays()  {
    try {
      log.append("(enterNumDays)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void showExistingPorfolios(String str)  {
    try {
      log.append("(showExisitingPortfolios)" + str);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void enterNumShares(String operation) {
    try {
      log.append("(enterNumShares)" + operation);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void portfolioCreated(String portfolioName)  {
    try {
      log.append("(portfolioCreated" + portfolioName + ")");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void portfolioError(int size)  {
    try {
      log.append("(portfolioError" + size + ")");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void month()  {
    try {
      log.append("(month)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void day()  {
    try {
      log.append("day");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void year() {
    try {
      log.append("(Year)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void sellCreated(String stocksName, int quanity)  {
    try {
      log.append("(sellCreated)" + quanity);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void tryAgain()  {
    try {
      log.append("(tryAgain)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void invaildDate(String value) {
    try {
      log.append("(InvaildDate)" + value);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void notInSystem()  {
    try {
      log.append("(Not in system)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void notInSystemTicker()  {
    try {
      log.append("(notInSystemTicker)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void duplicate() {
    try {
      log.append("(duplicate)");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}


