package controller;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * This interface represents the Controller part of the MVC model.
 * The Controller directs the Model to perform calculations
 * and give the results of these calculations to the View to be displayed to the user.
 */
public interface IController {

  /**
   * This method is started in the main file and begins running the program.
   *
   * @throws IOException if the input/output the method is trying read no longer exists
   *                     or has been moved to another directory.
   */
  void startProgram() throws IOException, ParserConfigurationException, TransformerException;
}
