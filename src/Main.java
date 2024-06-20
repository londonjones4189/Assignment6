import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import controller.ControllerGUI;
import controller.ControllerMax;
import controller.IController;
import model.IModelMax;
import model.ModelDate;
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
  public static void main(String[] args) throws IOException, ParserConfigurationException, TransformerException {
    if (args.length == 0) {
      GUIGo();
    } else if (args.length == 1 && "-text".equals(args[0])) {
     TextInterfaceGo();
    } else {
      System.err.println("Unknown command line argument inputted");
    }
  }

  //Launches the GUI when running the jar file
  private static void GUIGo() {
    try {
      IModelMax model = new ModelDate();
      IViewGUI view = new ViewGUI();
      IController controller = new ControllerGUI(model, view);
      controller.startProgram();
    } catch (IOException | ParserConfigurationException | TransformerException e) {
      e.printStackTrace();
    }
  }

  //Launches the text-based interface when running the jar file
  private static void TextInterfaceGo() {
    try {
      IModelMax model = new ModelDate();
      Readable rd = new InputStreamReader(System.in);
      Appendable ap = System.out;
      IViewMax view = new ViewMax(ap);
      IController controller = new ControllerMax(rd, model, view);
      controller.startProgram();
    } catch (IOException | ParserConfigurationException | TransformerException e) {
      e.printStackTrace();
    }
  }
}
