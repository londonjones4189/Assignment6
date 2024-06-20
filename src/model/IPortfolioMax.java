package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Represents the new portfolio with extended functionality. This portfolio has dates
 * and all purchases and sales can be seen on a specific date.
 */
public interface IPortfolioMax extends IPortfolio {

  /**
   * Creates a .xml file representing the portfolio to be saved after the program
   * has been closed.
   *
   * @throws ParserConfigurationException if the file to be parsed cannot be found.
   * @throws TransformerException         if the path is missing.
   * @throws IOException                  if the input or output is missing.
   */
  void generatePortfolioXML() throws ParserConfigurationException,
          TransformerException, IOException;

  /**
   * Gets the name of the portfolio.
   *
   * @return the name of the portfolio.
   */
  String getPortfolioName();

  /**
   * Allows the user to buy stocks from their portfolio on a specific date.
   *
   * @param date   represents the date of the purchase.
   * @param stock  represents the stock being bought.
   * @param shares represents the number of shares being bought.
   * @return a new model.Portfolio with the new stock added.
   */
  IPortfolioMax addStockNew(LocalDate date, Stock stock, double shares);

  /**
   * Allows the user to rebalance their portfolio to match their goal weights.
   *
   * @param goalStock represents the goal weights of each stock.
   * @param date      represents the date the portfolio is being rebalanced on.
   * @return a new model.Portfolio with the stock weights rebalanced.
   */
  IPortfolioMax rebalance(Map<Stock, Double> goalStock, LocalDate date);

  /**
   * Allows the user to sell stocks from their portfolio on a specific date.
   *
   * @param date   represents the date of the sale.
   * @param stock  represents the stock being sold.
   * @param shares represents the number of shares being sold.
   * @return a new model.Portfolio with the stock removed.
   */
  IPortfolioMax removeStockNew(LocalDate date, Stock stock, double shares);

  /**
   * Allows the user to find the composition of their portfolio.
   *
   * @param date represents the date the composition of the portfolio is being found.
   * @return A list of Pairs where the left side represents a stock and
   * the right side represents the number of shares in a portfolio.
   */
  ArrayList<Pair<String, Double>> compostion(LocalDate date);

  /**
   * Allows the user to find the distribution of their portfolio.
   *
   * @param date represents the date the distribution of the portfolio is being found.
   * @return A list of Pairs where the left side represents a stock and
   * the right side represents the number of shares in a portfolio.
   */
  ArrayList<Pair<String, Double>> distrubtion(LocalDate date);

}
