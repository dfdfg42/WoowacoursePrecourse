package planetlotto;

import camp.nextstep.edu.missionutils.Console;
import planetlotto.domain.BonusNumber;
import planetlotto.domain.Lotto;
import planetlotto.domain.Statistics;
import planetlotto.domain.WinningLotto;
import planetlotto.view.InputView;

public class Controller {

    private final InputView inputView;


    public Controller(InputView inputView ) {
        this.inputView = inputView;
    }

    public void run() {
        while (true) {
            try {


                //구매 금액 입력
                inputView.askAmount();
                String input = Console.readLine();
                Purchase purchaseAmount = inputPurchaseAmount();

                //당첨 번호 입력
                inputView.askWinningLotto();
                WinningLotto winningLotto = inputWinningLotto();

                inputView.askBonusNumber();
                BonusNumber bonusNumber = inputBonusNumber(winningLotto.getLotto());



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

    private static WinningLotto inputWinningLotto() {
        while (true) {
            try {
                String input = Console.readLine();
                return new WinningLotto(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static BonusNumber inputBonusNumber(Lotto winningLotto) {
        while (true) {
            try {
                String input = Console.readLine();
                return new BonusNumber(input, winningLotto);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
