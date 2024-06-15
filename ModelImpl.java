package model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Represents the implementation of the model. Performs computations and stores data about the
 * Stock program.
 */
public class ModelImpl implements IModel {
  protected final List<String> tickerList;
  protected final Map<String, Stock> tickerToStock;
  protected final Map<String, IPortfolio> portfolioList;


  /**
   * Public constructor for the model.
   *
   * @throws IOException if the expected input/output is missing or not in the expected location.
   */
  public ModelImpl() throws IOException {
    this.tickerList = Files.readAllLines(Paths.get("res/tickerList.txt"));
    this.tickerToStock = new HashMap<>();

    for (String ticker : tickerList) {
      try {
        tickerToStock.put(ticker, new SingleStock(ticker,
                Files.readAllLines(Paths.get("res/stock data/" + ticker + ".txt"))));
      } catch (IOException e) {
        this.downloadStockInfo("JRHGL8Z0YLRSGDYK", ticker);
      }
    }
    this.portfolioList = this.loadPortfolioList();
  }

  // retrieves the previous portfolios created by the program
  private List<String> getPreviousPortfolio() throws IOException {
    List<String> portfolioName = new ArrayList<>();
    try {
      portfolioName = Files.readAllLines(Paths.get("res/portfolios/savedPortfolios.txt"));
      return portfolioName;
    } catch (IOException e) {
      return portfolioName;
    }
  }

  // loads the previous portfolios so they could be used by the program
  private HashMap<String, IPortfolio> loadPortfolioList() throws IOException {
    HashMap<String, IPortfolio> portfolioList = new HashMap<>();
    List<String> portfolioNameList = this.getPreviousPortfolio();
    for (String portfolioName : portfolioNameList) {
      File file = new File("res/portfolios/" + portfolioName + ".xml");
      portfolioList.put(portfolioName, this.parseXML(file));
    }
    return portfolioList;
  }

  /**
   * Parses through a saved portfolio in a .xml file.
   * Lets the program retrieve stored files.
   * @param file the file where the profile is stored.
   * @return the portfolio saved in the program.
   */
  protected IPortfolioMax parseXML(File file) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(new FileInputStream(file));
      Element root = document.getDocumentElement();
      String portfolioName = getElementValue(root, "name");
      HashMap<LocalDate, List<Transaction>> log = new HashMap<>();
      List<LocalDate> datesInPortfolio = this.datesInSavedPortfolio(document);
      for (LocalDate date : datesInPortfolio) {
        List<Transaction> transactions = new ArrayList<>();
        if (this.getTransaction(document, date) != null) {
          transactions.add(this.getTransaction(document, date));
        }
        log.put(date, transactions);
      }
      IPortfolioMax portfolio = new PortfolioDate(portfolioName, log);
      return portfolio;
    } catch (Exception e) {
      throw new IllegalArgumentException();
    }
  }

  // retrieves an element stored in the .xml file
  private String getElementValue(Element element, String tagName) {
    NodeList nodeList = element.getElementsByTagName(tagName);
    if (nodeList.getLength() > 0) {
      return nodeList.item(0).getTextContent();
    }
    return "Not an element";
  }

  // retrieves transactions stored in a saved portfolio
  private Transaction getTransaction(Document doc, LocalDate date) {
    Element root = doc.getDocumentElement();
    NodeList transDateList = root.getElementsByTagName("transDate");
    for (int i = 0; i < transDateList.getLength(); i++) {
      Node transDateNode = transDateList.item(i);
      if (transDateNode.getNodeType() == Node.ELEMENT_NODE) {
        Element transDateElement = (Element) transDateNode;
        if (date.toString().equals(transDateElement.getAttribute("LocalDate"))) {
          NodeList childNodes = transDateElement.getChildNodes();
          String ticker = null;
          String numShares = null;
          for (int j = 0; j < childNodes.getLength(); j++) {
            Node childNode = childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
              Element childElement = (Element) childNode;
              if (childElement.getNodeName().equals("ticker")) {
                ticker = childElement.getAttribute("ticker");
              } else if (childElement.getNodeName().equals("numShares")) {
                numShares = childElement.getAttribute("numShares");
              }
            }
            if (ticker != null && numShares != null) {
              return new Transaction(tickerToStock.get(ticker), Double.parseDouble(numShares));
            }
          }
        }
      }
    }
    return null;
  }

  // retrieves a list of dates saved in the portfolio
  private List<LocalDate> datesInSavedPortfolio(Document doc) {
    List<LocalDate> dateList = new ArrayList<>();
    // Root element
    Element root = doc.getDocumentElement();
    // Get the transaction dates
    NodeList transDateList = root.getElementsByTagName("transDate");
    for (int i = 0; i < transDateList.getLength(); i++) {
      Node transDateNode = transDateList.item(i);
      if (transDateNode.getNodeType() == Node.ELEMENT_NODE) {
        Element transDateElement = (Element) transDateNode;
        String date = transDateElement.getAttribute("LocalDate");
        dateList.add(LocalDate.parse(date));
      }
    }
    return dateList;
  }

  // if the file does not exist, a new .CSV file will be created from using data from the
  // alpha vantage api
  private void downloadStockInfo(String apiKey, String stockSymbol) throws IOException {
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
    FileWriter myWriter;
    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {
      File myObj = new File("res/stock data/" + stockSymbol + ".txt");
      myWriter = new FileWriter(myObj, true);
      in = url.openStream();
      int b;
      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      myWriter.append(output);
    } catch (IOException e) {

      throw new IllegalArgumentException("No price data found for " + stockSymbol);
    }
  }

  /**
   * Returns the stock represented by a ticker. If the stock is not already saved to the program,
   * the program will contact the API to receive the data.
   * @param tickerCompName represents the ticker name of the stock.
   * @return a Stock with the given ticker.
   * @throws IllegalArgumentException if ticker is not supported by the program or the API.
   */
  @Override
  public Stock findTicker(String tickerCompName) throws IllegalArgumentException {
    if (tickerToStock.containsKey(tickerCompName)) {
      return tickerToStock.get(tickerCompName);
    } else {
      try {
        Stock newStock = new SingleStock(tickerCompName, "JRHGL8Z0YLRSGDYK");
        tickerToStock.put(tickerCompName, newStock);
        return tickerToStock.get(tickerCompName);
      } catch (IOException e) {
        throw new IllegalArgumentException("Input is neither a company name or a company's ticker");
      }
    }
  }

  @Override
  // checks if the portfolio is in the given list
  public IPortfolio findPortfolio(String name) throws IllegalArgumentException {
    if (!portfolioList.containsKey(name)) {
      throw new IllegalArgumentException("Portfolio " + name + " not found");
    }
    return portfolioList.get(name);
  }

  @Override
  public void createPortfolio(String portfolioName) throws IllegalArgumentException,
          ParserConfigurationException, IOException, TransformerException {
    if (portfolioList.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Portfolio " + portfolioName + " already exists");
    }
    portfolioList.put(portfolioName, new Portfolio(portfolioName));
  }

  @Override
  public String addStock(String ticker, int numShares, String portfolioName)
          throws IllegalArgumentException {
    if (numShares <= 0) {
      throw new IllegalArgumentException("Number of shares cannot be negative");
    }
    Stock stock = findTicker(ticker);
    IPortfolio portfolio = findPortfolio(portfolioName);
    portfolioList.put(portfolioName, portfolio.addStock(stock, numShares));
    if (numShares == 1) {
      return numShares + " share from " + ticker + " added to " + portfolioName;
    } else {
      return numShares + " shares from " + ticker + " added to " + portfolioName;
    }
  }

  @Override
  public String removeStock(String ticker, int numberOfShares, String portfolioName) {
    findPortfolio(portfolioName);
    Stock stock = findTicker(ticker);
    IPortfolio portfolio = portfolioList.get(portfolioName);
    portfolioList.put(portfolioName, portfolio.removeStock(stock, numberOfShares));
    if (numberOfShares == 1) {
      return numberOfShares + " share from " + ticker + " removed from " + portfolioName;
    } else {
      return numberOfShares + " shares from " + ticker + " removed from " + portfolioName;
    }

  }

  @Override
  public String getValue(String portfolioName, LocalDate date) {
    DecimalFormat df = new DecimalFormat("0.00");
    IPortfolio portfolio = findPortfolio(portfolioName);
    double result = portfolio.getPortfolioValue(date); //rename for portfolio
    return "$" + df.format(result);
  }

  @Override
  public String examineGainLossDate(String ticker, LocalDate earlierDate, LocalDate laterDate)
          throws IllegalArgumentException {
    if (!earlierDate.isBefore(laterDate)
            && !earlierDate.equals(laterDate)) {
      throw new IllegalArgumentException("Earlier date must be before later date");
    }
    try {
      Stock s = this.findTicker(ticker);
      String endStr = this.dateToString(laterDate);
      String startStr = this.dateToString(earlierDate);
      double priceDiff = s.getStockValues(endStr, "close")
              - s.getStockValues(startStr, "close");
      DecimalFormat df = new DecimalFormat("0.00");
      if (priceDiff > 0) {
        return "Stock gained value, stock value increased by $"
                + df.format(priceDiff) + " \uD83D\uDCC8";
      } else if (priceDiff == 0 || (priceDiff - 0 < 0.001 && priceDiff > -0.001)) {
        return "Stock stayed the same \uD83D\uDFF0, value is $"
                + s.getStockValues(endStr, "close");
      } else {
        return "Stock lost value, stock decreased by $"
                + df.format(priceDiff) + " \uD83D\uDCC9";
      }
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Ticker is not supported by program");
    }
  }

  // converts a line of string from the array into a LocalDate
  private LocalDate stringToDate(String dateStr) {
    if (dateStr.contains(",")) {
      return LocalDate.parse(dateStr.substring(0, dateStr.indexOf(",")));
    }
    return LocalDate.parse(dateStr);
  }

  // converts a LocalDate into a formatted string that can be searched
  // in the stock's array value
  private String dateToString(LocalDate date) {
    return date.getYear() + "-" + String.format("%02d", date.getMonthValue())
            + "-" + String.format("%02d", date.getDayOfMonth());
  }

  @Override
  public List<String> examCrossover(String ticker, LocalDate startingDate, int numDaysBefore) {
    Stock s = tickerToStock.get(ticker);
    String dateStr = this.dateToString(startingDate);
    String pastDate;
    List<LocalDate> crossoverDates = new ArrayList<>();
    for (int i = 0; i < numDaysBefore; i++) {
      pastDate = s.findLineDaysBefore(dateStr, i);
      double xDayAvg = Double.parseDouble(this.examXAvgOverDays(ticker,
              this.stringToDate(pastDate), numDaysBefore));
      if (s.getStockValues(pastDate, "close") > xDayAvg) {
        crossoverDates.add(this.stringToDate(pastDate));
      }
    }
    List<String> crossoverDatesStr = new ArrayList<String>();
    for (LocalDate date : crossoverDates) {
      crossoverDatesStr.add(this.dateToString(date));
    }
    return crossoverDatesStr;
  }

  @Override
  public String examXAvgOverDays(String ticker, LocalDate startingDate, int numDaysBefore) {
    try {
      Stock s = this.findTicker(ticker);
      if (numDaysBefore <= 0) {
        throw new IllegalArgumentException("Days cannot be negative or 0");
      }
      String dateStr = this.dateToString(startingDate);
      double total = 0;
      String pastDate;
      if (numDaysBefore == 1) {
        numDaysBefore += 1;
      }
      for (int i = 0; i < numDaysBefore; i++) {
        pastDate = s.findLineDaysBefore(dateStr, i);
        s.getStockValues(pastDate, "close");
        total += s.getStockValues(pastDate, "close");
      }
      DecimalFormat df = new DecimalFormat("0.00");
      return df.format(total / numDaysBefore);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Ticker is not supported by program");
    }
  }

  @Override
  public List<String> getPortfolioList() {
    List<String> strList = new ArrayList<String>();
    for (Map.Entry<String, IPortfolio> entry : this.portfolioList.entrySet()) {
      strList.add(entry.getKey());
    }
    return strList;
  }
}
