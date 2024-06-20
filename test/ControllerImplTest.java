import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import controller.ControllerImpl;
import controller.IController;
import model.IModel;
import model.MockModel;
import view.IView;
import view.MockView;
import view.ViewImpl;

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
  private IModel modle;
  private IView view;
  private Appendable log3;
  private Readable log4;

  @Before
  public void setUp() throws Exception {
    mockModel = new MockModel(new StringBuilder());
    mockView = new MockView(new StringBuilder());
    log = "";
    log2 = "";
    view = new ViewImpl(log3);

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
    StringReader input0 = new StringReader("2\nmenu\nquit\nquit");
    IController controller2 = new ControllerImpl(input0, mockModel, mockView);
    log = " (welcomeMessage)  (printMenu)  (cePortolio)  (printMenu)  (farewellMessage) ";
    log2 = " (getPortfolioList) "; //add
    controller2.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());

  }


  //Represents menu option 1, then goes into examineStock and completes
  //examining the gain and loss of a stock
  @Test
  public void testControllerMenuOption11() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader input0 = new StringReader("1\n1\nA\n2024\n05\n31\n2024\n06\n03\nquit\nquit");
    IController controller2 = new ControllerImpl(input0, mockModel, mockView);
    log = " (welcomeMessage)  (printMenu)  (examineStocks)  (examineGLStart)  (enterTickerName) " +
            " (examineEnterDate) (examineEnterEarlierDate)  (examineEnterDate)  (examineEnterLaterDate) " +
            "examGL0 (examineStocks)  (farewellMessage)  (printMenu)  (farewellMessage) ";
    log2 = "(findTicker (examineGainLossDate A,2024-05-31,2024-06-03)";
    controller2.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());
  }

  //Represents menu option 1, then goes into examineStock and completes
  //examining the xdayCrossover
  @Test
  public void testControlleMenOption12() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader input1 = new StringReader("1\n2\nAAPL\n2005\n04\n05\n20\nquit\nquit\nquit");
    IController controller = new ControllerImpl(input1, mockModel, mockView);
    log = (" (welcomeMessage)  (printMenu)  (examineStocks)  (examineXDayCrossStart)  " +
            "(enterTickerName)  (examineEnterDate)  (enterNumDays) XDayCrossoverNone20" +
            " (examineStocks)  (farewellMessage)  (printMenu)  (farewellMessage) ");
    log2 = "";
    controller.startProgram();
    assertEquals(log, mockView.getLog().toString());
    //assertEquals(log2, mockModel.getLog().toString());
  }


  //Represents menu option 1, then goes into examineStockMenu and completes
  //examining the xdayMovingAvg
  @Test
  public void testControlleMenOption13() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader input1 = new StringReader("1\n3\nA\n2022\n05\n31\n10\nquit\nquit");
    IController controller = new ControllerImpl(input1, mockModel, mockView);
    log = (" (welcomeMessage)  (printMenu)  (examineStocks)  (xDays)  (enterTickerName) " +
            " (examineEnterDate)  (enterNumDays) XDayAvg10 " +
            "(examineStocks)  (farewellMessage)  (printMenu)  (farewellMessage) ");
    log2 = "(findTicker(examXAvgOverDays A,2022-05-31,10)";
    controller.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());
  }

}




