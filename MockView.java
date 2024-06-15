package view;

import java.io.IOException;

/**
 * Represents the mock View.View which is used
 * to the test the controller and if it is properly
 * reciveing and sendingn information.
 */

public class MockView implements IView {
  private Appendable log;

  public MockView(Appendable log) {
    this.log = log;
  }


  public Appendable getLog() {
    return log;
  }

  @Override
  public void printResults(String function, String result, int num) {
    try {
      log.append(function + "" + result + "" + num);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void welcomeMessage() {
    try {
      log.append(" (welcomeMessage) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void farewellMessage() {
    try {
      log.append(" (farewellMessage) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void printMenu() {
    try {
      log.append(" (printMenu) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void examineStocks() {
    try {
      log.append(" (examineStocks) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void examineGLStart() {
    try {
      log.append(" (examineGLStart) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void examineXDayCrossStart() {
    try {
      log.append(" (examineXDayCrossStart) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void enterTickerName() {
    try {
      log.append(" (enterTickerName) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void examineEnterDate() {
    try {
      log.append(" (examineEnterDate) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void examineEnterEarlierDate() {
    try {
      log.append("(examineEnterEarlierDate) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void examineEnterLaterDate() {
    try {
      log.append(" (examineEnterLaterDate) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void cePortolio() {
    try {
      log.append(" (cePortolio) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void makePortfolio() {
    try {
      log.append(" (makePortfolio) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void enterPortfolioName() {
    try {
      log.append(" (enterPortfolioName) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void editPortfolio() throws IOException {
    try {
      log.append(" (editPortfolio) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void editingExistingPortfolio() {
    try {
      log.append(" (editingExistingPortfolio) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void xDays() throws IOException {
    try {
      log.append(" (xDays) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void undefinedInstructions(String str) {
    try {
      log.append(" (undefinedInstructions " + str + ") ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void invalidInput() {
    try {
      log.append(" (invaildInput) ");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void enterNumDays() {
    try {
      log.append(" (enterNumDays) ");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void showExistingPorfolios(String str) {
    try {
      log.append(str + " (showExistingPortfolio) ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void enterNumShares(String operation) {
    try {
      log.append(" (enterNumShares " + operation + ") ");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void portfolioCreated(String portfolioName) {
    try {
      log.append(" (portfolioCreated " + portfolioName + ") ");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void portfolioError(int size) {
    try {
      log.append(" (portfolioError " + size + ") ");
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  @Override
  public void month() {
    // mock
  }

  @Override
  public void day() {
    // mock
  }

  @Override
  public void year() {
    // mock
  }

  @Override
  public void sellCreated(String stocksName, int quanity) {
    // mock
  }

  @Override
  public void tryAgain() {
    // mock
  }

  @Override
  public void invaildDate(String value) {
    // mock
  }

  @Override
  public void notInSystem() {
    // mock
  }

  @Override
  public void notInSystemTicker() {
    // mock
  }

}
