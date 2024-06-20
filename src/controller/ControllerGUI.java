package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import model.IModelMax;
import view.IViewGUI;

public class ControllerGUI implements ActionListener {
  private IModelMax model;
  private IViewGUI view;
  private String currentFuction;
  private String currentPortfolio;
  private String currentTicker;
  private LocalDate currentDate;
  private int numShares;


  public ControllerGUI(IModelMax model, IViewGUI view) {
    this.model = model;
    this.view = view;
    this.view.setListener(this);
    this.view.display();
    this.currentFuction = "";
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      //read from the input textfield
      case "Quit":
        // save the files before quitting
        System.exit(0);
      case "Create a new portfolio":
        this.view.createPortfolio();
        this.view.setListenerPortfolioCreation(this);
        break;
      case "Create":
        try {
          this.createPortfolio();
        } catch (ParserConfigurationException
                 | TransformerException
                 | IOException
                 | IllegalArgumentException ex) {
          this.view.portfolioNameError();
        }
        break;
      case "Enter Portfolio":
        System.out.println("Enter Portfolio");
        if (this.nameExists()) {
          this.currentPortfolio = this.view.getNameString();
          if (this.currentFuction.equals("Portfolio Value")
                  || this.currentFuction.equals("Portfolio Composition") ) {
            this.view.stopListeningPortfolio(this);
            this.view.showDate(this.model.findPortfolio(this.currentPortfolio).getDatesInLog(),
                    this.currentFuction);
            this.view.setListenerCompute(this);
          }
        } else {
          this.view.portfolioNotFound();
        }
        break;
      case "Enter Ticker":
        System.out.println("Enter ticker");
        if (this.tickerExists()) {
          this.currentTicker = this.view.getTickerString();
          System.out.println("Current ticker: " + this.currentTicker);
          this.view.showDate(this.model.findTicker(this.currentTicker).getStockDates(),
                  this.currentFuction);
          this.view.setListenerBuy(this);
          this.view.stopListeningEnter(this);
        } else {
//          this.view.setListenerEnter(this);
          this.view.tickerNotFound();
        }
        break;
      case "Buy":
        if (this.validNum()) {
          try {
            this.currentDate = LocalDate.parse(this.view.getDate());
            this.model.buy(this.currentPortfolio, this.currentTicker,
                    this.numShares, this.currentDate);
          } catch (DateTimeParseException
                   | IOException
                   | ParserConfigurationException
                   | TransformerException ignored) {
          }
          this.view.editingPortfolio();
          this.view.setListenerPortfolioEdit(this);
          this.view.buyStatus();
        } else {
          this.view.invalidNum();
        }
        break;
      case "Sell":
        if (this.validNum()) {
          try {
            this.currentDate = LocalDate.parse(this.view.getDate());
            this.model.sell(this.currentPortfolio, this.currentTicker,
                    this.numShares, this.currentDate);
            this.view.editingPortfolio();
            this.view.setListenerPortfolioEdit(this);
            this.view.sellStatus();
          } catch (RuntimeException ignored) {
            this.view.sellError();
          } catch (IOException
                   | ParserConfigurationException
                   | TransformerException ignored) {
          }
        } else {
          this.view.invalidNum();
        }
        break;
      case "compute":
        System.out.println("compute");
        this.currentDate = LocalDate.parse(this.view.getDate());
        // Pair<String, String> newPair2
        if (this.currentFuction.equals("Portfolio Value")) {
          this.view.showPortfolioValue(this.model.getValue(this.currentPortfolio, this.currentDate));
        } else {
          this.view.showComputeResults(this.model
                  .composition(this.currentDate, this.currentPortfolio),
                  this.currentFuction);
        }

        break;
      case "Back":
        this.view.backToStart();
        break;
      case "Select":
       this.determineFunction();
       break;
      case "Edit an existing portfolio":
        this.view.editingPortfolio();
        this.view.setListenerPortfolioEdit(this);
        break;
    }
  }

  private void determineFunction() {
    this.currentFuction = this.view.getFunctionSelection();
    System.out.println("Function : " + this.currentFuction);
    switch (this.currentFuction) {
      case "Buy stocks":
        this.view.portfolioBuy();
        this.view.setListenerEnter(this);
        if (this.nameExists()) {
          System.out.println("Buy stocks");
        }
        break;
      case "Sell stocks":
        this.view.sellStocks();
        this.view.setListenerEnter(this);
        if (this.nameExists()) {
          System.out.println("Sell stocks");
        }
        break;
      case "Portfolio Value":
        this.view.portfolioValue();
        this.view.setListenerEnter(this);
        break;
        // trigger value screen
      case "Portfolio Composition":
        this.view.findComp();
        this.view.setListenerEnter(this);
      default:
        break;
    }
  }

  private void createPortfolio() throws ParserConfigurationException,
          IOException, TransformerException {
    String newName =  this.view.getNameString();
    for (String name : this.model.getPortfolioList()) {
      if (name.equals(newName)) {
        this.view.portfolioNameError();
        break;
      }
    }
    this.model.createPortfolio(newName);
    this.view.portfolioNameSuccess(newName);
  }

  private boolean nameExists() {
    String newName =  this.view.getNameString();
    for (String name : this.model.getPortfolioList()) {
      if (name.equals(newName)) {
        return true;
      }
    }
    return false;
  }

  private boolean validNum() {
    String validNum = this.view.getNumString();
    try {
      if (Integer.parseInt(validNum) <= 0) {
        return false;
      } else {
        this.numShares = Integer.parseInt(validNum);
        return true;
      }
    } catch (NumberFormatException ex) {
      return false;
    }
  }

  private boolean tickerExists() {
    try {
      String tickerName = this.view.getTickerString();
      this.model.findTicker(tickerName);
    } catch (IllegalArgumentException ex) {
      return false;
    }
    return true;
  }

  protected String formatListString(List<String> list) {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      if (i == list.size() - 1) {
        str.append(list.get(i));
      } else {
        str.append(list.get(i)).append(", ");
      }
    }
    if (list.isEmpty()) {
      return "None";
    }
    return String.valueOf(str);
  }
}
