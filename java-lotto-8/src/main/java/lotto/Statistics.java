package lotto;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {

    private static final int LOTTO_PRICE = 1000;

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

    public double getProfitRate() {

        long totalPurchasePrice = (long) rankCounts.values().stream()
                .mapToInt(Integer::intValue)
                .sum() * LOTTO_PRICE;

        long totalPrizeMoney = 0;
        for (Map.Entry<Rank, Integer> entry : rankCounts.entrySet()) {
            Rank rank = entry.getKey();
            int count = entry.getValue();
            totalPrizeMoney += (long) rank.getPrizeMoney() * count;
        }

        if (totalPurchasePrice == 0) {
            return 0.0;
        }

        double profitRate = (double) totalPrizeMoney / totalPurchasePrice * 100.0;


        return Math.round(profitRate * 10.0) / 10.0;
    }
}
