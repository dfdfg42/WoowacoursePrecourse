package planetlotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planetlotto.domain.Lotto;
import planetlotto.service.LottoGenerator;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static org.assertj.core.api.Assertions.assertThat;

public class LottoGenerateTest {

    @Test
    @DisplayName("구매 금액만큼 로또 발매")
    void 로또_구매_확인(){
        Purchase purchase = new Purchase(1500); //3장
        LottoGenerator generator = new LottoGenerator();

        // when (실행)
        assertRandomUniqueNumbersInRangeTest(() -> {
                    List<Lotto> lottos = generator.purchaseLottos(purchase);

                    // then (검증)
                    assertThat(lottos).hasSize(3);


                    assertThat(lottos.get(0).getNumbers()).containsExactly(1, 2, 3, 4, 5);
                    assertThat(lottos.get(1).getNumbers()).containsExactly(7, 8, 9, 10, 11);
                    assertThat(lottos.get(2).getNumbers()).containsExactly(13, 14, 15, 16, 17);
                },
                List.of(1, 2, 3, 4, 5),
                List.of(7, 8, 9, 10, 11),
                List.of(13, 14, 15, 16, 17)
        );
    }
}
