import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import controller.ControllerMax;
import controller.IController;
import model.MockModelDate;
import view.MockViewMax;

import static org.junit.Assert.*;

public class ControllerMaxTest {

  private MockModelDate mockModel;
  private MockViewMax mockView;
  private String log;
  private String log2;


  @Before
  public void setUp() throws Exception {
    mockModel = new MockModelDate(new StringBuilder());
    mockView = new MockViewMax(new StringBuilder());
    log = "";
    log2 = "";

  }


  //combination testing 1 4
  @Test
  public void testControllerQuit() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader inputQuit = new StringReader("quit");
    IController controller = new ControllerMax(inputQuit, mockModel, mockView);
    log = " (welcomeMessage)  (printMenu)  (farewellMessage) ";
    log2 = "";
    controller.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());
  }


  //combination testing 1 4
  @Test
  public void tesRebalancet() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader inputQuit = new StringReader("2\n2\n3\n2024\n04\n\n05\nhello\nAPhfwgfuL\n0.50\nA\n0.50\nstop\nquit");
    IController controller = new ControllerMax(inputQuit, mockModel, mockView);
    log = " (welcomeMessage)  (printMenu)  (farewellMessage) ";
    log2 = "";
    controller.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());
  }


  @Test
  public void tesRebalancet2() throws IOException, ParserConfigurationException,
          TransformerException {
    StringReader inputQuit = new StringReader("2\n1\ntesting\n2024\n04\n\n05\nhello\nAPhfwgfuL\n0.50\nA\n0.50\nstop\nquit");
    IController controller = new ControllerMax(inputQuit, mockModel, mockView);
    log = " (welcomeMessage)  (printMenu)  (farewellMessage) ";
    log2 = "";
    controller.startProgram();
    assertEquals(log, mockView.getLog().toString());
    assertEquals(log2, mockModel.getLog().toString());
  }
}
