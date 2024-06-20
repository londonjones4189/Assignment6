package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Represents the Model for the model.Stock program. The model handles the stock data,
 * performs computations, reads data from the res folder, and stores information about
 * the user stock profiles.
 */
public interface IModel {

  /**
   * Creates a portfolio with the specified portfolio name. New portfolio is added to
   * the list of portfolios stored by the model.
   *
   * @param portfolioName represents the name of the new model.Portfolio
   */
  void createPortfolio(String portfolioName) throws IllegalArgumentException,
          ParserConfigurationException, IOException, TransformerException;

  /**
   * Allows the user to add a stock to a portfolio without a date.
   * @param ticker represents the ticker name of the stock.
   * @param numShares represents the number of shares to be bought.
   * @param portfolioName represents the name of the portfolio.
   * @return a String indicating whether the method was successful or not.
   * @throws IllegalArgumentException if the ticker or number of shares is invalid.
   */
  String addStock(String ticker, int numShares, String portfolioName)
          throws IllegalArgumentException;

  /**
   * Allows the user to remove a stock from a portfolio  without a date.
   * @param ticker represents the ticker name of the stock.
   * @param numberOfShares represents the number of shares to be bought.
   * @param portfolioName represents the name of the portfolio.
   * @return a String indicating whether the method was successful or not.
   */
  String removeStock(String ticker, int numberOfShares, String portfolioName);

  /**
   * Returns the value of a given portfolio on a specified date.
   * @param portfolioName represents the name of the portfolio.
   * @param date represents the date used to evaluate the portfolio.
   * @return a String that shows the value of the portfolio in USD.
   */
  String getValue(String portfolioName, LocalDate date);

  /**
   * Returns the gain or loss of value of a specified stock during a given date range.
   *
   * @param ticker represents the stock's NYSE ticker.
   * @param start  represents the start of the specified period.
   * @param end represents the end of the specified period.
   * @return a String with the result of the calculation. The result can either be that the stock
   *     increased, decreased, or stayed the same value. The difference is value is also printed.
   * @throws IllegalArgumentException if the number of shares is less than or equal to 0.
   */
  String examineGainLossDate(String ticker, LocalDate start, LocalDate end)
          throws IllegalArgumentException;

  /**
   * Computes the X-Day crossover of a given stock over a specified period.
   *
   * @param ticker        represents the stock's NYSE ticker.
   * @param numDaysBefore the number of days before the start date
   *                      the user wants to see the average.
   * @return a list with the dates with a closing price higher than the X-Day average for that day.
   */
  List<String> examCrossover(String ticker, LocalDate startingDate, int numDaysBefore);

  /**
   * Computes the X-Day average of a given stock over a specified period.
   *
   * @param ticker     represents the stock's NYSE ticker.
   * @param daysBefore the number of days before the start date the user wants to see the average.
   * @return the X-Day average of a given stock starting at the start date and X number of days
   *     into the past.
   */
  String examXAvgOverDays(String ticker, LocalDate startingDate, int daysBefore);

  /**
   * Used by controller to view all the portfolios already created.
   * @return a list of all the portfolios currently stored by the model
   */
  List<String> getPortfolioList();

  /**
   * Returns the stock represented by a ticker. If the stock is not already saved to the program,
   * the program will contact the API to receive the data.
   * @param ticker represents the ticker name of the stock.
   * @return a model.Stock with the given ticker.
   * @throws IllegalArgumentException if ticker is not supported by the program or the API.
   */
  Stock findTicker(String ticker) throws IllegalArgumentException;

  /**
   * Returns a portfolio that is represented by the given portfolio name.
   * @param portfolioName represents the name of the portfolio.
   * @return the portfolio associated with the name.
   * @throws IllegalArgumentException if the portfolio cannot be found.
   */
  IPortfolio findPortfolio(String portfolioName) throws IllegalArgumentException;
}
