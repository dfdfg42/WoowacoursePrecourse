package planetlotto;

import camp.nextstep.edu.missionutils.Console;
import planetlotto.view.InputView;

public class Controller {

    private final InputView inputView;

    public Controller(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        while (true) {
            try {

                inputView.askAmount();


            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                throw e;
            }
        }
    }
}
