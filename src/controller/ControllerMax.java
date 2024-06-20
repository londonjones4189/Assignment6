package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.PortfolioGraphStats;
import model.StockGraphStats;
import view.IViewMax;
import model.IModelMax;
import model.Pair;

/**
 * Represents the controller used for part 2 of the assignment. This controller extends
 * the functionality of the earlier assignment, and allows users to access the new featuers.
 */
public class ControllerMax extends ControllerImpl {
  boolean quit;
  Scanner scanner;
  Readable readable;
  IModelMax model;
  IViewMax view;

  /**
   * Public constructor for the class.
   *
   * @param readable represents the input source.
   * @param model    represents the model used for calculations.
   * @param view     represents the output source.
   * @throws IOException if the input/output is missing.
   */
  public ControllerMax(Readable readable, IModelMax model, IViewMax view) throws IOException {
    super(readable, model, view);
    this.readable = readable;
    this.model = model;
    this.view = view;
    this.scanner = new Scanner(readable);
  }


  private void newAddSharesToPortfolio(Scanner sc, String portfolioName) throws IOException {
    try {
      this.view.examineEnterDate();
      String startDate = this.dateBuilder(sc);
      LocalDate date = this.formatDate(startDate);
      String ticker = this.checkStock(sc);
      this.view.enterNumShares("add");
      int numShares = this.formatNum(sc.next());
      this.view.printResults("", this.model.addStock(ticker, numShares, portfolioName), 0);
    } catch (IllegalArgumentException e) {
      this.view.invalidInput();
    }
  }

  //Allows user to look at the distribution of given portfolio
  private void distribution(Scanner scanner) throws IOException {
    try {
      this.view.heading("Distrubtion");
      String dateStr = this.dateBuilder(scanner);
      LocalDate date = this.formatDate(dateStr);
      this.view.enterPortfolioName();
      String portfolioName = scanner.next();
      Pair<String, String> newPair = this.model.distrubtion(date, portfolioName);
      this.view.comps(newPair.getLeft(), newPair.getRight());
    } catch (NullPointerException e) {
      this.view.invalidInput();
    }

  }

  //Allows user to look at the composition of a given portfolio
  private void composition(Scanner scanner) throws IOException {
    try {
      this.view.heading("Compostion");
      String dateStr = this.dateBuilder(scanner);
      LocalDate date = this.formatDate(dateStr);
      this.view.enterPortfolioName();
      String portfolioName = scanner.next();
      Pair<String, String> newPair = this.model.composition(date, portfolioName);
      this.view.comps(newPair.getLeft(), newPair.getRight());
    } catch (NullPointerException e) {
      this.view.invalidInput();
    }
  }


  /**
   * Starts the controller and allows the user to go through the pretend stock agme.
   * @throws IOException throw an exception if something is not enetered.
   * @throws ParserConfigurationException throws an exception if soemthign can not be parsed.
   * @throws TransformerException throws an exception if something can not be transformed.
   */
  public void startProgram() throws IOException,
          ParserConfigurationException, TransformerException {
    // print the welcome message
    this.view.welcomeMessage();
    // as long as the user has not quit the program, the program will keep running
    while (!this.quit) {
      this.view.printMenu();
      String input = scanner.next();
      switch (input.toLowerCase()) {
        case "1":
          // allow the user to examine individual stocks
          this.examineStocksMenu(scanner);
          break;
        case "2":
          // allow the user to create a portfolio
          this.createPortfolioMenu();
          break;
        case "3":
          //allows readeers bar chart
          this.barChart(scanner);
          break;
        case "quit":
          // allow the user to quit the program
          this.view.farewellMessage();
          this.quit = true;
          break;
        case "menu":
          // allow the user to see the main menu
          this.view.printMenu();
          break;
        default:
          //error due to unrecognized instruction
          this.view.undefinedInstructions(input);
      }
    }
  }


  //Shows the barChart Menu as well as prints out
  //"bar charts" based on put in portfolio
  private void barChart(Scanner sc) throws IOException {
    this.view.barChartMenu();
    String input = scanner.next();
    switch (input) {
      case "1":
        // stock over time
        try {
          ///checks to see ticker is vaild
          String ticker = this.checkStock(scanner);
          //checks to see if beginning date is vaild
          boolean vaildStartDate = false;
          while (!vaildStartDate) {
            try {
              this.view.examineEnterDate();
              this.view.examineEnterEarlierDate();
              String startDate = this.dateBuilder(sc);
              LocalDate earlierDate = this.formatDate(startDate);
              this.view.examineEnterDate();
              this.view.examineEnterLaterDate();
              String laterDate = this.dateBuilder(sc);
              LocalDate endDate = this.formatDate(laterDate);
              StockGraphStats stock =
                      new StockGraphStats(this.model.findTicker(ticker), earlierDate, endDate);
              this.view.printChart(stock.createGraph());
              vaildStartDate = true;
            } catch (IllegalArgumentException e) {
              this.view.notInSystem();
            }
          }
        } catch (IllegalArgumentException e) {
          this.view.invalidInput();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        break;
      case "2":
        //portfolio over time
        try {
          ///checks to see ticker is vaild
          this.view.enterPortfolioName();
          String name = this.checkPortfolio(sc);
          //checks to see if beginning date is vaild
          boolean vaildStartDate = false;
          while (!vaildStartDate) {
            try {
              this.view.examineEnterDate();
              this.view.examineEnterEarlierDate();
              String startDate = this.dateBuilder(sc);
              LocalDate earlierDate = this.formatDate(startDate);
              this.view.examineEnterDate();
              this.view.examineEnterLaterDate();
              String laterDate = this.dateBuilder(sc);
              LocalDate endDate = this.formatDate(laterDate);
              PortfolioGraphStats port = new PortfolioGraphStats(this.model, name,
                      earlierDate, endDate);
              this.view.printChart(port.createGraph() + System.lineSeparator());
              vaildStartDate = true;
            } catch (IllegalArgumentException e) {
              this.view.notInSystem();
            }
          }
        } catch (IllegalArgumentException e) {
          this.view.invalidInput();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        break;
      default:
        this.view.undefinedInstructions(input);
        break;
    }
  }


  //the editing menu, showing options that modify a portfilo.
  protected void editingPortfolioMenu(Scanner sc) throws IOException {
    List<String> portfolioList = this.model.getPortfolioList();
    this.view.editingExistingPortfolio();
    String input = sc.next();
    switch (input) {
      case "1":
        // represents buying
        this.view.enterPortfolioName();
        String buyPortfolioName = sc.next();
        this.addSharesToPortfolio(sc, buyPortfolioName);
        break;
      case "2":
        // represents selling
        this.view.enterPortfolioName();
        String sellPortfolioName = sc.next();
        this.doesPortfolioExist(sc,sellPortfolioName, portfolioList);
        this.removeSharesFromPortfolio(sc, sellPortfolioName);
        break;
      case "3":
        this.rebalance(sc);
        break;
      case "menu":
        this.view.printMenu();
        break;
      default:
        this.view.undefinedInstructions(input);
        break;
    }
  }




  // allows a user to interact with the creation/editing portfolio menu
  // If the user enters 1 -> they can create a new portfolio
  // If the user enters 2 -> they can edit an existing portfolio
  // If the user enters 3 -> they can compute the value of an existing portfolio

  /**
   *  Allows a user to interact with the creation/editing portfolio menu.
   *  If the user enters 1 -> they can create a new portfolio.
   *  If the user enters 2 -> they can edit an existing portfolio.
   *  If the user enters 3 -> they can compute the value of an existing portfolio.
   * @throws IOException if the input/output is missing.
   * @throws ParserConfigurationException if the file being parsed is missing.
   * @throws TransformerException if the path is missing.
   */
  //todo option 2 of the main create/edit portfolios option
  public void createPortfolioMenu() throws IOException, ParserConfigurationException,
          TransformerException {
    //Scanner sc = new Scanner(readable);
    List<String> portfolioList = this.model.getPortfolioList();
    boolean goToMenu = false;
    while (!goToMenu) {
      this.view.cePortolio();
      String input = scanner.next();
      switch (input) {
        case "quit":
          this.view.farewellMessage();
          this.quit = true;
          break;
        case "menu":
          goToMenu = true;
          break;
        case "1":
          this.createNewPortfolio(scanner);
          break;
        case "2":
          // editing a portfolio
          this.editingPortfolioMenu(scanner);
          break;
        case "3":
          this.examinePortfolioValue(scanner, portfolioList);
          break;
        case "4":
          // represents getting composition
          this.composition(scanner);
          break;
        case "5":
          // represents getting distribution
          this.distribution(scanner);
          break;
        default:
          this.view.undefinedInstructions(input);
          break;
      }
    }
  }


  //todo make it so you can't enter in 2 stops of the same name
  //rebalances a given portfolio based on an array List of user's goals
  private void rebalance(Scanner sc) throws IOException {
    this.view.rebalance();
    Map<String, Double> newGoal = new HashMap<>();
    this.view.examineEnterDate();
    String dateStr = this.dateBuilder(scanner);
    LocalDate date = this.formatDate(dateStr);
    String name = this.checkPortfolio(scanner);
    this.view.goals();
    boolean stop = false;
    System.out.print(this.model.getPortfolioList().toString());
    try{
      while (!stop) {
        String ticker = this.checkStock(sc);
        if (ticker.equals("stop")) {
          stop = true;
          this.model.reblance(date, name, newGoal);
          break;
        }
        this.view.enterPercent();
        double percen2t = sc.nextDouble();
        newGoal.put(ticker, percen2t);
      }
    } catch (IOException | ParserConfigurationException | TransformerException e) {
      this.view.invalidInput();
    } catch (IllegalArgumentException e) {
      this.view.heading("Goal List completed");
      this.view.heading(name + " is now updated");
    }
  }


  @Override
  protected void examinePortfolioValue(Scanner scanner, List<String> portfolioList)
          throws IOException {
    String portfolioName = this.checkPortfolio(scanner);
    String dateStr = this.dateBuilder(scanner);
    LocalDate date = this.formatDate(dateStr);
    this.view.printResults("value:", this.model.getValue(portfolioName, date), 0);
  }

  //updated add shares also takes in date
  protected void addSharesToPortfolio(Scanner scanner, String name) throws IOException {
    try {
      String ticker2 = this.checkStock(scanner);
      this.view.enterNumShares("Buy");
      int numShares2 = this.formatNum(scanner.next());
      this.view.examineEnterDate();
      String dateStr = this.dateBuilder(scanner);
      LocalDate date = this.formatDate(dateStr);
      this.model.sell(name, ticker2, numShares2, date);
      this.view.sellCreated(ticker2, numShares2);
    } catch (NullPointerException e) {
      this.view.invalidInput();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  //updated remove shares also take sin date
  protected void removeSharesFromPortfolio(Scanner scanner, String name) throws IOException {
    try {
      String ticker2 = this.checkStock(scanner);
      this.view.enterNumShares("Sell");
      int numShares2 = this.formatNum(scanner.next());
      this.view.examineEnterDate();
      String dateStr = this.dateBuilder(scanner);
      LocalDate date = this.formatDate(dateStr);
      this.model.sell(name, ticker2, numShares2, date);
    } catch (IllegalArgumentException | IOException e) {
      this.view.invalidInput();
    } catch (ParserConfigurationException e) {
      throw new RuntimeException(e);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }
}
