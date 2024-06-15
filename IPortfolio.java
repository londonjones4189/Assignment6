package model;

import java.time.LocalDate;
import java.util.List;

/**
 * Class represents a stock portfolio. Users can create a new portfolio with a name.
 * Portfolios have a value based on its stocks, the number of shares per stock,
 * and the date.
 */
public interface IPortfolio {

  /**
   * Gets the name of the portfolio.
   * @return the name of the portfolio.
   */
  String getPortfolioName();

  /**
   * Returns the value of the portfolio based on the portfolio's stocks, number of shares
   * per stock, and the date the user wants to examine the value.
   * @param date the date of the closing price used to calculate portfolio value.
   * @return the value of the Model.Model.Portfolio in USD
   */
  double getPortfolioValue(LocalDate date);

  /**
   * Allows a user to add a stock to the list of stocks in the program by inputting a ticker.
   *
   * @param stock          represents the stock added to the portfolio.
   * @param numberOfShares represents the number of shares of the stock
   *                       to be added to the portfolio.
   * @return a new Model.IPortfolio with the stock and the desired shares added
   */

  IPortfolio addStock(Stock stock, double numberOfShares);

  /**
   * Removes some stock from a portfolio based on number of shares.
   * @param numberOfShares represents the number of shares of the stock
   *                       to be removed from the portfolio.
   * @return a new Model.IPortfolio with the stock and the desired shares removed
   * @throws IllegalArgumentException if the user attempts to remove a stock that doesn't
   *     or the user tries to remove more stocks than currently exist in the portfolio
   */
  IPortfolio removeStock(Stock stock, double numberOfShares)
          throws IllegalArgumentException;

  /**
   * Returns the list in the current log of transactions.
   * @return the list of dates currently in the log.
   */
  List<LocalDate> getDatesInLog();
}
