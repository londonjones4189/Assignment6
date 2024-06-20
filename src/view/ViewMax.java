package view;

import java.io.IOException;

/**
 * New view that displays the new features that have been implmented to the controller and model.
 */
public class ViewMax extends AbstractView implements IViewMax {

  /**
   * Constructs the viewImpl.
   *
   * @param appendable represents entaken input
   */
  public ViewMax(Appendable appendable) {
    super(appendable);
  }

  @Override
  public void cePortolio() throws IOException {
    this.cePortfolioHelper();
    writeMessage("4.Determine composition of a portfolio (Type '4')" + System.lineSeparator());
    writeMessage("5.Determine distrubtion of a portfolio (Type '5')" + System.lineSeparator());
    writeMessage("8.Upload portfolio (Type 'upload')" + System.lineSeparator());
    writeMessage("9.Go back to menu (Type 'menu')" + System.lineSeparator());
    this.moveForward();
  }

  @Override
  public void editingExistingPortfolio() throws IOException {
    this.editingExistingPortfolioHelper();
    writeMessage("3.Sell stocks in a portfilo (Type '3')" + System.lineSeparator());
    writeMessage("4.Rebalance portfolio (Type '4')" + System.lineSeparator());
    this.moveForward();
  }

  @Override
  public void barChartMenu() {
    writeMessage("⎯⎯ Bar Chart Menu ⎯⎯" + System.lineSeparator());
    writeMessage("1.model.Stock Value overtime" + System.lineSeparator());
    writeMessage("2.model.Portfolio over time" + System.lineSeparator());
  }

  @Override
  public void printChart(String chart) {
    writeMessage(chart);
  }


  @Override
  public void printMenu() throws IOException {
    this.printMenuHelper2();
    writeMessage("3.Bar Menu (Type in 3)" + System.lineSeparator());
    writeMessage("4.Quit program (Type 'quit')" + System.lineSeparator());
    this.moveForward();
  }

  public void heading(String message) {
    writeMessage("⎯⎯" + message + "⎯⎯" + System.lineSeparator());
  }

  public void sell() {
    writeMessage("⎯⎯ Editing an existing portfolio ⎯⎯" + System.lineSeparator());
  }

  /**
   * Shows the user the rebalancing portfolio.
   */
  public void rebalance() {
    writeMessage("⎯⎯ Rebalancing  portfolio ⎯⎯" + System.lineSeparator());
    writeMessage("To rebalance you can type in desired stock's ticker value and the percent "
            + " of said stock you would like your portfolio to contain");
    writeMessage("Once you've selected desires stocks with "
            + "desire values type in 'stop' to update + "
            + "portfolio");
  }

  public void enterPercent() {
    writeMessage("Enter percentage in format (0.XX):" + System.lineSeparator());
  }


  /**
   * Lets the user enter their rebalancing goals.
   * @throws IOException if the input/output is missing.
   */
  public void goals() throws IOException {
    writeMessage("Now it's time to enter in the goals you have for your portfolio"
            + System.lineSeparator());
    writeMessage("You will first be asked, to type in a ticker symbol them the "
            + "percentage you want"
            + System.lineSeparator());
    writeMessage("For that to be in your  folder. Every ticker symbol "
            + "must have a percentage system"
            + System.lineSeparator());
    writeMessage("Type in 'stop' when you have finished your lists of goals"
            + System.lineSeparator());
    this.enterTickerName();
  }


  public void comps(String left, String right) {
    writeMessage("Stocks:" + left + System.lineSeparator());
    writeMessage("values:" + right + System.lineSeparator());
  }


}
