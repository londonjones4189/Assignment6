package view;

import java.io.IOException;

/**
 * Represents the class abstracting out viewImpl abd viewMax,
 * both being used to display text in the terminal to the users.
 */
public abstract class AbstractView implements IView {
  private final Appendable appendable;
  private StringBuilder log;

  /**
   * Constructs the viewImpl.
   * @param appendable represents input/
   */
  public AbstractView(Appendable appendable) {
    this.appendable = appendable;
    this.log = new StringBuilder();
  }

  /**
   * Gets the log of the current view.
   * @return the log of the current view.
   */
  public Appendable getLog() {
    return this.log;
  }

  // writes a message to the console/appendable
  void writeMessage(String message) {
    try {
      this.appendable.append(message);
      this.log.append(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public abstract void printMenu() throws IOException;

  /**
   * Prints out the stock menu options.
   * @throws IOException if the input/output are missing.
   */
  public void printMenuHelper2() throws IOException {
    writeMessage("⎯⎯ Menu Options ⎯⎯" + System.lineSeparator());
    writeMessage("Would you like to examine individual stocks or create/edit stock portfolios?"
            + System.lineSeparator());
    writeMessage("1.Examine stocks (Type '1')" + System.lineSeparator());
    writeMessage("2.Create/edit portfolios (Type '2')" + System.lineSeparator());
  }

  void printMenuHelper() {
    writeMessage("3.Quit the program (Type 'quit')" + System.lineSeparator());
    writeMessage("Type in the number correlating with the option "
            + "you would like to move forward with"
            + System.lineSeparator());
    writeMessage("⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯"
            + System.lineSeparator());
  }

  @Override
  public void printResults(String function, String result, int num) throws IOException {
    switch (function) {
      case "examGL":
        writeMessage("Result: " + result + System.lineSeparator());
        break;
      case "XDayCrossover":
        writeMessage(num + " Day Stock Crossover, Dates that crossover: "
                + result + System.lineSeparator());
        break;
      case "XDayAvg":
        writeMessage(num + " Day Stock Average: $" + result + System.lineSeparator());
        break;
      default:
        writeMessage(result + System.lineSeparator());
    }
  }

  @Override
  public void welcomeMessage() throws IOException {
    writeMessage("⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯"
            + System.lineSeparator());
    writeMessage("Welcome to the Virtual Stock Market Simulator! \uD83D\uDCCA"
            + System.lineSeparator());
    writeMessage("This program is designed to help new investors "
            + "learn more about the stock market." + System.lineSeparator());
    writeMessage("Data is not available on weekends and national holidays."
            + System.lineSeparator());
    writeMessage("⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯"
            + System.lineSeparator());
  }

  @Override
  public void farewellMessage() throws IOException {
    writeMessage("Thank you for using our program! Goodbye! \uD83D\uDC4B"
            + System.lineSeparator());
  }


  @Override
  public void examineStocks() throws IOException {
    writeMessage("⎯⎯ Examining Individual Stocks Menu: ⎯⎯" + System.lineSeparator());
    writeMessage("1.Examine the gain/loss over a specific stock over time (Type '1')"
            + System.lineSeparator());
    writeMessage("2.Discover X-Day crossovers (Type '2')" + System.lineSeparator());
    writeMessage("3.Discover X-Day moving average (Type '3')" + System.lineSeparator());
    writeMessage("4.Go back to menu (Type 'menu')" + System.lineSeparator());
    writeMessage("5.Quit program (Type 'quit')" + System.lineSeparator());
    writeMessage("Type in the number correlating with the option you would like to select"
            + System.lineSeparator());
    writeMessage("⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯"
            + System.lineSeparator());
  }

  @Override
  public void examineGLStart() throws IOException {
    writeMessage("Examine the gain and loss of a specific stock \uD83D\uDD0E"
            + System.lineSeparator());
  }

  @Override
  public void examineXDayCrossStart() throws IOException {
    writeMessage("Examine the X-Day crossover of a specific stock \uD83D\uDD0E"
            + System.lineSeparator());
  }

  @Override
  public void xDays() throws IOException {
    this.writeMessage("Examine the X-day moving average of a specific stock \uD83D\uDD0E"
            + System.lineSeparator());
  }

  @Override
  public void enterTickerName() throws IOException {
    writeMessage("Type in the stock's ticker value: "
            + System.lineSeparator());
  }

  @Override
  public void examineEnterDate() throws IOException {
    writeMessage("Entering in date" + System.lineSeparator());
  }

  public void month() {
    writeMessage("Enter the month you wish to examine(XX):" + System.lineSeparator());
  }

  public void year() {
    writeMessage("Enter the year you wish to examine(XXXX):" + System.lineSeparator());
  }

  public void day() {
    writeMessage("Enter the day you wish to examine(XX):" + System.lineSeparator());
  }


  @Override
  public void examineEnterEarlierDate() throws IOException {
    writeMessage("Type the starting (earlier) date: "
            + System.lineSeparator());
  }

  @Override
  public void examineEnterLaterDate() throws IOException {
    writeMessage("Type the ending (later) date: "
            + System.lineSeparator());
  }

  @Override
  //need to have a for loop going through and give message back if not in list
  public void makePortfolio() throws IOException {
    writeMessage("Make a new portfolio \uD83D\uDCC1" + System.lineSeparator());
    writeMessage("The new name can not be the same as an existing portfolio name"
            + System.lineSeparator());
    this.enterPortfolioName();
  }

  @Override
  public void enterPortfolioName() throws IOException {
    writeMessage("The name should be one word with no spaces"
            + System.lineSeparator());
    writeMessage("Enter a name for the portfolio: " + System.lineSeparator());
  }

  @Override
  public void editPortfolio() throws IOException {
    writeMessage("Editing an existing portfolio \uD83D\uDCDD" + System.lineSeparator());
    this.enterPortfolioName();
  }


  @Override
  public void undefinedInstructions(String str) throws IOException {
    writeMessage("Undefined instruction: " + str + System.lineSeparator());
  }

  @Override
  public void invalidInput() {
    writeMessage("⚠\uFE0F Invalid input, cancelling action" + System.lineSeparator());
  }

  @Override
  public void enterNumDays() {
    writeMessage("Enter the number of days back you wish to include in the calculation period: "
            + System.lineSeparator());
  }

  @Override
  public void showExistingPorfolios(String str) {
    writeMessage("List of existing portfolios: " + str + System.lineSeparator());
  }

  @Override
  public void enterNumShares(String operation) {
    writeMessage("Enter the number of shares you wish to " + operation
            + ": " + System.lineSeparator());
  }

  @Override
  public void portfolioCreated(String portfolioName) {
    writeMessage("Success! ✅ New portfolio '" + portfolioName + "' added"
            + System.lineSeparator());
  }

  @Override
  public void portfolioError(int size) {
    if (size > 0) {
      writeMessage("❌ Error! That portfolio does not exist" + System.lineSeparator());
    } else {
      writeMessage("❌ No portfolios found to edit/view!" + System.lineSeparator());
    }
  }

  //helper for cePortfolio, abstarcting out similar feildsce

  /**
   * Shows the user the portfolio creation menu.
   * @throws IOException if the input/output is missing.
   */
  public void cePortfolioHelper() throws IOException {
    writeMessage("⎯⎯ Portfolio Creation/Editing Menu ⎯⎯" + System.lineSeparator());
    writeMessage("1.Make a new portfolio (Type '1')" + System.lineSeparator());
    writeMessage("2.Edit an existing portfolio (Type '2')" + System.lineSeparator());
    writeMessage("3.See the value of an existing portfolio (Type '3')" + System.lineSeparator());
  }

  /**
   * Prompts the user to type in the option they wish to execute.
   * @throws IOException if the input/output are missing.
   */
  public void moveForward() throws IOException {
    writeMessage("Type in the number correlating with the option "
            + "you would like to move forward with"
            + System.lineSeparator());

    writeMessage("⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯"
            + System.lineSeparator());
  }

  /**
   * Prints out the existing portfolio options.
   * @throws IOException if the input/output are missing.
   */
  public void editingExistingPortfolioHelper() throws IOException {
    writeMessage("⎯⎯ Editing an existing portfolio ⎯⎯" + System.lineSeparator());
    writeMessage("What changes would you like to make?" + System.lineSeparator());
    writeMessage("1.Add shares to an existing stock/add new stock (Type '1')"
            + System.lineSeparator());
    writeMessage("2.Remove a shares from an existing stock (Type '2')" + System.lineSeparator());
  }

  @Override
  public void sellCreated(String stocksName, int quanity) {
    writeMessage("Success!" + quanity + " of" + stocksName + "' sold"
            + System.lineSeparator());
  }

  public void addCreated(String stocksName, int quanity) {
    writeMessage("Success!" + quanity + " of" + stocksName + "' bought"
            + System.lineSeparator());
  }

  public void tryAgain() {
    writeMessage("Due to invaild input please try last action again");
  }

  public void invaildDate(String date) {
    writeMessage("Invaild " + date + " : try last action again" + System.lineSeparator());
  }

  /**
   * Informs user if the date is not in the system.
   */
  public void notInSystem() {
    writeMessage("The date you inputted is not in our system meaning it is a weekend or a holiday"
            + System.lineSeparator());
    this.invaildDate("date");
  }

  /**
   * Informs user if the ticker is not in the system.
   */
  public void notInSystemTicker() {
    writeMessage("The ticker symbol you inputted is not in our system"
            + System.lineSeparator());
    this.invaildDate("ticker");
  }

}
