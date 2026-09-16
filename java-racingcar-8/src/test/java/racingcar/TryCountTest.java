package racingcar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TryCountTest {

    @Test
    @DisplayName("유효한 숫자 문자열로 시도 횟수를 생성한다.")
    void createTryCount_WithValidNumber() {
        // given
        String input = "5";

        // when
        // TryCount 클래스는 아직 없습니다.
        TryCount tryCount = new TryCount(input);

        // then
        assertThat(tryCount.getCount()).isEqualTo(5);
    }

    @DisplayName("입력값이 숫자가 아닐 경우 예외가 발생한다.")
    @ParameterizedTest // 2. 여러 비-숫자 값으로 테스트
    @ValueSource(strings = {"a", " ", "1a2"})
    void createTryCount_WithInvalidNumber_ThrowsException(String input) {
        // when & then
        assertThatThrownBy(() -> new TryCount(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 1 미만일 경우 예외가 발생한다.")
    void createTryCount_WithNumberLessThanOne_ThrowsException() {
        // given
        String input = "0";

        // when & then
        assertThatThrownBy(() -> new TryCount(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}