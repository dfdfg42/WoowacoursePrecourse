package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class RankTest {

    @DisplayName("일치 개수와 보너스 여부로 올바른 Rank를 찾는다.")
    @ParameterizedTest
    @CsvSource({
            "6, false, FIRST",  // 1등
            "5, true, SECOND", // 2등
            "5, false, THIRD", // 3등
            "4, false, FOURTH",// 4등
            "3, false, FIFTH", // 5등
            "2, false, MISS",  // 꽝
            "1, true, MISS",   // 꽝
            "0, false, MISS"   // 꽝
    })
    void 일치번호에_따라_순위가_맞는지_확인(int matchCount, boolean hasBonus, Rank expectedRank) {
        // when
        Rank result = Rank.valueOf(matchCount, hasBonus);

        // then
        assertThat(result).isEqualTo(expectedRank);
    }

    @Test
    @DisplayName("Rank가 올바른 상금을 가지고 있는지 확인한다.")
    void 랭크의_상금이_올바른지_확인() {
        // then
        assertThat(Rank.FIRST.getPrizeMoney()).isEqualTo(2_000_000_000);
        assertThat(Rank.SECOND.getPrizeMoney()).isEqualTo(30_000_000);
        assertThat(Rank.THIRD.getPrizeMoney()).isEqualTo(1_500_000);
        assertThat(Rank.FOURTH.getPrizeMoney()).isEqualTo(50_000);
        assertThat(Rank.FIFTH.getPrizeMoney()).isEqualTo(5_000);
        assertThat(Rank.MISS.getPrizeMoney()).isEqualTo(0);
    }
}
