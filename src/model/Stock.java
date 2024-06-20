package model;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a stock that can be traded on the NYSE.
 */
public interface Stock {
  /**
   * Returns the value of a stock on a specified date.
   *
   * @param date    represents the date used to find the stock's value.
   * @param valType represents the type of value being found.
   *                For example, the file contains the opening,
   *                starting, high, and low value of the stock.
   * @return the value of the stock on that date.
   */
  double getStockValues(String date, String valType);

  /**
   * Finds the line X number of days before a specified date. Used as a helper for the
   * X-Day-Crossover and X-Day-average calculations.
   *
   * @param date       represents the start of the specified date period.
   * @param daysBefore represents the number of days before the method should search.
   * @return the string with the data for the date X days before.
   */
  String findLineDaysBefore(String date, int daysBefore);

  /**
   * Returns the ticker of the stock, tickers are sourced from the NYSE.
   *
   * @return the ticker of the stock.
   */
  String getTicker();

  List<String> getStockDates() throws IllegalArgumentException;
}
