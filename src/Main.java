import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import controller.ControllerGUI;
import controller.ControllerImpl;
import controller.ControllerMax;
import controller.IController;
import model.IModelMax;
import model.IPortfolioMax;
import model.ModelDate;
import model.PortfolioDate;
import model.SingleStock;
import model.Stock;
import view.IView;
import view.IViewGUI;
import view.IViewMax;
import view.ViewGUI;
import view.ViewMax;

/**
 * Starts the stocks program. Creates a model, controller, and view. Once created, the controller
 * manages the flow of the program.
 */
public class Main {
  /**
   * Main method runs the program.
   *
   * @param args represents the arguments needed to start the main method.
   * @throws IOException if the input/out it is expecting does not exist.
   */
  public static void main(String[] args) throws IOException,
          ParserConfigurationException, TransformerException {
//    Stock apple = new SingleStock("MSFT", "JRHGL8Z0YLRSGDYK");
//    for (LocalDate date : apple.getStockDates()) {
//      System.out.println(date);
//    }
    IModelMax model = new ModelDate();
//    Readable rd = new InputStreamReader(System.in);
//    Appendable ap = System.out;
//    IViewMax view = new ViewMax(ap);
    IViewGUI gui = new ViewGUI();
    ControllerGUI controller = new ControllerGUI(model, gui);
//    controller.startProgram();
//    IPortfolioMax test = new PortfolioDate("test");
//    model.createPortfolio("test");
//    model.findPortfolio("test");
    // .addStockNew(LocalDate.of(2024,05,31),
    //            apple, 10);
//    model.distrubtion(LocalDate.of(2024,05,31), "test");
  }

}
