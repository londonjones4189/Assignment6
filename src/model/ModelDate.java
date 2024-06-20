package model;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * New model that extends date compatibility for the program. Allows the user to rebalance
 * their portfolio, and buy and sell on specific dates.
 */
public class ModelDate extends ModelImpl implements IModelMax {
  private final Map<String, IPortfolioMax> portfolios;

  /**
   * Public constructor for the model.
   *
   * @throws IOException if the expected input/output is missing or not in the expected location.
   */
  public ModelDate() throws IOException {
    super();
    this.portfolios = new HashMap<>();
    for (Map.Entry<String, IPortfolio> entry : super.portfolioList.entrySet()) {
      this.portfolios.put(entry.getKey(), (IPortfolioMax) entry.getValue());
    }

    updatePortfolios();
  }

  /**
   * Lets the user create a new portfolio.
   *
   * @param portfolioName represents the name of the new Portfolio.
   * @throws ParserConfigurationException if the file to be parsed is missing.
   * @throws IOException                  if the input/output is missing.
   * @throws TransformerException         if the path is missing.
   */
  public void createPortfolio(String portfolioName)
          throws ParserConfigurationException, IOException, TransformerException {
    for (Map.Entry<String, IPortfolioMax> entry : this.portfolios.entrySet()) {
      String key = entry.getKey();
      if (portfolioName.equals(key)) {
        throw new IllegalArgumentException("No duplicate names");
      }
    }
    IPortfolioMax newPortfolio = new PortfolioDate(portfolioName);
    this.portfolios.put(portfolioName, newPortfolio);
    newPortfolio.generatePortfolioXML();
  }

  /**
   * Updates the list of portfolios based on the list of saved portfolios
   * from previous times the program was run.
   */
  public void updatePortfolios() throws IOException {
    File folder = new File("res/portfolios/");
    File[] files = folder.listFiles();

    for (File file : files) {
      if (file.isFile() && file.getName().endsWith(".xml")) {
        IPortfolioMax portfolio = this.parseXML(file, file.getName()
                .toString()
                .substring(0, file.getName().length() - 4));
        portfolios.put(portfolio.getPortfolioName(), portfolio);
      }
    }
  }

  public List<IPortfolioMax> getPortfolios() {
    return new ArrayList<>(portfolios.values());
  }


  /**
   * Allows the user to sell stocks from their portfolio on a specific date.
   * @param portfolioname represents the name of the portfolio.
   * @param ticker represents the stock's ticker.
   * @param numShares represents the number of shares being sold.
   * @param date represents the date of the sale.
   * @throws IOException if the input/output is missing.
   */
  public void sell(String portfolioname, String ticker, double numShares, LocalDate date)
          throws IOException, ParserConfigurationException, TransformerException {
    IPortfolioMax portfolio = this.portfolios.get(portfolioname);
    Stock stock = findTicker(ticker);
    IPortfolioMax newPort = portfolio.removeStockNew(date, stock, numShares);
    newPort.generatePortfolioXML();
    portfolios.put(portfolioname, newPort);
  }


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
  public void buy(String portfolioname, String ticker, double numShares, LocalDate date)
          throws IOException, ParserConfigurationException, TransformerException {
    IPortfolioMax portfolio = this.portfolios.get(portfolioname);
    Stock stock = findTicker(ticker);
    IPortfolioMax newPort = portfolio.addStockNew(date, stock, numShares);
    newPort.generatePortfolioXML();
    portfolios.put(portfolioname, newPort);
  }


  /**
   * Allows the user to find the composition of their portfolio.
   * @param date represents the date the composition of the portfolio is being found.
   * @param portfolioName represents the name of the portfolio.
   * @return A Pair where the left side represents a stock and
   *     the right side represents the number of shares in a portfolio.
   */
  @Override
  public Pair<String, String> composition(LocalDate date, String portfolioName) {
    IPortfolioMax portfolio = this.portfolios.get(portfolioName);
    ArrayList<Pair<String, Double>> lop = portfolio.compostion(date);
    return listMaker(lop);
  }

  /**
   * Returns a pair representing 2 lists, a list of stocks, and
   * then a list of values of the stocks.
   *
   * @param date date which disturbtion is being calculated.
   * @param portfolioName name of said portfolio.
   * @return a pair of 2 lists.
   */
  public Pair<String, String> distrubtion(LocalDate date, String portfolioName) {
    IPortfolioMax portfolio = this.portfolios.get(portfolioName);
    ArrayList<Pair<String, Double>> lop = portfolio.distrubtion(date);
    return listMaker(lop);
  }


  //Helper helps creates 2 lists of strings, used for distrubtion and compsotion to turn
  //ArrayList<String,Doubles> -> a pair of 2 strings
  private Pair<String, String> listMaker(ArrayList<Pair<String, Double>> lop) {
    String stocks = "";
    String value = "";
    int numberCount = 1;

    if (lop.size() < 0) {
      //question
    }

    for (Pair<String, Double> p : lop) {
      stocks = stocks + " " + (numberCount) + ".)" + p.getLeft();
      value = value + " " + (numberCount) + ".)" + (p.getRight());
      numberCount++;
    }
    return new Pair<String, String>(stocks, value);
  }

  /**
   * Allows the user to rebalance their portfolio to match their goal weights.
   * @param date represents the date the portfolio is being rebalanced on.
   * @param portfolioName represents the name of the portfolio.
   * @param goals represents the goal weights of the portfolios.
   * @return a new Portfolio with the weights changed.
   * @throws ParserConfigurationException if the file parsed is missing.
   * @throws IOException if the input/output is missing.
   * @throws TransformerException if there is a path missing.
   */
  @Override
  public IPortfolioMax reblance(LocalDate date, String portfolioName, Map<String, Double> goals)
          throws ParserConfigurationException, IOException, TransformerException {
    check100(goals);
    Map<Stock, Double> goalStock = new HashMap<>();
    for (String key : goals.keySet()) {
      goalStock.put(findTicker(key), goals.get(key));
    }
    System.out.print(portfolios.toString());
    System.out.println(this.getPortfolioList().toString());
    IPortfolioMax portfolio = portfolios.get(portfolioName);
    //todo saying empty here
    IPortfolioMax newPortfolio = portfolio.rebalance(goalStock, date);
    return newPortfolio;
  }


  //Checks whether or not values in a list of doubles
  //adds up to be exactly 1 if not throws an error
  private void check100(Map<String, Double> data) {
    Set<String> percents = data.keySet();
    double result = 0;
    for (String percent : percents) {
      result += data.get(percent);
    }
    if (result != 1) {
      throw new IllegalArgumentException("Values must add up too 1.0(100%)");
    }
  }

  @Override
  public List<String> getPortfolioList() {
    List<String> strList = new ArrayList<String>();
    for (Map.Entry<String, IPortfolioMax> entry : this.portfolios.entrySet()) {
      strList.add(entry.getKey());
    }
    return strList;
  }

  @Override
  // checks if the portfolio is in the given list
  public IPortfolio findPortfolio(String name) throws IllegalArgumentException {
    if (!portfolios.containsKey(name)) {
      throw new IllegalArgumentException("Portfolio " + name + " not found");
    }
    return portfolios.get(name);
  }
}


