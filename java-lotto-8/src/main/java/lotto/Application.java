package lotto;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import java.util.Map;

public class Application {
    public static void main(String[] args) {

        Purchase purchaseAmount = inputPurchaseAmount();

        LottoGenerator generator = new LottoGenerator();
        List<Lotto> purchasedLottos = generator.purchaseLottos(purchaseAmount);

        printPurchasedLottos(purchasedLottos);

        WinningLotto winningLotto = inputWinningLotto();

        BonusNumber bonusNumber = inputBonusNumber(winningLotto.getLotto());

        Statistics stats = new Statistics(purchasedLottos, winningLotto.getLotto(), bonusNumber.getNumber());

        printStatistics(stats);
    }


    private static Purchase inputPurchaseAmount() {
        System.out.println("구입금액을 입력해 주세요.");
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
        System.out.println("\n당첨 번호를 입력해 주세요.");
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
        System.out.println("\n보너스 번호를 입력해 주세요.");
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


    private static void printStatistics(Statistics stats) {
        Map<Rank, Integer> rankCounts = stats.getRankCounts();

        System.out.println("\n당첨 통계\n---");
        System.out.printf("3개 일치 (%,d원) - %d개\n", Rank.FIFTH.getPrizeMoney(), rankCounts.get(Rank.FIFTH));
        System.out.printf("4개 일치 (%,d원) - %d개\n", Rank.FOURTH.getPrizeMoney(), rankCounts.get(Rank.FOURTH));
        System.out.printf("5개 일치 (%,d원) - %d개\n", Rank.THIRD.getPrizeMoney(), rankCounts.get(Rank.THIRD));
        System.out.printf("5개 일치, 보너스 볼 일치 (%,d원) - %d개\n", Rank.SECOND.getPrizeMoney(), rankCounts.get(Rank.SECOND));
        System.out.printf("6개 일치 (%,d원) - %d개\n", Rank.FIRST.getPrizeMoney(), rankCounts.get(Rank.FIRST));

        // 수익률 출력
        double profitRate = stats.getProfitRate();
        System.out.printf("총 수익률은 %.1f%%입니다.\n", profitRate);
    }
}