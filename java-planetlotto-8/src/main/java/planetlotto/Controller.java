package planetlotto;

import camp.nextstep.edu.missionutils.Console;
import planetlotto.domain.BonusNumber;
import planetlotto.domain.Lotto;
import planetlotto.domain.Statistics;
import planetlotto.domain.WinningLotto;
import planetlotto.service.LottoGenerator;
import planetlotto.view.InputView;
import planetlotto.view.OutputView;

import java.util.List;

public class Controller {

    private final InputView inputView;
    private final OutputView outputView;


    public Controller(InputView inputView , OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        while (true) {
            try {

                //구매 금액 입력
                Purchase purchaseAmount = inputPurchaseAmount(inputView);

                //로또 구매
                LottoGenerator generator = new LottoGenerator();
                List<Lotto> purchasedLottos = generator.purchaseLottos(purchaseAmount);
                printPurchasedLottos(purchasedLottos);

                //당첨 번호 입력
                inputView.askWinningLotto();
                WinningLotto winningLotto = inputWinningLotto();

                //보너스 번호 입력
                inputView.askBonusNumber();
                BonusNumber bonusNumber = inputBonusNumber(winningLotto.getLotto());


                //당첨 결과 저장
//                Statistics stats = new Statistics(purchasedLottos, winningLotto.getLotto(), bonusNumber.getNumber());
//                outputView.printResult(stats.getRankCounts());


            } catch (IllegalArgumentException e) {
                OutputView.printErrorMessage(e.getMessage());
                throw e;
            }
        }
    }

    private static Purchase inputPurchaseAmount(InputView inputView) {
        while (true) {
            try {
                int input = inputView.askAmount();
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

    private static void printPurchasedLottos(List<Lotto> lottos) {
        System.out.println("\n" + lottos.size() + "개를 구매했습니다.");
        for (Lotto lotto : lottos) {
            System.out.println(lotto.getNumbers());
        }
    }
}
