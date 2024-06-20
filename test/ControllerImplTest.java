import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import controller.ControllerImpl;
import controller.IController;
import model.MockModel;
import view.MockView;

import static org.junit.Assert.assertEquals;

/**
 * Represents test for controller which
 * take in mockView and a mockModel.
 */
public class ControllerImplTest {
  private MockModel mockModel;
  private MockView mockView;
  private String log;
  private String log2;

  @Before
  public void setUp() throws Exception {
    mockModel = new MockModel(new StringBuilder());
    mockView = new MockView(new StringBuilder());
    log = "";
    log2 = "";
  }

  @Test
  public void testControllerQuit() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader inputQuit = new StringReader("quit");
    IController controller = new ControllerImpl(inputQuit, mockModel, mockView);
    log = " (welcomeMessage)  (printMenu)  (farewellMessage) ";
    log2 = "";
    controller.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());
  }

  @Test
  public void testControllerPrintMenuFrom1() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader input0 = new StringReader("1\nmenu\nquit");
    IController controller2 = new ControllerImpl(input0, mockModel, mockView);
    log = " (welcomeMessage)  (printMenu)  (examineStocks)  (printMenu)  (farewellMessage) ";
    log2 = "";
    controller2.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());

  }

  @Test
  public void testControllerPrintMenuFrom2() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader input0 = new StringReader("2\nmenu\nquit");
    IController controller2 = new ControllerImpl(input0, mockModel, mockView);
    log = " (welcomeMessage)  (printMenu)  (cePortolio)  (printMenu)  (farewellMessage) ";
    log2 = " (getPortfolioList) ";
    controller2.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());

  }


  //Represents menu option 1, then goes into examineStock and completes
  //examining the gain and loss of a stock
  @Test
  public void testControllerMenuOption11() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader input0 = new StringReader("1\n1\nAAPL\n2024-05-30\n2024-05-31\nquit");
    IController controller2 = new ControllerImpl(input0, mockModel, mockView);
    log = " (welcomeMessage)  (printMenu)  (examineStocks) " +
            " (examineGLStart)  (enterTickerName) " +
            " (examineEnterDate) (examineEnterEarlierDate)  (examineEnterDate)  " +
            "(examineEnterLaterDate) examGL0 (printMenu)  (farewellMessage) ";
    log2 = " (examineGainLossDate AAPL,2024-05-30,2024-05-31)";
    controller2.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());
  }

  //Represents menu option 1, then goes into examineStock and completes
  //examining the xdayCrossover
  @Test
  public void testControlleMenOption12() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader input1 = new StringReader("1\n2\nAAPL\n2024-05-30\n2024-05-31\nquit");
    IController controller = new ControllerImpl(input1, mockModel, mockView);
    log = (" (welcomeMessage)  (printMenu)  (examineStocks)  " +
            "(examineXDayCrossStart)  (enterTickerName)  (examineEnterDate)  " +
            "(enterNumDays)  (invaildInput)  (printMenu)  (farewellMessage) ");
    log2 = "";
    controller.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());
  }


  //Represents menu option 1, then goes into examineStockMenu and completes
  //examining the xdayMovingAvg
  @Test
  public void testControlleMenOption13() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader input1 = new StringReader("1\n3\nAAPL\n2022-05-31\n10\nquit");
    IController controller = new ControllerImpl(input1, mockModel, mockView);
    log = (" (welcomeMessage)  (printMenu)  (examineStocks)  (xDays) " +
            " (enterTickerName)  (examineEnterDate) " +
            " (enterNumDays) XDayAvg10 (printMenu)  (farewellMessage) ");
    log2 = "(examXAvgOverDays AAPL,2022-05-31,10)";
    controller.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());
  }


  //Represents menu option 2, then goes into create/edit porftolio menu,
  //and compeletes creating a new portfolio
  @Test
  public void testControllerMenuOption21() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader input1 = new StringReader("2\n1\nportfolio1\nquit");
    IController controller = new ControllerImpl(input1, mockModel, mockView);
    log = (" (welcomeMessage)  (printMenu)  " +
            "(cePortolio) None (showExistingPortfolio)  (makePortfolio)  " +
            "(portfolioCreated portfolio1) None (showExistingPortfolio)  " +
            "(printMenu)  (farewellMessage) ");
    log2 = " (getPortfolioList)  (getPortfolioList)  (createPortfolio portfolio1)";
    controller.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());

  }

  //Represents menu option 2, then goes into create/edit portfolio men,
  //representing getting value of protfolio
  @Test
  public void testControllerMenuOption223() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader input1 = new StringReader("2\n3\nportfolio1\n2024-05-31\nquit");
    IController controller = new ControllerImpl(input1, mockModel, mockView);
    log = " (welcomeMessage)  (printMenu)  (cePortolio) None (showExistingPortfolio) " +
            " (enterPortfolioName)  (portfolioError 0)  (printMenu) " +
            " (undefinedInstructions 2024-05-31)  (printMenu)  (farewellMessage) ";
    log2 = " (getPortfolioList)  (getPortfolioList) ";
    controller.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());
  }

  //Represents menu option 2 then option 3, get value of a portfolio for
  @Test
  public void testExaminePortfolioValue() throws IOException, ParserConfigurationException,
          TransformerException {
    Readable input = new StringReader("2\n3\nportfolio1\n2024-05-31\nquit");
    IController controller = new ControllerImpl(input, mockModel, mockView);
    controller.startProgram();
    assertEquals(" (welcomeMessage)  (printMenu)  (cePortolio) None "
                    + "(showExistingPortfolio) "
                    + " (enterPortfolioName)  (portfolioError 0)  (printMenu)  "
                    + "(undefinedInstructions 2024-05-31)  (printMenu)  (farewellMessage) ",
            mockView.getLog().toString());
    assertEquals(" (getPortfolioList)  (getPortfolioList) ",
            mockModel.getLog().toString());
  }

  //reprent test for datebuilder, which allows a user to build date without having to type
  //in a length string, which is error prone
  @Test
  public void testDateBuilder() throws IOException, ParserConfigurationException,
          TransformerException {
    Readable input = new StringReader("1\n1\napappa\nAAPL");
    IController controller = new ControllerImpl(input, mockModel, mockView);
    controller.startProgram();
    assertEquals(" (getPortfolioList)  (getPortfolioList) ",
            mockModel.getLog().toString());
  }


}




