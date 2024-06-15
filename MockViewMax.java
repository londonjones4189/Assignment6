package view;

import java.io.IOException;

/**
 * Represents a mock view used for testing.
 */
public class MockViewMax implements IViewMax {
  @Override
  public void printChart(String chart) {
    // mock
  }

  @Override
  public void rebalance() {
    // mock
  }

  @Override
  public void enterPercent() {
    // mock
  }

  @Override
  public void heading(String message) {
    // mock
  }

  @Override
  public void barChartMenu() {
    // mock
  }

  @Override
  public void goals() {
    // mock
  }

  @Override
  public void comps(String left, String right) {
    // mock
  }

  @Override
  public Appendable getLog() {
    return null;
  }

  @Override
  public void printResults(String function, String result, int num) throws IOException {
    // mock
  }

  @Override
  public void welcomeMessage() throws IOException {
    // mock
  }

  @Override
  public void farewellMessage() throws IOException {
    // mock
  }

  @Override
  public void printMenu() throws IOException {
    // mock
  }

  @Override
  public void examineStocks() throws IOException {
    // mock
  }

  @Override
  public void examineGLStart() throws IOException {
    // mock
  }

  @Override
  public void examineXDayCrossStart() throws IOException {
    // mock
  }

  @Override
  public void enterTickerName() throws IOException {
    // mock
  }

  @Override
  public void examineEnterDate() throws IOException {
    // mock
  }

  @Override
  public void examineEnterEarlierDate() throws IOException {
    // mock
  }

  @Override
  public void examineEnterLaterDate() throws IOException {
    // mock
  }

  @Override
  public void cePortolio() throws IOException {
    // mock
  }

  @Override
  public void makePortfolio() throws IOException {
    // mock
  }

  @Override
  public void enterPortfolioName() throws IOException {
    // mock
  }

  @Override
  public void editPortfolio() throws IOException {
    // mock
  }

  @Override
  public void editingExistingPortfolio() throws IOException {
    // mock
  }

  @Override
  public void xDays() throws IOException {
    // mock
  }

  @Override
  public void undefinedInstructions(String str) throws IOException {
    // mock
  }

  @Override
  public void invalidInput() {
    // mock
  }

  @Override
  public void enterNumDays() {
    // mock
  }

  @Override
  public void showExistingPorfolios(String str) {
    // mock
  }

  @Override
  public void enterNumShares(String operation) throws IOException {
    // mock
  }

  @Override
  public void portfolioCreated(String portfolioName) throws IOException {
    // mock
  }

  @Override
  public void portfolioError(int size) throws IOException {
    // mock
  }

  @Override
  public void month() {
    // mock
  }

  @Override
  public void day() {
    // mock
  }

  @Override
  public void year() {
    // mock
  }

  @Override
  public void sellCreated(String stocksName, int quanity) {
    // mock
  }

  @Override
  public void tryAgain() {
    // mock
  }

  @Override
  public void invaildDate(String value) {
    // mock
  }

  @Override
  public void notInSystem() {
    // mock
  }

  @Override
  public void notInSystemTicker() {
    // mock
  }
}
