Program Design:
PART 1
- The program uses an MVC design pattern.

- The model.model is represented by the model.model interface, and is implemented by the model.
model.ModelImpl class.

- The model.model.ModelImpl works the model.IPortfolio interface and the model.Stock interface
 manage the program's data.

- IPorfolio is implemented by model.model.Portfolio and contains info about:
    - the portfolio's name
    - stocks
    - and number of shares per stock.

- model.Stock is implemented by model.model.SingleStock, and contains info about:
    - the stock's ticker
    - a list of string containing info
    - about the stock's value on all dates available

- The controller.controller is represented by the controller.controller interface. and is
 implemented by controller.controller.ControllerImpl.
- It uses the model and the view to manage the flow of the program

- The view.view is represented by the view.view interface and is implemented by the
view.view.ViewImpl.
- It receives data from the model and prints out a text-based interface to the console
accordingly.

** Design choices **
- If the user pulls data for a date that missing while trying to calculate
- the X-Day average or X-Day crossing:
    - the program will fill in data from the previous available day
- If the user pulls data for a date that is missing while trying to
    - examine the gain/loss of a stock:
    - if either one of the dates provided is not available,
    - the program will consider this an invalid input and terminate the calculation


  PART 2
  For part 2 we updated the date so instead of being entered as one string it is broken up into
  month, day, and year.

  We also added a featured where if you enter in the wrong stock ticker symbol, it allows
  you to re-enter again.

  We also added in the feature of having the program to go back to the most recent menu, instead
   the main menu.

  We also fixed the null-point exception inx-moving-day average which occured when you entered in
  a weekend or a holdiday as the starting day. Now it prompts you to enter in a different starting
  day.

  Some deisgn choices we had for this assignment were the following

  1.) If you enter a date that is not in chronological order, the program deletes
  any transactions that would've been invaildated by it. For example if I bought stocks on
  Tuesday then bought stocks on Monday, Tuesday would be invaildated and deleted. This works for
  both the add and the buy and is reflected in the composition and decomposition
  2.) If the date can't be found for the bar grapgh that can't be found in the range,
  the program shows the last vaild date instead




