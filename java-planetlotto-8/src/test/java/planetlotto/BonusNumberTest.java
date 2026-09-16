package planetlotto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import planetlotto.domain.BonusNumber;
import planetlotto.domain.Lotto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BonusNumberTest {

    private Lotto winningLotto;

    @BeforeEach
    void setUp() {
        // 비교 대상인 당첨 번호를 미리 생성
        winningLotto = new Lotto(List.of(1, 2, 3, 4, 5));
    }

    @Test
    @DisplayName("유효한 보너스 번호를 생성한다.")
    void 유효한_보너스_번호를_생성한다() {
        // given
        int input = 7; // 당첨 번호와 중복되지 않고 범위 내의 숫자

        // when
        BonusNumber bonusNumber = new BonusNumber(input, winningLotto);

        // then
        assertThat(bonusNumber.getNumber()).isEqualTo(7);
    }

//    @DisplayName("보너스 번호가 숫자가 아니면 예외가 발생한다.")
//    @ParameterizedTest
//    @ValueSource(strings = {"a", " ", "1a"})
//    void 숫자가_아닌_보너스_번호는_예외가_발생한다(String input) {
//        // when & then
//        assertThatThrownBy(() -> new BonusNumber(input, winningLotto))
//                .isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @DisplayName("보너스 번호가 1~30 범위를 벗어나면 예외가 발생한다.")
//    @ParameterizedTest
//    @ValueSource(strings = {"0", "31"})
//    void 보너스_번호가_범위를_벗어나면_예외가_발생한다(String input) {
//        // when & then
//        assertThatThrownBy(() -> new BonusNumber(input, winningLotto))
//                .isInstanceOf(IllegalArgumentException.class);
//    }
//
//    @Test
//    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
//    void 보너스_번호가_당첨_번호와_중복되면_예외가_발생한다() {
//        // given
//        String input = "5"; // 당첨 번호(1,2,3,4,5)에 포함됨
//
//        // when & then
//        assertThatThrownBy(() -> new BonusNumber(input, winningLotto))
//                .isInstanceOf(IllegalArgumentException.class);
//    }
}
