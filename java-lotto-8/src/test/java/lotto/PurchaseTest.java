package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseTest {

    @Test
    @DisplayName("유효한 금액(1000원 단위)으로 Purchase 객체를 생성한다.")
    void 유효한_금액으로_Purchase_객체_생성() {
        // given
        String input = "3000";

        // when
        Purchase purchase = new Purchase(input);

        // then
        assertThat(purchase.getAmountAsInt()).isEqualTo(3000);
        assertThat(purchase.getLottoCount()).isEqualTo(3); // 3000원 = 3장
    }

    @DisplayName("입력값이 숫자가 아닐 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", " ", "1000a"})
    void 숫자가_아닌_입력값은_예외가_발생한다(String input) {
        // when & then
        assertThatThrownBy(() -> new Purchase(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 1000원 단위가 아닐 경우 예외가 발생한다.")
    void 천원_단위가_아닌_금액은_예외가_발생한다() {
        // given
        String input = "1500"; // 1000으로 나누어 떨어지지 않음

        // when & then
        assertThatThrownBy(() -> new Purchase(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 1000원 미만일 경우 예외가 발생한다.")
    void 천원_미만의_금액은_예외가_발생한다() {
        // given
        String input = "0"; // 1000원 미만

        // when & then
        assertThatThrownBy(() -> new Purchase(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
