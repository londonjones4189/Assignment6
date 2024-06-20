package view;

import java.io.IOException;

/**
 * Represents the View implementation of the program. This class controls
 * what the user sees in the console.
 */
public class ViewImpl extends AbstractView {

  /**
   * Constructs the viewImpl.
   *
   * @param appendable represents input
   */
  public ViewImpl(Appendable appendable) {
    super(appendable);
  }

  @Override
  public void cePortolio() throws IOException {
    this.cePortfolioHelper();
    writeMessage("4.Go back to menu (Type 'menu')" + System.lineSeparator());
    this.moveForward();
  }


  public void printMenu() {
    this.printMenu();
    this.printMenuHelper();
  }

  @Override
  public void editingExistingPortfolio() throws IOException {
    this.editingExistingPortfolioHelper();
    this.moveForward();
  }

}
