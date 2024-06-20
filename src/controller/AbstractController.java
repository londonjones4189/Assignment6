package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.IModel;
import view.IView;

/**
 * Represents the Abstract controller. Used to reduce duplication between the original
 * controller and the new controller with new functionality.
 */

public abstract class AbstractController implements IController {
  Readable readable;
  IModel model;
  IView view;
  boolean quit;
  Scanner scanner;

  /**
   * Public constructor for the controller.
   *
   * @param readable represents the input stream where users can interact with the program.
   * @param model    represents the model which performs the calculations for the program.
   * @param view     represents the display which program users can see.
   * @throws IOException if the input/output the method is trying read no longer exists
   *                     or has been moved to another directory.
   */
  public AbstractController(Readable readable, IModel model, IView view) throws IOException {
    this.readable = readable;
    this.model = model;
    this.scanner = new Scanner(readable);
    this.view = view;
    this.quit = false;
  }

  /**
   * Allows a user to examine the gain and loss of a specific stock.
   *
   * @param sc represents the scanner used to detect the next input.
   * @throws IOException if the input/output source is missing.
   */
  protected void examineGainLoss(Scanner sc) throws IOException {
    this.view.examineGLStart();

    ///checks to see ticker is vaild
    String ticker = this.checkStock(sc);

    //checks to see if beginning date is vaild
    boolean vaildStartDate = false;
    while (!vaildStartDate) {
      try {
        this.view.examineEnterDate();
        this.view.examineEnterEarlierDate();
        String startDate = this.dateBuilder(sc);
        LocalDate earlierDate = this.formatDate(startDate);
        this.examineGLLater(sc, earlierDate, ticker);
        vaildStartDate = true;
      } catch (IllegalArgumentException e) {
        this.view.notInSystem();
      }
    }
  }

  /**
   * Determines if a ticker is vaild if not makes user retype in ticker symbol.
   *
   * @param sc represents the scanner used to detect the next input.
   * @return a String representing the model.Stock's ticker.
   * @throws IOException if the input/output source is missing.
   */
  protected String checkStock(Scanner sc) throws IOException {
    boolean vaildStock = false;
    String result = "";
    while (!vaildStock) {
      try {
        this.view.enterTickerName();
        result = String
                .valueOf(this.model.findTicker(String.valueOf(sc.next())
                                .toUpperCase())
                        .getTicker());
        vaildStock = true;
      } catch (IllegalArgumentException e) {
        this.view.notInSystemTicker();
      }
    }
    return result;
  }


  /**
   * Used to allow the user to examine the gain loss of a stock if
   * they mistype their original input.
   *
   * @param sc          represents the scanner used to detect the next input.
   * @param earlierDate is the earlier date in the range they are examining.
   * @param ticker      represents the ticker of a model.Stock.
   * @throws IOException if the input/output source is missing.
   */
  protected void examineGLLater(Scanner sc, LocalDate earlierDate, String ticker)
          throws IOException {
    boolean vaildEndDate = false;
    while (!vaildEndDate) {
      try {
        this.view.examineEnterDate();
        this.view.examineEnterLaterDate();
        String endDate = this.dateBuilder(sc);
        LocalDate laterDate = this.formatDate(endDate);
        String result = this.model.examineGainLossDate(ticker, earlierDate, laterDate);
        this.view.printResults("examGL", result, 0);
        vaildEndDate = true;
      } catch (IllegalArgumentException e) {
        this.view.notInSystem();
      }
    }
  }

  /**
   * Allows a user to examine the X-Day crossover of a specific stock.
   *
   * @param sc represents a scanner.
   * @throws IOException if the input/output is missing.
   */
  protected void examineXDayCrossover(Scanner sc) throws IOException {
    try {
      this.view.examineXDayCrossStart();
      String ticker1 = this.checkStock(sc);
      this.view.examineEnterDate();
      String date = this.dateBuilder(sc);
      LocalDate startDate = this.formatDate(date);
      this.view.enterNumDays();
      int numberOfDays = this.formatNum(sc.next());
      List<String> result = this.model.examCrossover(ticker1, startDate, numberOfDays);
      String str = this.formatListString(result);
      this.view.printResults("XDayCrossover", String.valueOf(str), numberOfDays);
    } catch (IllegalArgumentException e) {
      this.view.invalidInput();
    }
  }

  /**
   * Formats a list of string into a single line seperated by commas.
   *
   * @param list represents a list to be formatted.
   * @return a single string containing all the values of the list.
   */
  protected String formatListString(List<String> list) {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      if (i == list.size() - 1) {
        str.append(list.get(i));
      } else {
        str.append(list.get(i)).append(", ");
      }
    }
    if (list.isEmpty()) {
      return "None";
    }
    return String.valueOf(str);
  }

  public abstract void startProgram()
          throws IOException, ParserConfigurationException, TransformerException;


  /**
   * Allows the user to interact with the examine stock menu.
   * If the user enters 1 -> examine gain/loss of a specific stock.
   * If the user enters 2 -> X-Day crossover.
   * If the user enters 3 -> X-Day moving average.
   * Menu brings the user back to the start, and quit ends the program.
   *
   * @throws IOException if the input/output is missing.
   */
  protected void examineStocksMenu() throws IOException {
    boolean goToMenu = false;
    while (!goToMenu) {
      this.view.examineStocks();
      String input = scanner.next();
      switch (input) {
        case "menu":
          goToMenu = true;
          break;
        case "quit":
          this.view.farewellMessage();
          this.quit = true;
          break;
        case "1":
          this.examineGainLoss(scanner);
          break;
        case "2":
          this.examineXDayCrossover(scanner);
          break;
        case "3":
          this.examineXDayMovingAvg(scanner);
          break;
        default:
          this.view.undefinedInstructions(input);
      }
    }

  }

  // allows a user to examine the x-day moving average of a stock
  void examineXDayMovingAvg(Scanner sc) throws IOException {
    try {
      this.view.xDays();
      String ticker = this.checkStock(sc);
      this.view.examineEnterDate();
      String startDate = this.dateBuilder(sc);
      LocalDate date = this.formatDate(startDate);
      this.view.enterNumDays();
      int numberOfDays = this.formatNum(sc.next());
      String result = this.model.examXAvgOverDays(ticker, date, numberOfDays);
      this.view.printResults("XDayAvg", result, numberOfDays);
    } catch (IllegalArgumentException e) {
      this.view.invalidInput();
    }
  }

  // formats the number and throws an error if the format is invalid
  protected int formatNum(String numDaysStr) throws IllegalArgumentException {
    try {
      return Integer.parseInt(numDaysStr);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid number input");
    }
  }

  // formats the date and throws an error if the format is invalid
  protected LocalDate formatDate(String input) throws IllegalArgumentException {
    return LocalDate.parse(input);
  }

  void createNewPortfolio(Scanner sc)
          throws IOException, ParserConfigurationException, TransformerException {
    //creating portfolio
    String portfolioList = this.formatListString(this.model.getPortfolioList());
    this.view.showExistingPorfolios(portfolioList);
    this.view.makePortfolio();
    String portfolioName = sc.next();
    try {
      this.model.createPortfolio(portfolioName);
      this.view.portfolioCreated(portfolioName);
    } catch (IllegalArgumentException e) {
      this.view.invalidInput();
    }
  }


  protected void editPortfolio(Scanner sc, List<String> portfolioList) throws IOException {
    String portfolioListStr = this.formatListString(this.model.getPortfolioList());
    this.view.showExistingPorfolios(portfolioListStr);
    this.view.editingExistingPortfolio();
    String inputEdit = sc.next();
    this.view.showExistingPorfolios(portfolioListStr);
    this.view.editPortfolio();
    String portfolioName = sc.next();
    try {
      this.doesPortfolioExist(sc, portfolioName, portfolioList);
    } catch (IllegalArgumentException e) {
      return;
    }
    this.editingPortfolioMenu(inputEdit, portfolioName);
  }


  protected abstract void editingPortfolioMenu(String inputEdit, String portfolioName)
          throws IOException;


  //

  /**
   * Allows a user to interact with the creation/editing portfolio menu.
   * If the user enters 1 -> they can create a new portfolio.
   * If the user enters 2 -> they can edit an existing portfolio.
   * If the user enters 3 -> they can compute the value of an existing portfolio.
   * @throws IOException if the input/output is missing.
   * @throws ParserConfigurationException if the file used parse existing portfolios is misisng.
   * @throws TransformerException if there is a missing path or configuration problems.
   */
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
          this.editPortfolio(scanner, portfolioList);
          break;
        case "3":
          this.examinePortfolioValue(scanner, portfolioList);
          break;
        default:
          this.view.undefinedInstructions(input);
      }
    }
  }

  //determines value of a portfolio based on the type of portfolio
  protected abstract void examinePortfolioValue(Scanner scanner, List<String> portfolioList)
          throws IOException;

  /**
   * Verifies that the portfolio name the user provides already exists so they can edit
   * allowing the user to edit.
   *
   * @param sc represents the scanner.
   * @param portfolioName represents the name of the portfolio.
   * @param portfolioList represents the list of the portfolio.
   * @throws IOException if the input/output is missing.
   */
  protected void portfolioExist(Scanner sc, String portfolioName, List<String>
          portfolioList) throws IOException {
    if (!portfolioList.contains(portfolioName)) {
      this.view.portfolioError(portfolioList.size());
      throw new IllegalArgumentException("Invalid portfolio name");
    }
  }

  /**
   * Allows users to type in the date in a way that increases error by asking for
   * the date, month, and year separately. If users inputs invaild value, allows
   * them to retype in the value without having to retype in the whole date again.
   *
   * @param sc represents the scanner.
   * @return the formatted date.
   */
  protected String dateBuilder(Scanner sc) {
    String year = "";
    String finalDate = "";
    boolean validYear = false;
    while (!validYear) {
      try {
        this.view.year();
        year = sc.next();
        finalDate = year + "-" + String.format("%02d", Integer.parseInt("12")) + "-"
                + String.format("%02d", Integer.parseInt("01"));
        this.formatDate(finalDate);
        validYear = true;
      } catch (IllegalArgumentException | DateTimeParseException e) {
        this.view.invaildDate("year");
      }
    }
    return finalDate = this.monthBuilder(year, sc);
  }


  /**
   * Allows users to input the month and if inputted incorrectly allows them to
   * retype the month until the day is correct.
   * @param year represents the year of the date they want to input.
   * @param sc   represents the scanner.
   * @return a String with the formatted date.
   */

  private String monthBuilder(String year, Scanner sc) {
    String finalDate = "";
    String month = "";
    boolean validMonth = false;
    while (!validMonth) {
      try {
        this.view.month();
        month = sc.next();
        finalDate = year + "-" + String.format("%02d", Integer.parseInt(month)) + "-"
                + String.format("%02d", Integer.parseInt("01"));
        this.formatDate(finalDate);
        validMonth = true;
      } catch (IllegalArgumentException | DateTimeParseException e) {
        this.view.invaildDate("month");
      }
    }
    finalDate = this.dayBuilder(month, year, sc);
    return finalDate;
  }

  /**
   * Allows users to input the day and if inputted incorrectly allows them to
   * retype the day until the day is correct.
   *
   * @param month represents the month of the date they want to input.
   * @param year represents the year of the date they want to input.
   * @param sc represents the scanner.
   * @return a String with the formatted date.
   */
  public String dayBuilder(String month, String year, Scanner sc) {
    boolean validDay = false;
    String finalDate = "";
    while (!validDay) {
      try {
        this.view.day();
        String day = sc.next();
        finalDate = year + "-" + String.format("%02d", Integer.parseInt(month)) + "-"
                + String.format("%02d", Integer.parseInt(day));
        this.formatDate(finalDate); // Validate full date
        validDay = true;
      } catch (IllegalArgumentException | DateTimeParseException e) {
        this.view.invaildDate("day");
      }
    }
    return finalDate;
  }

  /**
   * Verifies that the portfolio name the user provides already exists before editing.
   * @param sc represents a scanner.
   * @param portfolioName represents the name of the portfolio.
   * @param portfolioList represents the list of currently existing portfolios.
   * @throws IOException if the input/output is missing.
   */
  protected void doesPortfolioExist(Scanner sc, String portfolioName, List<String>
          portfolioList) throws IOException {
    if (!portfolioList.contains(portfolioName)) {
      this.view.portfolioError(portfolioList.size());
      throw new IllegalArgumentException("Invalid portfolio name");
    }
  }

}

