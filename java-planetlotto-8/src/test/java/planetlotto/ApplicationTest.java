package planetlotto;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
            () -> {
                run("1000", "1,2,3,4,5", "6");
                assertThat(output()).contains(
                        "2개를 구매했습니다.",
                        "[8, 11, 13, 21, 22]",
                        "[1, 3, 6, 14, 22]",
                        "당첨 통계",
                        "5개 일치 (100,000,000원) - 0개",
                        "4개 일치, 보너스 번호 일치 (10,000,000원) - 0개",
                        "4개 일치 (1,500,000원) - 0개",
                        "3개 일치, 보너스 번호 일치 (500,000원) - 0개",
                        "2개 일치, 보너스 번호 일치 (5,000원) - 1개",
                        "0개 일치 (0원) - 1개"
                );
            },
            List.of(8, 11, 13, 21, 22),
            List.of(1, 3, 6, 14, 22)
        );
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("500j");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }

    @Test
    @DisplayName("유효한 금액(500원 단위)으로 Purchase 객체를 생성한다.")
    void 유효한_금액으로_Purchase_객체_생성() {
        // given
        String input = "3000";

        // when
        Purchase purchase = new Purchase(input);

        // then
        assertThat(purchase.getAmountAsInt()).isEqualTo(3000);
        assertThat(purchase.getLottoCount()).isEqualTo(6); // 3000원 = 3장
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
    @DisplayName("입력값이 500원 단위가 아닐 경우 예외가 발생한다.")
    void 천원_단위가_아닌_금액은_예외가_발생한다() {
        // given
        String input = "1200"; // 500으로 나누어 떨어지지 않음

        // when & then
        assertThatThrownBy(() -> new Purchase(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("입력값이 500원 미만일 경우 예외가 발생한다.")
    void 천원_미만의_금액은_예외가_발생한다() {
        // given
        String input = "0"; // 1000원 미만

        // when & then
        assertThatThrownBy(() -> new Purchase(input))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
