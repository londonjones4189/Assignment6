package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a portfolio that can be created by the user.
 * Contains stocks and number of shares per stock.
 */
public class Portfolio implements IPortfolio {
  private final String portfolioName;
  private final List<Stock> stocks;
  private final List<Double> shares;

  /**
   * Public constructor for the class.
   * @param portfolioName represents the name of the portfolio.
   */
  public Portfolio(String portfolioName) {
    this.portfolioName = portfolioName;
    this.stocks = new ArrayList<>();
    this.shares = new ArrayList<>();
  }

  private Portfolio(String portfolioName, List<Stock> stocks, List<Double> shares) {
    this.portfolioName = portfolioName;
    this.stocks = new ArrayList<>(stocks);
    this.shares = new ArrayList<>(shares);
  }

  @Override
  public String getPortfolioName() {
    return this.portfolioName;
  }

  @Override
  public double getPortfolioValue(LocalDate date) {
    double combinedValue = 0;
    for (int i = 0; i < stocks.size(); i++) {
      double value = stocks.get(i).getStockValues(date.toString(), "close");
      double result = value * shares.get(i);
      combinedValue += result;
    }
    return combinedValue;
  }

  @Override
  public IPortfolio addStock(Stock stock, double numberOfShares) {
    List<Stock> newStocks = new ArrayList<>(this.stocks);
    List<Double> newShares = new ArrayList<>(this.shares);
    if (!(this.stocks.contains(stock))) {
      newStocks.add(stock);
      newShares.add(numberOfShares);
    } else {
      for (int i = 0; i < newStocks.size(); i++) {
        for (int j = 0; j < newShares.size(); j++) {
          if (newStocks.get(i) == stock) {
            newShares.set(j, newShares.get(j) + numberOfShares);
          }
        }
      }
    }
    return new Portfolio(this.portfolioName, newStocks, newShares);
  }

  @Override
  public Portfolio removeStock(Stock stock, double numberOfShares) {
    String ticker = stock.getTicker();
    if (numberOfShares < 0) {
      throw new IllegalArgumentException("Shares cannot be negative");
    }
    List<Stock> newStocks = new ArrayList<>(this.stocks);
    List<Double> newShares = new ArrayList<>(this.shares);
    for (int i = 0; i < newStocks.size(); i++) {
      String otherTicker = newStocks.get(i).getTicker();
      if (otherTicker.equals(ticker)) {
        double currShares = newShares.get(i);
        if (numberOfShares > currShares) {
          throw new IllegalArgumentException("You do not have enough shares to remove the stock");
        }
        if (numberOfShares <= currShares - 1) {
          newShares.set(i, currShares - numberOfShares);
        } else {
          newStocks.remove(i);
          newShares.remove(i);
        }
        return new Portfolio(this.portfolioName, newStocks, newShares);
      }
    }
    throw new IllegalArgumentException("The inputted stock does not exist in this portfolio");
  }

  public String formatPortfolioAsFile() {
    return "";
  }

  @Override
  public List<LocalDate> getDatesInLog() {
    return null;
  }
}