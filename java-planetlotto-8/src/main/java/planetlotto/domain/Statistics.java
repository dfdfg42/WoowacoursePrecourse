package planetlotto.domain;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    private static final int LOTTO_PRICE = 500;

    private final Map<Rank, Integer> rankCounts;

    public Statistics(List<Lotto> purchasedLottos, Lotto winningLotto, int bonusNumber) {
        this.rankCounts = new EnumMap<>(Rank.class);
        for (Rank rank : Rank.values()) {
            rankCounts.put(rank, 0);
        }

        calculate(purchasedLottos, winningLotto, bonusNumber);
    }

    private void calculate(List<Lotto> purchasedLottos, Lotto winningLotto, int bonusNumber){
        for(Lotto lotto : purchasedLottos){
            int matchCount = lotto.matchNumber(winningLotto);
            boolean bonusMatched = lotto.hasBonusNumber(bonusNumber);

            Rank rank = Rank.valueOf(matchCount,bonusMatched);

            rankCounts.put(rank, rankCounts.get(rank) + 1);
        }
    }

    public Map<Rank, Integer> getRankCounts() {
        return this.rankCounts;
    }


}
