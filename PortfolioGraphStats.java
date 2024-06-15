package model;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Class that creates a bar graph representing the value of a portfolio over time.
 */
public class PortfolioGraphStats implements IGraphStats {
  private final LocalDate start;
  private final LocalDate end;
  private final long timeDiff;
  private final IPortfolio portfolio;
  private int intVal;

  /**
   * Public constructor for the class.
   *
   * @param model         represents the model used to access portfolios.
   * @param portfolioName represents the name of the portfolio being graphed.
   * @param start         represents the start date.
   * @param end           represents the end date.
   * @throws IOException              if the input/output is missing.
   * @throws IllegalArgumentException if the arguments are invalid.
   */
  public PortfolioGraphStats(IModel model, String portfolioName,
                             LocalDate start, LocalDate end) throws IOException,
          IllegalArgumentException {
    this.start = start;

    this.end = end;
    this.timeDiff = DAYS.between(this.start, this.end);
    if (timeDiff < 0) {
      throw new IllegalArgumentException("Time difference cannot be negative");
    }
    this.intVal = this.determineScale();
    this.portfolio = model.findPortfolio(portfolioName);
    if (this.start.isBefore((this.portfolio.getDatesInLog().get(0)))) {
      throw new IllegalArgumentException("Data not available for this start date");
    }
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
    return "Performance of portfolio "
            + this.portfolio.getPortfolioName()
            + " from "
            + this.start.toString()
            + " to "
            + this.end.toString();
  }

  private LocalDate findNextDate(String dateStr, int extraDiff) {
    try {
      this.portfolio.getPortfolioValue(LocalDate.parse(dateStr));
      return LocalDate.parse(dateStr);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }


  private List<LocalDate> findDatesinRange() {
    List<LocalDate> dates = new ArrayList<>();
    LocalDate date = this.start;
    dates.add(this.start);
    for (int i = 0; i < this.timeDiff / intVal + 1; i++) {
      date = date.plusDays(intVal);
      dates.add(date);
      if (date.equals(this.end)) {
        return dates;
      }
    }
    return dates;
  }

  @Override
  public String createGraph() {
    Map<LocalDate, Double> headingAndVal = new LinkedHashMap<>();
    double graphMin = Integer.MAX_VALUE;
    double graphMax = Integer.MIN_VALUE;
    for (LocalDate date : this.findDatesinRange()) {
      double value = this.findValue(date);
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
    return this.printTitle() + System.lineSeparator() + graph.toString();
  }

  private double findValue(LocalDate date) {
    if (this.portfolio.getPortfolioValue(date) == 0
            && date.isBefore(this.end)) {
      return this.findValue(date.plusDays(-1));
    }

    return this.portfolio.getPortfolioValue(date);
  }
}