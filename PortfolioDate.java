package model;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;


/**
 * Represents a portfolio that keeps a record. The portfolio has a log of transaction
 * so the user can see on what days they bought and sold stocks.
 */
public class PortfolioDate implements IPortfolioMax {
  private final String portfolioName;
  private final HashMap<LocalDate, List<Transaction>> transLog;

  /**
   * Public constructor.
   *
   * @param portfolioName represents the name of the portfolio.
   */
  public PortfolioDate(String portfolioName) {
    this.portfolioName = portfolioName;
    this.transLog = new HashMap<>();

  }

  PortfolioDate(String portfolioName,
                HashMap<LocalDate, List<Transaction>> log) {
    this.portfolioName = portfolioName;
    this.transLog = log;
  }

  public String getPortfolioName() {
    return this.portfolioName;
  }

  @Override
  public IPortfolio addStock(Stock stock, double numberOfShares) {
    return null;
  }

  @Override
  public IPortfolio removeStock(Stock stock, double numberOfShares)
          throws IllegalArgumentException {
    return null;
  }

  /**
   * Allows the user to buy stocks from their portfolio on a specific date.
   * @param date represents the date of the purchase.
   * @param stock represents the stock being bought.
   * @param shares  represents the number of shares being bought.
   * @return a new Portfolio with the new stock added.
   */
  public IPortfolioMax addStockNew(LocalDate date, Stock stock, double shares) {
    List<Transaction> entry = new ArrayList<>();
    List<LocalDate> datesSoFar = new ArrayList<>();
    HashMap<LocalDate, List<Transaction>> newLog = new HashMap<>();
    if (!this.transLog.isEmpty()) {
      newLog.putAll(this.transLog);
    }
    if (!transLog.isEmpty()) {
      datesSoFar.addAll(this.transLog.keySet());
    }

    if (transLog.containsKey(date)) {
      entry.addAll(transLog.get(date));

    }
    if (!datesSoFar.isEmpty()) {
      Transaction trans = new Transaction(stock, shares
              + this.mostRecentTrans(datesSoFar, stock));
      entry.add(trans);
    } else {
      Transaction trans = new Transaction(stock, shares);
      entry.add(trans);
    }

    newLog.put(date, entry);
    return new PortfolioDate(this.portfolioName, newLog);
  }

  private double mostRecentTrans(List<LocalDate> dates, Stock stock) {
    LocalDate mostRecentDate = dates.get(0);
    // go through the dates so far
    for (LocalDate date : dates) {
      if (date.isAfter(mostRecentDate)) {
        mostRecentDate = date;
      }
    }
    List<Transaction> dayLog = this.transLog.get(mostRecentDate);

    for (Transaction t : dayLog) {
      if (t.getStock().equals(stock)) {
        return t.getNumShares();
      }
    }
    return 0;
  }

  /**
   * Allows the user to sell stocks from their portfolio on a specific date.
   * @param date represents the date of the sale.
   * @param stock represents the stock being sold.
   * @param shares  represents the number of shares being sold.
   * @return  a new Portfolio with the stock removed.
   */
  public IPortfolioMax removeStockNew(LocalDate date, Stock stock, double shares) {
    // Invalidate dates after the given date
    this.invaild(date);
    // Ensure there is an entry for the current date
    this.currDate(date);
    // Get the most recent transactions before or on the given date
    List<Transaction> newLog = this.mostRecent(date);
    // Create a new HashMap to store updated transactions
    HashMap<LocalDate, List<Transaction>> updatedHash = new HashMap<>(this.transLog);
    double remainingShares = 0;
    boolean hasStock = false;

    // Iterate through the list of transactions
    for (int i = 0; i < newLog.size(); i++) {
      // Check if the current transaction matches the given stock
      if (newLog.get(i).getTicker().equals(stock.getTicker())) {
        hasStock = true;
        if (newLog.get(i).getNumShares() > shares) {
          remainingShares = newLog.get(i).getNumShares() - shares;
          newLog.set(i, new Transaction(stock, remainingShares));
        } else if (newLog.get(i).getNumShares() == shares) {
          // Handle the case where shares to be removed exceed or equal the current shares
          newLog.remove(i);
          i--; // Adjust index after removal
        } else {
          throw new IllegalArgumentException("Not enough stocks to remove");
        }
      }
    }
    // Check if the stock was found in the transactions
    if (!hasStock) {
      throw new IllegalArgumentException("Given stock not included in this portfolio");
    }

    // Update the transaction log for the given date
    updatedHash.put(date, newLog);

    // Return a new PortfolioDate with the updated transaction log
    return new PortfolioDate(this.portfolioName, updatedHash);
  }

  @Override
  public ArrayList<Pair<String, Double>> compostion(LocalDate date) {
    ArrayList<Pair<String, Double>> lop = new ArrayList<>();
    for (Transaction t : mostRecent(date)) {
      Pair<String, Double> newPair = new Pair<>(t.getTicker(), t.getNumShares());
      lop.add(newPair);
    }
    return lop;
  }

  @Override
  public ArrayList<Pair<String, Double>> distrubtion(LocalDate date) {
    ArrayList<Pair<String, Double>> lop = new ArrayList<>();
    for (Transaction t : mostRecent(date)) {
      Pair<String, Double> newPair = new Pair<>(t.getTicker(),
              t.getValue((mostRecentDate(date)), "close"));
      lop.add(newPair);
    }
    return lop;
  }

  /**
   * Allows the user to rebalance their portfolio to match their goal weights.
   * @param goalStock represents the goal weights of each stock.
   * @param date represents the date the portfolio is being rebalanced on.
   * @return  a new Portfolio with the stock weights rebalanced.
   */
  public IPortfolioMax rebalance(Map<Stock, Double> goalStock, LocalDate date) {
    //removes all invaild dates
    this.invaild(date);
    //Adds currDate if not already contained
    this.currDate(date);
    double currValue = this.getPortfolioValue(date);
    //variables
    List<Transaction> currTransactions = this.transLog.get(date);
    //checks to see if stocks are present
    for (Stock s : goalStock.keySet()) {
      if (!(stockContained(s, date))) {
        throw new IllegalArgumentException("Stock is not in this portfolio");
      }
    }
    //making hashamp (stock, goalValue)
    for (Map.Entry<Stock, Double> entry : goalStock.entrySet()) {
      goalStock.put(entry.getKey(), entry.getValue() * currValue);
    }
    //making hashMap (stock, goalShare) -> do this by didving goalValue by stock value amount
    for (Map.Entry<Stock, Double> entry : goalStock.entrySet()) {
      goalStock.put(entry.getKey(), entry.getValue()
              / entry.getKey().getStockValues(String.valueOf(date), "close"));
    }
    //new log
    List<Transaction> newLog = new ArrayList<>();
    for (Map.Entry<Stock, Double> entry : goalStock.entrySet()) {
      Transaction newTrans = new Transaction(entry.getKey(), entry.getValue());
      newLog.add(newTrans);
    }
    this.transLog.put(date, newLog);
    return new PortfolioDate(this.portfolioName, this.transLog);
  }


  //Returns the mostRecent date in the list of transaction
  private List<Transaction> mostRecent(LocalDate date) {
    List<LocalDate> beforeGiven = new ArrayList<>();
    for (LocalDate ld : this.transLog.keySet()) {
      if (ld.isBefore(date) || ld.isEqual(date)) {
        beforeGiven.add(ld);
      }
    }

    if (beforeGiven.isEmpty()) {
      return new ArrayList<>();
    }

    LocalDate mostRecent = Collections.max(beforeGiven);
    return new ArrayList<>(this.transLog.get(mostRecent));
  }

  //Puts the date into the hashmap with most recent transactions
  private void currDate(LocalDate date) {
    if (!this.transLog.containsKey(date)) {
      this.transLog.put(date, mostRecent(date));
    }
  }

  //Removes all invaid dates in map
  private void invaild(LocalDate date) {
    for (LocalDate ld : this.transLog.keySet()) {
      if (ld.isAfter(date)) {
        this.transLog.remove(ld);
      }
    }
  }

  //Removes all invaid dates in map
  private List<LocalDate> vaild(LocalDate date) {
    ArrayList<LocalDate> newLog = new ArrayList<>();
    for (LocalDate ld : this.transLog.keySet()) {
      if (ld.isBefore(date)) {
        newLog.add(ld);
      }
    }
    return newLog;
  }


  private LocalDate mostRecentDate(LocalDate date) {
    List<LocalDate> beforeOrOnGivenDate = new ArrayList<>();
    for (LocalDate ld : this.transLog.keySet()) {
      if (!ld.isAfter(date)) {
        beforeOrOnGivenDate.add(ld);
      }
    }
    LocalDate mostRecentDate = Collections.max(beforeOrOnGivenDate);
    return mostRecentDate;
  }


  //checks to see if a stock is contained in a list of transcatons, returns false if not
  private boolean stockContained(Stock stock, LocalDate date) {
    boolean result = false;
    for (Transaction t : this.transLog.get(date)) {
      if (t.getTicker().equals(stock.getTicker())) {
        result = true;
      }
    }
    return result;
  }

  private List<LocalDate> datesInLog() {
    List<LocalDate> dateList = new ArrayList<>();
    for (Map.Entry<LocalDate, List<Transaction>> entry : transLog.entrySet()) {
      dateList.add(entry.getKey());
    }
    return dateList;
  }

  private List<Transaction> transOnDate(LocalDate date) {
    List<Transaction> result = new ArrayList<>();
    for (Transaction t : transLog.get(date)) {
      result.add(t);
    }
    return result;
  }

  @Override
  public void generatePortfolioXML() {
    try {
      DocumentBuilderFactory dbFactory =
              DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.newDocument();

      // root element
      Element rootElement = doc.createElement("Portfolio");
      doc.appendChild(rootElement);

      // portfolio name
      Element name = doc.createElement("name");
      rootElement.appendChild(name);
      Attr attr = doc.createAttribute("portfolioName");
      attr.setValue(this.portfolioName);
      name.setAttributeNode(attr);

      List<LocalDate> datesInLog = datesInLog();
      for (LocalDate date : datesInLog) {
        List<Transaction> transList = transOnDate(date);
        // date of transaction element
        Element transDate = doc.createElement("transDate");
        rootElement.appendChild(transDate);
        Attr dateAttr = doc.createAttribute("LocalDate");
        dateAttr.setValue(date.toString());
        transDate.setAttributeNode(dateAttr);
        for (Transaction t : transList) {
          // ticker
          Element ticker = doc.createElement("ticker");
          transDate.appendChild(ticker);
          Attr tickerAttr = doc.createAttribute("ticker");
          tickerAttr.setValue(t.getTicker());
          ticker.setAttributeNode(tickerAttr);
          // num shares
          Element numShares = doc.createElement("numShares");
          transDate.appendChild(numShares);
          Attr sharesAttr = doc.createAttribute("numShares");
          sharesAttr.setValue(String.valueOf(t.getNumShares()));
          numShares.setAttributeNode(sharesAttr);
        }
      }

      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File("res/portfolios/"
              + this.portfolioName + ".xml"));
      transformer.transform(source, result);

    } catch (Exception e) {
      e.printStackTrace();
    }
    this.updateSavedPortfolioList(true);
  }


  private boolean nameAlreadyExists() {
    try {
      List<String> file = Files.readAllLines(Paths.get("res/portfolios/savedPortfolios.txt"));
      return (file.contains(this.portfolioName));
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  private void updateSavedPortfolioList(boolean shouldOverwrite) {
    FileWriter fw;
    if (this.nameAlreadyExists() && !shouldOverwrite) {
      return;
    }

    try {
      File file = new File("res/portfolios/savedPortfolios.txt");
      fw = new FileWriter(file, true);
      fw.write(this.portfolioName + System.lineSeparator());
      fw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public double getPortfolioValue(LocalDate date) {
    List<LocalDate> beforeGiven = new ArrayList();
    List<Transaction> newLog = this.mostRecent(date);
    if (this.transLog.isEmpty()) {
      return 0.0;
    } else {
      double result = 0;
      for (Transaction t : newLog) {
        try {
          result += t.getValue(this.mostRecentDate(date), "close");
        } catch (IllegalArgumentException ignored) {
        }
      }
      return result;
    }
  }

  @Override
  public List<LocalDate> getDatesInLog() {
    List<LocalDate> datesInLog = new ArrayList<>();
    for (Map.Entry<LocalDate, List<Transaction>> entry : this.transLog.entrySet()) {
      LocalDate date = entry.getKey();
      datesInLog.add(date);
    }
    Collections.sort(datesInLog);
    return datesInLog;
  }

}



