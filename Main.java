import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import controller.ControllerMax;
import controller.IController;
import model.IModelMax;
import model.ModelDate;
import view.IViewMax;
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
    IModelMax model = new ModelDate();
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    IViewMax view = new ViewMax(ap);
    IController controller = new ControllerMax(rd, model, view);
    controller.startProgram();
  }

}
