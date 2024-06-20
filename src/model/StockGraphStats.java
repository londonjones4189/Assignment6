package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Class that creates a bar graph representing the value of a stock over time.
 */
public class StockGraphStats implements IGraphStats {
  private final LocalDate start;
  private final LocalDate end;
  private final long timeDiff;
  private final Stock stock;
  private int intVal;

  /**
   * Public constructor for the class.
   *
   * @param stock represents the stock being graphed.
   * @param start represents the start date.
   * @param end   represents the end date.
   * @throws IOException              if the input/output is missing.
   * @throws IllegalArgumentException if the stock or dates are invalid.
   */
  public StockGraphStats(Stock stock, LocalDate start, LocalDate end) throws IOException,
          IllegalArgumentException {
    this.start = start;
    this.end = end;
    this.timeDiff = DAYS.between(this.start, this.end);
    if (timeDiff < 0) {
      throw new IllegalArgumentException("Time difference cannot be negative");
    }
    this.intVal = this.determineScale();
    this.stock = stock;
  }

  // determines the scale
  private int determineScale() {
    if (this.timeDiff <= 15) {
      return 1;
    } else if (this.timeDiff <= 30) {
      return 2;
    } else if (this.timeDiff <= 140) {
      return 7;
    } else if (this.timeDiff <= 600) {
      return 30;
    } else if (this.timeDiff <= 1800) {
      return 90;
    } else if (this.timeDiff <= 5475) {
      return 365;
    } else {
      return 1095;
    }
  }

  private String printTitle() {
    return "Performance of stock "
            + this.stock.getTicker()
            + " from "
            + this.start.toString()
            + " to "
            + this.end.toString();
  }

  private LocalDate findNextDate(String dateStr, int extraDiff) {
    String dateLine = this.stock.findLineDaysBefore(dateStr, intVal + extraDiff);
    String tempDate = (dateLine.substring(0, dateLine.indexOf(",")));
    try {
      this.stock.getStockValues(this.stock.getTicker(), tempDate);
      return LocalDate.parse(tempDate);
    } catch (IllegalArgumentException e) {
      String updatedDateLine = this.stock.findLineDaysBefore(dateStr, intVal + 1);
      return LocalDate.parse((updatedDateLine.substring(0, updatedDateLine.indexOf(","))));
    }
  }


  private List<LocalDate> findDatesinRange() {
    List<LocalDate> dates = new ArrayList<>();
    String dateStr = this.end.toString();
    String pastDate;
    dates.add(this.end);
    for (int i = 0; i < this.timeDiff / intVal + 1; i++) {
      LocalDate date = this.findNextDate(dateStr, 0);
      if (date.isBefore(this.start)) {
        dates.add(this.start);
        Collections.reverse(dates);
        return dates;
      }
      dates.add(date);
      if (date.equals(this.start)) {
        Collections.reverse(dates);
        return dates;
      }
      dateStr = date.toString();
    }
    Collections.reverse(dates);
    return dates;
  }

  @Override
  public String createGraph() {
    Map<LocalDate, Double> headingAndVal = new LinkedHashMap<>();
    double graphMin = Integer.MAX_VALUE;
    double graphMax = Integer.MIN_VALUE;
    for (LocalDate date : this.findDatesinRange()) {
      double value = this.stock.getStockValues(date.toString(), "close");
      headingAndVal.put(date, value);
      if (value > graphMax) {
        graphMax = value;
      }
      if (value < graphMin) {
        graphMin = value;
      }
    }
    // no more than 50 * per line
    double scale = Math.max(1, graphMax / 50);

    // building each line to have the right number of stars and header
    StringBuilder graph = new StringBuilder();
    for (Map.Entry<LocalDate, Double> entry : headingAndVal.entrySet()) {
      LocalDate date = entry.getKey();
      double value = entry.getValue();
      int numAsterisks = (int) Math.ceil(value / scale);
      graph.append(String.format("%s %s\n", date + ":", "*".repeat(numAsterisks)));
    }
    graph.append("Scale: * = " + scale);
    return this.printTitle() + System.lineSeparator() + graph.toString() + System.lineSeparator();
  }
  }
