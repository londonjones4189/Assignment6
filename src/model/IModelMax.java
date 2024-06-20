package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Represents the new model. This model supports the new features added in part 2, such as
 * allowing the user to add and remove stocks with dates and rebalance their portfolio.
 */
public interface IModelMax extends IModel {
  /**
   * Allows the user to rebalance their portfolio to match their goal weights.
   * @param date represents the date the portfolio is being rebalanced on.
   * @param portfolioName represents the name of the portfolio.
   * @param goal represents the goal weights of the portfolios.
   * @return a new model.Portfolio with the weights changed.
   * @throws ParserConfigurationException if the file parsed is missing.
   * @throws IOException if the input/output is missing.
   * @throws TransformerException if there is a path missing.
   */
  IPortfolioMax reblance(LocalDate date, String portfolioName, Map<String, Double> goal)
          throws ParserConfigurationException, IOException, TransformerException;

  /**
   * Allows the user to find the composition of their portfolio.
   * @param date represents the date the composition of the portfolio is being found.
   * @param portfolioName represents the name of the portfolio.
   * @return A model.Pair where the left side represents a stock and
   *     the right side represents the number of shares in a portfolio.
   */
  Pair<String, String> composition(LocalDate date, String portfolioName);

  /**
   * Allows the user to find the distribution of their portfolio.
   * @param date represents the date the distribution of the portfolio is being found.
   * @param portfolioName represents the name of the portfolio.
   * @return A model.Pair where the left side represents a stock and
   *     the right side represents the monetary value in a portfolio.
   */
  Pair<String, String> distrubtion(LocalDate date, String portfolioName);

  /**
   * Allows the user to sell stocks from their portfolio on a specific date.
   * @param portfolioname represents the name of the portfolio.
   * @param ticker represents the stock's ticker.
   * @param numShares represents the number of shares being sold.
   * @param date represents the date of the sale.
   * @throws IOException if the input/output is missing.
   */
  void sell(String portfolioname, String ticker, double numShares, LocalDate date)
          throws IOException;

  /**
   * Allows the user to sell stocks from their portfolio on a specific date.
   * @param portfolioname represents the name of the portfolio.
   * @param ticker represents the stock's ticker.
   * @param numShares represents the number of shares being bought.
   * @param date represents the date of the sale.
   * @throws IOException if the input/output is missing.
   * @throws ParserConfigurationException if the file cannot be parsed.
   * @throws TransformerException if the path is wrong.
   */
  void buy(String portfolioname, String ticker, double numShares, LocalDate date)
          throws IOException, ParserConfigurationException, TransformerException;
}
