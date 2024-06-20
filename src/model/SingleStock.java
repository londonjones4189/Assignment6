package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents an individual company's stock to be traded on the stock market.
 */
public class SingleStock implements Stock {
  private final String ticker;
  private final List<String> stockValueFile;

  /**
   * Represents an individual stock.
   * @param ticker represents the stock's NYSE ticker.
   * @param stockValue represents the data about the stock stored in its
   *                   .txt file as a list of string that can be searched by the program
   * @throws IOException is thrown if the input/output the program is expecting
   *     is missing/changed location.
   */
  public SingleStock(String ticker, List<String> stockValue) throws IOException {
    this.ticker = ticker;
    this.stockValueFile = stockValue;
  }

  public SingleStock(String ticker, String apiKey) throws IOException {
    this.ticker = ticker;
    this.stockValueFile = this.getStockFromAPI(ticker, apiKey);
  }

  private List<String> getStockFromAPI(String stockSymbol, String apiKey) throws IOException {
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new RuntimeException("the API has either changed or "
              + "no longer works");
    }
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }

      String sbString = output.toString();

      String[] linesArray = sbString.split("\n");

      List<String> linesList = Arrays.asList(linesArray);
      if (linesList.get(1).contains("Error Message")) {
        throw new IllegalArgumentException("Stock doesn't exist");
      }
      return linesList;

    } catch (IOException e) {
      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
  }

  @Override
  public String getTicker() {
    return this.ticker;
  }

  // finds the line where a date in present in a .txt file containing the data
  // of a specific stock, returns that line if the date is found
  private String findLine(String date) {
    for (String string : this.stockValueFile) {
      if (string.startsWith(date)) {
        return string;
      }
    }
    return "";
  }

  @Override
  public String findLineDaysBefore(String date, int daysBefore) {
    for (int i = 0; i < this.stockValueFile.size(); i++) {
      String string = this.stockValueFile.get(i);
      if (string.startsWith(date) && this.stockValueFile.size() > i + daysBefore) {
        return this.stockValueFile.get(i + daysBefore);
      }
    }
    return "";
  }

  @Override
  public double getStockValues(String date, String valType) throws IllegalArgumentException {
    String str = this.findLine(date);
    if (str.isEmpty()) {
      throw new IllegalArgumentException("Date not found");
    }
    String[] array = str.split(",");
    try {
      switch (valType.toLowerCase()) {
        case "open":
          return Double.parseDouble(array[1]);
        case "high":
          return Double.parseDouble(array[2]);
        case "low":
          return Double.parseDouble(array[3]);
        case "close":
          return Double.parseDouble(array[4]);
        default:
          return 0; }
    } catch (IndexOutOfBoundsException e) {
      return 0;
    }
  }

  @Override
  public List<String> getStockDates() throws IllegalArgumentException {
    List<String> datesAvailable = new ArrayList<>();
    for (int i = 0; i < this.stockValueFile.size(); i++) {
      String string = this.stockValueFile.get(i);
      try {
        LocalDate localDate = LocalDate.parse(string.split(",")[0]);
        datesAvailable.add(localDate.toString());
      } catch (Exception ignored) {
      }
    }
    return datesAvailable;
  }
}
