import org.junit.Before;
import org.junit.Test;

import view.IView;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * Represents test for viewImpl.
 */
public class ViewImplTest {
  IView view;
  StringBuilder str;

  @Before
  public void setUp() throws Exception {
    str = new StringBuilder();
    view = new ViewImpl(str);
  }

  @Test
  public void testPrintResults1() throws Exception {
    view.printResults("examGL", "stock decreased", 0);
    assertEquals("Result: " + "stock decreased" + System.lineSeparator(),
            String.valueOf(view.getLog()));
  }

  @Test
  public void testPrintResults2() throws Exception {
    view.printResults("XDayCrossover", "2024-06-02",
            2);
    assertEquals("2 Day model.Stock Crossover, Dates that crossover: "
                    + "2024-06-02" + System.lineSeparator(),
            String.valueOf(view.getLog()));
  }

  @Test
  public void testPrintResults3() throws Exception {
    view.printResults("XDayAvg", "100", 2);
    assertEquals("2 Day model.Stock Average: $100" + System.lineSeparator(),
            String.valueOf(view.getLog()));
  }

  @Test
  public void testPrintResults4() throws Exception {
    view.printResults("", "100", 2);
    assertEquals("100" + System.lineSeparator(),
            String.valueOf(view.getLog()));
  }

  @Test
  public void testWelcome() throws Exception {
    view.welcomeMessage();
    assertEquals("⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯"
            + System.lineSeparator()
            + "Welcome to the Virtual model.Stock Market Simulator! \uD83D\uDCCA"
            + System.lineSeparator()
            + "This program is designed to help new investors "
            + "learn more about the stock market."
            + System.lineSeparator()
            + "This program has data from the past until 2024-06-03."
            + System.lineSeparator()
            + "Data is not available on weekends and national holidays."
            + System.lineSeparator()
            + "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testFarewell() throws Exception {
    view.farewellMessage();
    assertEquals("Thank you for using our program! Goodbye! \uD83D\uDC4B"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testMenu() throws Exception {
    view.printMenu();
    assertEquals("⎯⎯ Menu Options ⎯⎯"
            + System.lineSeparator()
            + "Would you like to examine individual stocks " +
            "or create/edit stock portfolios?"
            + System.lineSeparator()
            + "1.Examine stocks (Type '1')"
            + System.lineSeparator()
            + "2.Create/edit portfolios (Type '2')"
            + System.lineSeparator()
            + "3.Quit the program (Type 'quit')"
            + System.lineSeparator()
            + "Type in the number correlating with the option "
            + "you would like to move forward with"
            + System.lineSeparator()
            + "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void examineStocks() throws Exception {
    view.examineStocks();
    assertEquals("⎯⎯ Examining Individual Stocks Menu: ⎯⎯"
            + System.lineSeparator()
            + "1.Examine the gain/loss over a specific stock over time (Type '1')"
            + System.lineSeparator()
            + "2.Discover X-Day crossovers (Type '2')"
            + System.lineSeparator()
            + "3.Discover X-Day moving average (Type '3')"
            + System.lineSeparator()
            + "4.Go back to menu (Type 'menu')"
            + System.lineSeparator()
            + "5.Quit program (Type 'quit')"
            + System.lineSeparator()
            + "Type in the number correlating with the option you would like to select"
            + System.lineSeparator()
            + "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testExamineGLStart() throws Exception {
    view.examineGLStart();
    assertEquals("Examine the gain and loss of a specific stock \uD83D\uDD0E"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testExamineXDayCrossStart() throws Exception {
    view.examineXDayCrossStart();
    assertEquals("Examine the X-Day crossover of a specific stock \uD83D\uDD0E"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testXDays() throws Exception {
    view.xDays();
    assertEquals("Examine the X-day moving average of a specific stock \uD83D\uDD0E"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testEnterTickerName() throws Exception {
    view.enterTickerName();
    assertEquals("Type in the stock's ticker value: "
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testExamineEnterDate() throws Exception {
    view.examineEnterDate();
    assertEquals("Enter the date you wish to examine: "
            + System.lineSeparator()
            + "Proper date format is YYYY-MM-DD"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testExamineEnterEarlierDate() throws Exception {
    view.examineEnterEarlierDate();
    assertEquals("Type the starting (earlier) date: "
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testExamineEnterLaterDate() throws Exception {
    view.examineEnterLaterDate();
    assertEquals("Type the ending (later) date: "
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testCePortolio() throws Exception {
    view.cePortolio();
    assertEquals("⎯⎯ model.Portfolio Creation/Editing Menu ⎯⎯"
            + System.lineSeparator()
            + "1.Make a new portfolio (Type '1')"
            + System.lineSeparator()
            + "2.Edit an existing portfolio (Type '2')"
            + System.lineSeparator()
            + "3.See the value of an existing portfolio (Type '3')"
            + System.lineSeparator()
            + "4.Go back to menu (Type 'menu')"
            + System.lineSeparator()
            + "Type in the number correlating with the "
            + "option you would like to move forward with"
            + System.lineSeparator()
            + "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testMakePortfolio() throws Exception {
    view.makePortfolio();
    assertEquals("Make a new portfolio \uD83D\uDCC1"
            + System.lineSeparator()
            + "The new name can not be the same as an existing portfolio name"
            + System.lineSeparator()
            + "The name should be one word with no spaces"
            + System.lineSeparator()
            + "Enter a name for the portfolio: "
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testEnterPortfolioName() throws Exception {
    view.enterPortfolioName();
    assertEquals("The name should be one word with no spaces"
            + System.lineSeparator()
            + "Enter a name for the portfolio: "
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testEditPortfolio() throws Exception {
    view.editPortfolio();
    assertEquals("Editing an existing portfolio \uD83D\uDCDD"
            + System.lineSeparator()
            + "The name should be one word with no spaces"
            + System.lineSeparator()
            + "Enter a name for the portfolio: "
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void EditingExistingPortfolio() throws Exception {
    view.editingExistingPortfolio();
    assertEquals("⎯⎯ Editing an existing portfolio ⎯⎯"
            + System.lineSeparator()
            + "What changes would you like to make?"
            + System.lineSeparator()
            + "1.Add shares to an existing stock/add new stock (Type '1')"
            + System.lineSeparator()
            + "2.Remove a shares from an existing stock (Type '2')"
            + System.lineSeparator()
            + "Type in the number correlating with the option "
            + "you would like to move forward with"
            + System.lineSeparator()
            + "⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯⎯"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testUndefinedInstructions() throws Exception {
    view.undefinedInstructions("");
    assertEquals("Undefined instruction: "
            + "" + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testInvalidInput() throws Exception {
    view.invalidInput();
    assertEquals("⚠\uFE0F Invalid input, cancelling action"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testEnterNumDays() throws Exception {
    view.enterNumDays();
    assertEquals("Enter the number of days back you wish "
            + "to include in the crossover period: "
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testShowExistingPorfolios() throws Exception {
    view.showExistingPorfolios("");
    assertEquals("List of existing portfolios: "
            + "" + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testEnterNumShares() throws Exception {
    view.enterNumShares("add");
    assertEquals("Enter the number of shares you wish to "
            + "add"
            + ": " + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testPortfolioCreated() throws Exception {
    view.portfolioCreated("name");
    assertEquals("Success! ✅ New portfolio '" + "name" + "' added"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testPortfolioError1() throws Exception {
    view.portfolioError(0);
    assertEquals("❌ No portfolios found to edit/view!"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }

  @Test
  public void testPortfolioError2() throws Exception {
    view.portfolioError(1);
    assertEquals("❌ Error! That portfolio does not exist"
            + System.lineSeparator(), String.valueOf(view.getLog()));
  }
}
