package view;

import java.io.IOException;

/**
 * Represents the new view to support the features added to the program. Works with the
 * updated controller and model
 */
public interface IViewMax extends IView {
  /**
   * Displays the chart to the veiwer.
   *
   * @param chart represents a bar grapgh of the portfolio.
   */
  void printChart(String chart);

  /**
   * Prints out the rebalance of the portfilo
   * and that it was sucessful.
   */
  void rebalance();

  /**
   * Prints out a message teling the user to enter in
   * the percentage.
   */
  void enterPercent();

  /**
   * Prints out whatever the given message is
   * out as a title heading.
   *
   * @param message the given title.
   */
  void heading(String message);

  /**
   * Prints out the options that come with viewin
   * the bar graph.
   */
  void barChartMenu();

  /**
   * Prints out instructions for users typing in
   * list of goals for a portfolio.
   */
  void goals() throws IOException;

  /**
   * Prints out list of composition or distrubtion to users
   * of a portfilo.
   *
   * @param left  the stocks vaules list.
   * @param right either the amount of shares or value fo shares.
   */
  void comps(String left, String right);
}
