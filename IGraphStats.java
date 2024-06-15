package model;

/**
 * Represents a class used to create bar graphs to show stock
 * or portfolio performance over time.
 */
public interface IGraphStats {
  /**
   * Allows the user to create a graph to show the performance
   * of a stock or a graph over time.
   * @return a String that contains the graph.
   */
  String createGraph();
}
