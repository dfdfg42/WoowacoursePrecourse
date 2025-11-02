package lotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class StatisticsTest {

    private Lotto winningLotto;
    private int bonusNumber;
    private List<Lotto> purchasedLottos;

    @BeforeEach
    void setUp() {

        winningLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        bonusNumber = 7;

        // 구매한 로또 목록 (총 8,000원 구매)
        purchasedLottos = List.of(
                new Lotto(List.of(8, 21, 23, 41, 42, 43)), // 꽝
                new Lotto(List.of(3, 5, 11, 16, 32, 38)), // 꽝
                new Lotto(List.of(7, 11, 16, 35, 36, 44)), // 꽝
                new Lotto(List.of(1, 8, 11, 31, 41, 42)), // 꽝
                new Lotto(List.of(13, 14, 16, 38, 42, 45)), // 꽝
                new Lotto(List.of(7, 11, 30, 40, 42, 43)), // 꽝
                new Lotto(List.of(2, 13, 22, 32, 38, 45)), // 꽝
                new Lotto(List.of(1, 3, 5, 14, 22, 45))  // 5등 (1, 3, 5 일치)
        );
    }

    @Test
    @DisplayName("구매한 로또 목록을 기준으로 당첨 통계를 정확히 집계한다.")
    void 구매한_로또_목록의_당첨통계_확인() {
        // when
        Statistics stats = new Statistics(purchasedLottos, winningLotto, bonusNumber);

        Map<Rank, Integer> rankCounts = stats.getRankCounts();

        // then
        assertThat(rankCounts.getOrDefault(Rank.FIFTH, 0)).isEqualTo(1);
        assertThat(rankCounts.getOrDefault(Rank.MISS, 0)).isEqualTo(7);
        assertThat(rankCounts.getOrDefault(Rank.FOURTH, 0)).isEqualTo(0);
    }

    @Test
    @DisplayName("총 구매 금액 대비 총 수익률을 소수점 둘째 자리에서 반올림하여 계산한다.")
    void 수익률을_계산하여_반환() {
        // given
        // 8000원 구매, 5000원(5등) 당첨

        // when
        Statistics stats = new Statistics(purchasedLottos, winningLotto, bonusNumber);

        double profitRate = stats.getProfitRate();

        // then
        // (5000 / 8000) * 100 = 62.5
        assertThat(profitRate).isEqualTo(62.5);
    }
}
