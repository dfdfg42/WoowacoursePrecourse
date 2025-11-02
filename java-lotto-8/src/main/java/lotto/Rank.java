package lotto;

import java.util.Arrays;

public enum Rank {

    FIRST(2_000_000_000, 6, false),
    SECOND(30_000_000, 5, true),
    THIRD(1_500_000, 5, false),
    FOURTH(50_000, 4, false),
    FIFTH(5_000, 3, false),
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
        if (matchCount == 5 && hasBonus) {
            return SECOND;
        }

        return Arrays.stream(values())
                .filter(rank -> rank.matchCount == matchCount && !rank.needsBonus) // 보너스가 필요 없는 랭크 중 일치 개수가 같은 것을 찾음
                .findFirst()
                .orElse(MISS);
    }
}
