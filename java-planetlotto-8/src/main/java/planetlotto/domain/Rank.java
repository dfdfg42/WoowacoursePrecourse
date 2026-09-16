package planetlotto.domain;

import java.util.Arrays;

public enum Rank {

    FIRST(100_000_000, 5, false),
    SECOND(10_000_000, 4, true),
    THIRD(1_500_000, 4, false),
    FOURTH(500_000, 3, true),
    FIFTH(5_000, 2, true),
    MISS(0, 0, false);


    private final int prizeMoney;
    private final int matchCount;
    private final boolean needsBonus;

    Rank(int prizeMoney, int matchCount, boolean needsBonus) {
        this.prizeMoney = prizeMoney;
        this.matchCount = matchCount;
        this.needsBonus = needsBonus;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public static Rank valueOf(int matchCount, boolean hasBonus) {

        if (matchCount == 5) {
            return FIRST;
        }

        if (matchCount == 4 && hasBonus) {
            return SECOND;
        }

        if (matchCount == 4 && !hasBonus) {
            return THIRD;
        }

        if (matchCount == 3 && hasBonus) {
            return FOURTH;
        }

        if (matchCount == 2 && !hasBonus) {
            return FIFTH;
        }


        return Arrays.stream(values())
                .filter(rank -> rank.matchCount == matchCount && !rank.needsBonus) // 보너스가 필요 없는 랭크 중 일치 개수가 같은 것을 찾음
                .findFirst()
                .orElse(MISS);
    }
}
