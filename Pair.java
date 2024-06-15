package model;

/**
 * Represents a pair class. A pair has a left and a right value.
 */
public class Pair<T, K> {
  private T left;
  private K right;

  public Pair(T left, K right) {
    this.left = left;
    this.right = right;
  }

  /**
   * Returns the left string of a pair.
   * @return the left list of a pair.
   */
  public T getLeft() {
    return left;
  }

  /**
   * Retunrs the right string of a pair.
   * @return the right string of a pair.
   */
  public K getRight() {
    return right;
  }

  public String toString() {
    String s = "" + left.toString() + " ||" + right.toString() + "";
    return s;
  }


}
