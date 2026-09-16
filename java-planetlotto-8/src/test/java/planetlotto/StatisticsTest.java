package planetlotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planetlotto.domain.Lotto;
import planetlotto.domain.Rank;
import planetlotto.domain.Statistics;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StatisticsTest {
    private Lotto winningLotto;
    private int bonusNumber;
    private List<Lotto> purchasedLottos;

    @BeforeEach
    void setUp() {

        winningLotto = new Lotto(List.of(1, 2, 3, 4, 5));

        bonusNumber = 7;

        // 구매한 로또 목록 (총 8,000원 구매)
        purchasedLottos = List.of(
                new Lotto(List.of(8, 21, 23, 24, 22)), // 꽝
                new Lotto(List.of(3, 5, 11, 16, 22)), // 5등 (1,5 일치)
                new Lotto(List.of(7, 11, 16, 24, 26)), // 꽝
                new Lotto(List.of(1, 8, 11, 25, 21)), // 꽝
                new Lotto(List.of(13, 14, 16, 28, 22)), // 꽝
                new Lotto(List.of(7, 11, 30, 20, 22)), // 꽝
                new Lotto(List.of(2, 13, 22, 24, 28)), // 꽝
                new Lotto(List.of(1, 3, 8, 14, 22))  // 5등 (1, 3 일치)
        );
    }

    @Test
    @DisplayName("구매한 로또 목록을 기준으로 당첨 통계를 정확히 집계한다.")
    void 구매한_로또_목록의_당첨통계_확인() {
        // when
        Statistics stats = new Statistics(purchasedLottos, winningLotto, bonusNumber);

        Map<Rank, Integer> rankCounts = stats.getRankCounts();

        // then
        assertThat(rankCounts.getOrDefault(Rank.FIFTH, 0)).isEqualTo(2);
        assertThat(rankCounts.getOrDefault(Rank.MISS, 0)).isEqualTo(6);
        assertThat(rankCounts.getOrDefault(Rank.FOURTH, 0)).isEqualTo(0);
    }
}
