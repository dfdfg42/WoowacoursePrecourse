package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningLottoTest {

    @Test
    @DisplayName("당첨 로또 생성.")
    void 당첨_로또_생성() {
        // given
        String input = "1,2,3,4,5,6";

        // when
        WinningLotto winningLotto = new WinningLotto(input);

        // then
        Lotto lotto = winningLotto.getLotto();
        assertThat(lotto.getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }




}