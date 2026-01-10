package planetlotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planetlotto.domain.Lotto;
import planetlotto.domain.WinningLotto;

import static org.assertj.core.api.Assertions.assertThat;

public class WinningLottoTest {

    @Test
    @DisplayName("당첨 로또 생성.")
    void 당첨_로또_생성() {
        // given
        String input = "1,2,3,4,5";

        // when
        WinningLotto winningLotto = new WinningLotto(input);

        // then
        Lotto lotto = winningLotto.getLotto();
        assertThat(lotto.getNumbers()).containsExactly(1, 2, 3, 4, 5);
    }
}
