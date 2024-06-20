package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import model.IModel;


import view.IView;

/**
 * The implementation of the controller. Controller receives text inputs from the user,
 * communicates with the model to perform calculations, and then transmits this information
 * to the view to be printed to a text-based interface.
 */
public class ControllerImpl extends AbstractController {
  private boolean quit;
  private Scanner scanner;

  /**
   * Public constructor for the controller.
   *
   * @param readable represents the input stream where users can interact with the program.
   * @param model    represents the model which performs the calculations for the program.
   * @param view     represents the display which program users can see.
   * @throws IOException if the input/output the method is trying read no longer exists
   *                     or has been moved to another directory.
   */
  public ControllerImpl(Readable readable, IModel model, IView view) throws IOException {
    super(readable, model, view);
    this.scanner = new Scanner(readable);
  }
  @Override
  public void startProgram() throws IOException, ParserConfigurationException,
          TransformerException {
    // print the welcome message
    this.view.welcomeMessage();
    // as long as the user has not quit the program, the program will keep running
    while (!this.quit) {
      this.view.printMenu();
      String input = scanner.next();
      ; //in methods quits
      switch (input.toLowerCase()) {
        case "1":
          // allow the user to examine individual stocks
          this.examineStocksMenu(scanner);
          break;
        case "2":
          // allow the user to create a portfolio
          this.createPortfolioMenu(scanner);
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
          // error due to unrecognized instruction
          this.view.undefinedInstructions(input);
      }
    }
  }


  // allows the user to interact with the editing portfolio menu
  // If the user enters 1 -> they can add shares to the portfolio
  // If the user enters 2 -> they can remove shares from the portfolio
  protected void editingPortfolioMenu(String input, String portfolioName) throws IOException {
    Scanner sc = new Scanner(readable);
    switch (input) {
      case "1":
        this.addSharesToPortfolio(sc, portfolioName);
        break;
      case "2":
        this.removeSharesFromPortfolio(sc, portfolioName);
        break;
      case "quit":
        this.quit = true;
        break;
      default:
        this.view.undefinedInstructions(input);
        break;
    }
  }

  protected void menuportfolio(Scanner sc, List<String> portfolioList) throws IOException {
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


  // allows the user to input the stock and number of shares to add to a given portfolio
  private void addSharesToPortfolio(Scanner sc, String portfolioName) throws IOException {
    try {
      this.view.enterTickerName();
      String ticker = this.checkStock(sc);
      this.view.enterNumShares("add");
      int numShares = this.formatNum(sc.next());
      this.view.printResults("", this.model.addStock(ticker, numShares, portfolioName), 0);
    } catch (IllegalArgumentException e) {
      this.view.invalidInput();
    }
  }

  // allows a user to input the stock and number of shares to remove from a given portfolio
  private void removeSharesFromPortfolio(Scanner sc, String portfolioName) throws IOException {
    try {
      this.view.enterTickerName();
      String ticker2 = this.checkStock(sc);
      this.view.enterNumShares("remove");
      int numShares2 = this.formatNum(sc.next());
      this.view.printResults("",
              this.model.removeStock(ticker2, numShares2, portfolioName), 0);
    } catch (IllegalArgumentException e) {
      this.view.invalidInput();
    }
  }

  // allows the user to compute the value of a specific portfolio
  protected void examinePortfolioValue(Scanner sc, List<String> portfolioList) throws IOException {
    String portfolioListStr = this.formatListString(this.model.getPortfolioList());
    this.view.showExistingPorfolios(portfolioListStr);
    this.view.enterPortfolioName();
    String portfolioName = sc.next();
    String dateStr = this.dateBuilder(sc);
    LocalDate date = this.formatDate(dateStr);
    this.view.printResults("examineGL", this.model.getValue(portfolioName, date), 0);
  }
}