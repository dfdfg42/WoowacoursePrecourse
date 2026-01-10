package planetlotto;

import camp.nextstep.edu.missionutils.Console;
import planetlotto.view.InputView;

public class Controller {

    private final InputView inputView;


    public Controller(InputView inputView ) {
        this.inputView = inputView;
    }

    public void run() {
        while (true) {
            try {

                inputView.askAmount();
                String input = Console.readLine();
                Purchase purchaseAmount = inputPurchaseAmount();


                System.out.println(purchaseAmount);





            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }
    }

    private static Purchase inputPurchaseAmount() {
        while (true) {
            try {
                String input = Console.readLine();
                return new Purchase(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
