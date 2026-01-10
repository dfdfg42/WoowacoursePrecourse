package planetlotto;

import camp.nextstep.edu.missionutils.Console;
import planetlotto.domain.*;
import planetlotto.service.LottoGenerator;
import planetlotto.view.InputView;
import planetlotto.view.OutputView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                WinningLotto winningLotto = inputWinningLotto(inputView);

                //보너스 번호 입력

                BonusNumber bonusNumber = inputBonusNumber(winningLotto.getLotto(),inputView);


                //당첨 결과 저장
                Statistics stats = new Statistics(purchasedLottos, winningLotto.getLotto(), bonusNumber.getNumber());
                Map<Rank, Integer> rankCounts = stats.getRankCounts();
                HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
                hashMap.put(1, rankCounts.get(Rank.FIRST));
                hashMap.put(2, rankCounts.get(Rank.SECOND));
                hashMap.put(3, rankCounts.get(Rank.THIRD));
                hashMap.put(4, rankCounts.get(Rank.FOURTH));
                hashMap.put(5, rankCounts.get(Rank.FIFTH));
                OutputView.printResult(hashMap);



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

    private static WinningLotto inputWinningLotto(InputView inputView) {
        while (true) {
            try {
                List<Integer> numbers = inputView.askWinningLotto();
                return new WinningLotto(numbers);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static BonusNumber inputBonusNumber(Lotto winningLotto,InputView inputView) {
        while (true) {
            try {
                int input = inputView. askBonusNumber();
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

    private static void printStatistics(Statistics stats) {
        Map<Rank, Integer> rankCounts = stats.getRankCounts();

        System.out.println("\"당첨 통계\"");

        System.out.printf("3개 일치 (%,d원) - %d개\n", Rank.FIFTH.getPrizeMoney(), rankCounts.get(Rank.FIFTH));
        System.out.printf("4개 일치 (%,d원) - %d개\n", Rank.FOURTH.getPrizeMoney(), rankCounts.get(Rank.FOURTH));
        System.out.printf("5개 일치 (%,d원) - %d개\n", Rank.THIRD.getPrizeMoney(), rankCounts.get(Rank.THIRD));
        System.out.printf("5개 일치, 보너스 볼 일치 (%,d원) - %d개\n", Rank.SECOND.getPrizeMoney(), rankCounts.get(Rank.SECOND));
        System.out.printf("6개 일치 (%,d원) - %d개\n", Rank.FIRST.getPrizeMoney(), rankCounts.get(Rank.FIRST));


    }
}
