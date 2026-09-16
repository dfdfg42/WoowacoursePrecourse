package planetlotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import planetlotto.domain.Rank;

import static org.assertj.core.api.Assertions.assertThat;

public class RankTest {
    @DisplayName("일치 개수와 보너스 여부로 올바른 Rank를 찾는다.")
    @ParameterizedTest
    @CsvSource({
            "5, false, FIRST", // 1
            "4, true, SECOND", // 2등
            "4, false, THIRD",// 3등
            "3, true, FOURTH", // 4등
            "2, false, FIFTH",  // 5등
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
        assertThat(Rank.FIRST.getPrizeMoney()).isEqualTo(100_000_000);
        assertThat(Rank.SECOND.getPrizeMoney()).isEqualTo(10_000_000);
        assertThat(Rank.THIRD.getPrizeMoney()).isEqualTo(1_500_000);
        assertThat(Rank.FOURTH.getPrizeMoney()).isEqualTo(500_000);
        assertThat(Rank.FIFTH.getPrizeMoney()).isEqualTo(5_000);
        assertThat(Rank.MISS.getPrizeMoney()).isEqualTo(0);
    }
}
