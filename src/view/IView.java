package view;

import java.io.IOException;

/**
 * Represents the view of the program. The view is output seen by the user.
 */
public interface IView {

  /**
   * Prints out the log so far created by the view.
   *
   * @return an Appendable with all the program messages written to it.
   */
  Appendable getLog();

  /**
   * Prints the results of the functions associated with examining a single stock. The supported
   * functions include examining gain/loss, examining the X-Day crossover, and the X-day average.
   *
   * @param function represents the function with the results to be printed.
   * @param result   represents the result of the function.
   * @param num      represents the number of days included in the calculation (X-Day crossover
   *                 and average only).
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void printResults(String function, String result, int num) throws IOException;

  /**
   * Prints a welcome message if the user starts the program.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void welcomeMessage() throws IOException;

  /**
   * Prints a farewell message if the user quits the program.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void farewellMessage() throws IOException;

  /**
   * Prints the general menu for the program.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void printMenu() throws IOException;

  /**
   * Prints the examining stocks menu options.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void examineStocks() throws IOException;

  /**
   * Prompts the user to input values to calculate the gain/loss of a specific stock.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void examineGLStart() throws IOException;

  /**
   * Prompts the user to input values to calculate the X-Day crossover of a specific stock.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void examineXDayCrossStart() throws IOException;

  /**
   * Prompts the user to enter the ticker name.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void enterTickerName() throws IOException;

  /**
   * Prompts the user to enter the date they want to examine.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void examineEnterDate() throws IOException;

  /**
   * Prompts the user to enter the earlier date of a specified date period.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void examineEnterEarlierDate() throws IOException;

  /**
   * Prompts the user to enter the later date of a specified date period.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void examineEnterLaterDate() throws IOException;

  /**
   * Prints the creation/editing menu options for portfolios.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void cePortolio() throws IOException;

  /**
   * Prompts a user to make a new portfolio.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void makePortfolio() throws IOException;

  /**
   * Prompts a user to enter a portfolio name.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void enterPortfolioName() throws IOException;

  /**
   * Prompts the user to edit an existing portfolio.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void editPortfolio() throws IOException;

  /**
   * Prints a message showing the using the editing portfolio options.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void editingExistingPortfolio() throws IOException;

  /**
   * Prints a message if the user wants to see the X-day moving average for a stock.
   *
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void xDays() throws IOException;

  /**
   * Prints a message if the user inputs an undefined direction.
   *
   * @param str represents the last direction the user entered.
   * @throws IOException exception thrown if the expected input/output is missing.
   */
  void undefinedInstructions(String str) throws IOException;

  /**
   * Prints an invalid input message if the user tries to input an illegal input.
   */
  void invalidInput();

  /**
   * Prompts the user to enter the number of days (used for X-Day Crossover
   * and X-Day average calculations).
   */
  void enterNumDays();

  /**
   * Prints a list of existing portfolios.
   *
   * @param str represents the list of portfolios.
   */
  void showExistingPorfolios(String str);

  /**
   * Prompts the user to enter the number of shares to add/remove.
   *
   * @param operation represents if the user is trying to add or remove.
   */
  void enterNumShares(String operation) throws IOException;

  /**
   * Prints a message stating a portfolio was successfully created.
   *
   * @param portfolioName represents the name of the portfolio created.
   */
  void portfolioCreated(String portfolioName) throws IOException;

  /**
   * Prints an error message if the user tries to
   * edit a portfolio that doesn't exist.
   *
   * @param size represents the size of the portfolio list at runtime (size changes message).
   */
  void portfolioError(int size) throws IOException;

  /**
   * Prints out message letting the user know to type in
   * the month they would like to examine.
   */
  void month();

  /**
   * Prints out a message letting the user know to type in
   * the day they would like to eexamine.
   */
  void day();

  /**
   * Prints out a message letting the user know to type in the
   * year they would like to examine .
   */
  void year();

  void sellCreated(String stocksName, int quanity);

  void tryAgain();

  void invaildDate(String value);

  void notInSystem();

  void notInSystemTicker();

  void duplicate();
}
