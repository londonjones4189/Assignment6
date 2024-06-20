package view;

import java.awt.event.ActionListener;
import java.util.List;

import controller.ControllerGUI;
import model.Pair;

public interface IViewGUI {
  void backToStart();

  void portfolioValue();

  void editingPortfolio();

  void sellStocks();

  void createPortfolio();

  void findComp();

  /**
   * Set the label that is showing what the model stores.
   */
  void display();

  /**
   * Set the listener for any actions.
   */
  void setListener(ActionListener listener);

  void setListenerPortfolioCreation(ActionListener listener);

  void setListenerPortfolioEdit(ActionListener listener);

  void setListenerEnter(ActionListener listener);

  void setListenerBuy(ActionListener listener);

  void setListenerCompute(ActionListener listener);

  void portfolioNameError();

  void portfolioNameSuccess(String name);

  void portfolioNotFound();

  String getNameString();

  String getFunctionSelection();

  /**
   * closes the current panel
   */
  void close();

  void portfolioBuy();

  String getTickerString();

  String getNumString();

  void showDate(List<String> dates, String buyOrSell);

  void tickerNotFound();

  void buyStatus();

  void sellStatus();

  void invalidNum();

  String getDate();

  void stopListeningEnter(ControllerGUI controller);

  void stopListeningPortfolio(ControllerGUI controller);

  void showComputeResults(Pair<String, String> result, String function);

  void sellError();

  void showPortfolioValue(String str);
}
