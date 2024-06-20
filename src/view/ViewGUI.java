package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import controller.ControllerGUI;
import model.Pair;

// composition -> num of stocks
// value/distribution -> monetary value of stocks in a portfolio
public class ViewGUI extends JFrame implements IViewGUI {
  private CardLayout cardLayout;
  private JPanel cardPanel;
  private JPanel startPanel, buyScreen, editingScreen,
          sellScreen, valueScreen, compScreen;
  private JLabel title;
  private JLabel info, buyStatus;
  private JTextField portfolioNameTextField, tickerNameTextField, numShares, valueText;
  private JButton createPortfolio, editPortfolio, quit,
          createButton, back, enterPortfolio, select, enterTicker, buy, compute;
  private JComboBox<String> portfolioFunctions, dateOptions;

  public ViewGUI() {
    this.valueText = new JTextField();
    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);
    startPanel = this.createStartScreen();
    cardPanel.add(startPanel, "start");
    this.setResizable(false);
    // Add the card panel to the frame
    this.add(cardPanel);
    this.setSize(500, 200);
    setVisible(true);
  }

  @Override
  public void backToStart() {
    cardLayout.show(cardPanel, "start");
    this.setSize(500, 200);
  }

  private JPanel createStartScreen() {
    JPanel start = new JPanel(new BorderLayout(25, 25));
    setTitle("Virtual Stock Market Simulator! \uD83D\uDCCA");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);

    title = new JLabel("Welcome to the Virtual Stock Market Simulator! \uD83D\uDCCA",
            SwingConstants.CENTER);
    info = new JLabel("Data is not available on weekends and national holidays.",
            SwingConstants.CENTER);

    createPortfolio = new JButton("Create a new portfolio");
    editPortfolio = new JButton("Edit an existing portfolio");
    quit = new JButton("Quit");

    start.add(title, BorderLayout.PAGE_START);
    start.add(info, BorderLayout.PAGE_END);
    start.add(createPortfolio, BorderLayout.LINE_START);
    start.add(editPortfolio, BorderLayout.CENTER);
    start.add(quit, BorderLayout.LINE_END);
    pack();

    return start;
  }

  private JPanel createEditingPortfolioScreen() {
    setTitle("Edit an existing portfolio");
    this.editingScreen = new JPanel();
    this.portfolioFunctions = new JComboBox<>(new String[]{"Select portfolio option",
            "Buy stocks", "Sell stocks",
            "Portfolio Value", "Portfolio Composition"});
    editingScreen.add(portfolioFunctions);
    this.select = new JButton("Select");
    this.back = new JButton("Back");
    editingScreen.add(select);
    editingScreen.add(this.back);
    this.setResizable(true);
    return editingScreen;
  }

  private JPanel createBuyScreen() {
    this.setTitle("Buy stocks for portfolio");
    JPanel buyScreen = new JPanel(new FlowLayout());
    JLabel enterName = new JLabel("Enter the name of the portfolio: ");
    buyScreen.add(enterName);
    this.enterPortfolio = new JButton("Enter Portfolio");
    this.portfolioNameTextField = new JTextField("Type portfolio name", 20);
    buyScreen.add(this.portfolioNameTextField);
    buyScreen.add(enterPortfolio);
    this.tickerNameTextField = new JTextField("enter the ticker here");
    JLabel ticker = new JLabel("Enter ticker name:");
    buyScreen.add(ticker);
    buyScreen.add(this.tickerNameTextField);
    this.enterTicker = new JButton("Enter Ticker");
    buyScreen.add(this.enterTicker);
    return buyScreen;
  }

  private JPanel createSellScreen() {
    this.setTitle("Sell stocks for portfolio");
    this.sellScreen = new JPanel(new FlowLayout());
    JLabel enterName = new JLabel("Enter the name of the portfolio: ");
    sellScreen.add(enterName);
    this.enterPortfolio = new JButton("Enter Portfolio");
    this.portfolioNameTextField = new JTextField("Type portfolio name", 20);
    sellScreen.add(this.portfolioNameTextField);
    sellScreen.add(enterPortfolio);
    this.tickerNameTextField = new JTextField("enter the ticker here");
    JLabel ticker = new JLabel("Enter ticker name:");
    sellScreen.add(ticker);
    sellScreen.add(this.tickerNameTextField);
    this.enterTicker = new JButton("Enter Ticker");
    sellScreen.add(this.enterTicker);
    return sellScreen;
  }

  private JPanel createValueScreen() {
    this.setTitle("Compute portfolio values");
    this.valueScreen = new JPanel(new FlowLayout());
    JLabel enterName = new JLabel("Enter the name of the portfolio: ");
    valueScreen.add(enterName);
    this.enterPortfolio = new JButton("Enter Portfolio");
    this.portfolioNameTextField = new JTextField("Type portfolio name", 20);
    valueScreen.add(this.portfolioNameTextField);
    valueScreen.add(enterPortfolio);
    valueScreen.add(back);
    return valueScreen;
  }

  @Override
  public void portfolioValue() {
    this.valueScreen = this.createValueScreen();
    cardPanel.add(valueScreen, "value");
    cardLayout.show(cardPanel, "value");
    this.setSize(400, 300);
  }

  private JPanel createCompScreen() {
    this.setTitle("Compute portfolio composition");
    this.compScreen = new JPanel(new FlowLayout());
    JLabel enterName = new JLabel("Enter the name of the portfolio: ");
    compScreen.add(enterName);
    this.enterPortfolio = new JButton("Enter Portfolio");
    this.portfolioNameTextField = new JTextField("Type portfolio name", 20);
    compScreen.add(this.portfolioNameTextField);
    compScreen.add(enterPortfolio);
    compScreen.add(back);
    return compScreen;
  }

  private JPanel createPortfolioScreen() {
    JPanel createPort = new JPanel(new FlowLayout(FlowLayout.CENTER));
    setTitle("Create a new portfolio");
    this.setSize(600, 600);
    this.setLocation(200, 200);

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    JLabel enterName = new JLabel("Enter the name of the portfolio: ");
    createPort.add(enterName);
    this.createButton = new JButton("Create");
    this.portfolioNameTextField = new JTextField("Enter text here", 20);
    this.back = new JButton("Back");
    createPort.add(portfolioNameTextField);
    createPort.add(createButton);
    createPort.add(back);
    createPort.setVisible(true);
    pack();
    return createPort;
  }

  @Override
  public void editingPortfolio() {
    JPanel editingScreen = this.createEditingPortfolioScreen();
    cardPanel.add(editingScreen, "edit screen");
    cardLayout.show(cardPanel, "edit screen");
    this.setSize(400, 300);
    this.setSize(400, 300);
  }

  @Override
  public void sellStocks() {
    JPanel sellScreen = this.createSellScreen();
    cardPanel.add(sellScreen, "sell screen");
    cardLayout.show(cardPanel, "sell screen");
    this.setSize(400, 300);
  }

  @Override
  public void createPortfolio() {
    JPanel creationScreen = this.createPortfolioScreen();
    cardPanel.add(creationScreen, "create portfolio screen");
    cardLayout.show(cardPanel, "create portfolio screen");
    this.setSize(400, 300);
  }

  @Override
  public void portfolioBuy() {
    this.buyScreen = this.createBuyScreen();
    cardPanel.add(buyScreen, "buy");
    cardLayout.show(cardPanel, "buy");
    this.setSize(400, 300);
  }

  @Override
  public void findComp() {
    this.compScreen = this.createCompScreen();
    cardPanel.add(compScreen, "comp");
    cardLayout.show(cardPanel, "comp");
    this.setSize(400, 300);
  }

  public void display() {
    setVisible(true);
  }

  public void setListener(ActionListener listener) {
    createPortfolio.addActionListener(listener);
    editPortfolio.addActionListener(listener);
    quit.addActionListener(listener);
  }

  @Override
  public void setListenerPortfolioCreation(ActionListener listener) {
    this.createButton.addActionListener(listener);
    this.portfolioNameTextField.addActionListener(listener);
    this.back.addActionListener(listener);
  }

  @Override
  public void setListenerPortfolioEdit(ActionListener listener) {
    this.portfolioFunctions.addActionListener(listener);
    this.back.addActionListener(listener);
    this.select.addActionListener(listener);
  }

  @Override
  public void setListenerEnter(ActionListener listener) {
    this.enterPortfolio.addActionListener(listener);
    try {
      this.enterTicker.addActionListener(listener);
    } catch (NullPointerException ignored) {
    }

  }

  @Override
  public void setListenerBuy(ActionListener listener) {
    this.buy.addActionListener(listener);
  }

  @Override
  public void setListenerCompute(ActionListener listener) {
    this.compute.addActionListener(listener);
  }

  @Override
  public void portfolioNameError() {
    this.portfolioNameTextField.setText("Error! Portfolio name is already taken! ❌");
  }

  @Override
  public void portfolioNameSuccess(String name) {
    this.portfolioNameTextField.setText("Success! ✅ New portfolio created");
  }

  @Override
  public void portfolioNotFound() {
    this.portfolioNameTextField.setText("Error! Portfolio not found ❌");
  }

  @Override
  public String getNameString() {
    return portfolioNameTextField.getText();
  }

  @Override
  public String getFunctionSelection() {
    return this.portfolioFunctions.getSelectedItem().toString();
  }

  @Override
  public void close() {
    this.dispose();
  }

  @Override
  public String getTickerString() {
    return (String) this.tickerNameTextField.getText();
  }

  @Override
  public String getNumString() {
    return (String) this.numShares.getText();
  }

  @Override
  public void showDate(List<String> dates, String function) {
    String[] dateStrings = dates.toArray(new String[dates.size()]);
    this.dateOptions = new JComboBox<String>(dateStrings);
    if (function.equals("Buy stocks")) {
      this.buyScreen.add(this.dateOptions);
      this.numShares = new JTextField("Enter Number of Shares");
      this.buy = new JButton("Buy");
      this.buyScreen.add(this.buy);
      this.buyStatus = new JLabel("Waiting");
      this.buyScreen.add(this.numShares);
      this.buyScreen.add(this.buyStatus);
      this.setSize(400, 300);
      cardPanel.add(buyScreen, "buy");
      cardLayout.show(cardPanel, "buy");
    } else if (function.equals("Sell stocks")) {
      this.sellScreen.add(this.dateOptions);
      this.numShares = new JTextField("Enter Number of Shares");
      this.buy = new JButton("Sell");
      this.sellScreen.add(this.buy);
      this.buyStatus = new JLabel("Waiting");
      this.sellScreen.add(this.numShares);
      this.sellScreen.add(this.buyStatus);
      this.setSize(400, 300);
      cardPanel.add(sellScreen, "buy");
      cardLayout.show(cardPanel, "buy");
    } else if (function.equals("Portfolio Value")) {
      this.valueScreen.add(this.dateOptions);
      this.compute = new JButton("compute");
      this.valueScreen.add(this.compute);
      cardPanel.add(valueScreen, "value");
      cardLayout.show(cardPanel, "value");
    } else {
      this.compScreen.add(this.dateOptions);
      this.compute = new JButton("compute");
      this.compScreen.add(this.compute);
      cardPanel.add(compScreen, "comp");
      cardLayout.show(cardPanel, "comp");
    }

  }

  @Override
  public void tickerNotFound() {
    this.tickerNameTextField.setText("Ticker not found! ❌");
  }

  @Override
  public void buyStatus() {
    this.buyStatus.setText("Success! Shares added to portfolio ✅");
    this.editingScreen.add(this.buyStatus);
  }

  @Override
  public void sellStatus() {
    this.buyStatus.setText("Success! Shares sold from the portfolio ✅");
    this.editingScreen.add(this.buyStatus);
  }

  @Override
  public void invalidNum() {
    this.numShares.setText("Num of shares must an integer greater than 0");
  }

  @Override
  public String getDate() {
    return this.dateOptions.getSelectedItem().toString();
  }

  @Override
  public void stopListeningEnter(ControllerGUI controller) {
    this.enterTicker.removeActionListener(controller);
  }

  @Override
  public void stopListeningPortfolio(ControllerGUI controller) {
    this.enterPortfolio.removeActionListener(controller);
  }

  @Override
  public void showComputeResults(Pair<String, String> result, String function) {
    String finalResult = "";
    String stocks = result.getLeft();
    String[] stockArr = stocks.split("||");
    List<String> stockList = Arrays.asList(stockArr);
    String share = result.getRight();
    String[] shareArr = share.split("||");
    // Convert the array to a list
    List<String> shareList = Arrays.asList(shareArr);
    for (int i = 0; i < stockList.size(); i++) {
      for (int j = 0; j < shareList.size(); j++) {
        String valueStr = "Stock: " + stockList.get(i) + ", Value: " + shareList.get(j);
        finalResult += valueStr + System.lineSeparator();
      }
    }
    if (function.equals("Portfolio Value")) {
      this.valueText = new JTextField(finalResult);
      this.valueScreen.setLayout(new FlowLayout());
      this.valueScreen.add(this.valueText);
      Dimension maxSize = new Dimension(500, 300);
      valueScreen.setMaximumSize(maxSize);
      valueScreen.setPreferredSize(maxSize);
      cardPanel.add(valueScreen, "value");
      cardLayout.show(cardPanel, "value");
      System.out.println("Value: " + finalResult);
    } else {
      this.valueText = new JTextField(finalResult);
      this.compScreen.setLayout(new FlowLayout());
      this.compScreen.add(this.valueText);
      Dimension maxSize = new Dimension(500, 300);
      compScreen.setMaximumSize(maxSize);
      compScreen.setPreferredSize(maxSize);
      System.out.println("comp: " + finalResult);
      cardPanel.add(compScreen, "comp");
      cardLayout.show(cardPanel, "comp");
    }

  }

  @Override
  public void sellError() {
    this.buyStatus = new JLabel("Error! Trying to remove more shares than added");
    this.sellScreen.add(this.buyStatus);
    this.sellScreen.add(this.back);
    cardPanel.add(sellScreen, "sell");
    cardLayout.show(cardPanel, "sell");
  }

  @Override
  public void showPortfolioValue(String str) {
    this.valueText.setText(str);
    this.valueScreen.setLayout(new FlowLayout());
    this.valueScreen.add(this.valueText);
    Dimension maxSize = new Dimension(500, 300);
    valueScreen.setMaximumSize(maxSize);
    valueScreen.setPreferredSize(maxSize);
    cardPanel.add(valueScreen, "value");
    cardLayout.show(cardPanel, "value");
    System.out.println("Value: " + str);
  }
}
