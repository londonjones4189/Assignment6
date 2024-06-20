package model;

import java.time.LocalDate;

/**
 * Represents a transaction on a portfolio. model.Transaction contains the number of shares and
 * the stock bought or sold.
 */
public class Transaction {
  private final Stock stock;
  private final double numShares;

  Transaction(Stock stock, double numShares) throws IllegalArgumentException {
    if (stock == null || numShares < 0) {
      throw new IllegalArgumentException("Stock and numShares must be non-negative and exist");
    }
    this.stock = stock;
    this.numShares = numShares;
  }

  public double getValue(LocalDate date, String valType) {
    return this.numShares * this.stock.getStockValues(date.toString(), valType);
  }


  public Stock getStock() {
    return this.stock;
  }

  public String getTicker() {
    return this.stock.getTicker();
  }

  public double getNumShares() {
    return this.numShares;
  }
}
