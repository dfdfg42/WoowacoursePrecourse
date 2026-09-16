package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringCalculatorTest {

    private StringCalculator calculator;

    @BeforeEach
    void setUp() {
        //각 테스트가 실행되기 전에 계산기 객체를 새로 생성
        calculator = new StringCalculator();
    }

    @Test
    @DisplayName("빈 문자열을 입력하면 0을 입력한다.")
    void emptyString_shouldReturnZero() {
        //given(준비)
        String input = "";

        //when(실행)
        int result = calculator.calculate(input);

        //then(검증)
        assertThat(result).isEqualTo(0);

    }

    @Test
    @DisplayName("null을 입력하면 0을 반환한다")
    void null_shouldReturnZero() {
        //given(준비)
        String input = null;

        //when(실행)
        int result = calculator.calculate(input);

        //then(검증)
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("쉼표(,) 구분자가 포함된 문자열의 합을 반환한다.")
    void commaSeparatedNumbers_shouldReturnSum() {
        // given (준비)
        String input = "1,2";

        // when (실행)
        int result = calculator.calculate(input);

        // then (검증)
        // 1 + 2 = 3 이므로 3인지 확인
        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("숫자가 하나만 입력될 경우 해당 숫자를 반환한다.")
    void singleNumber_shouldReturnItself() {
        // given (준비)
        String input = "1";

        // when (실행)
        int result = calculator.calculate(input);

        // then (검증)
        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("콜론(:) 구분자가 포함된 문자열의 합을 반환한다.")
    void colonSeparatedNumbers_shouldReturnSum() {
        // given (준비)
        String input = "1:2";

        // when (실행)
        int result = calculator.calculate(input);

        // then (검증)
        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("쉼표(,)와 콜론(:) 구분자가 혼용된 문자열의 합을 반환한다.")
    void mixedSeparators_shouldReturnSum() {
        // given (준비)
        String input = "1,2:3"; // 요구 사항 예시

        // when (실행)
        int result = calculator.calculate(input);

        // then (검증)
        assertThat(result).isEqualTo(6);
    }

    @Test
    @DisplayName("커스텀 구분자(//와 \n 사이)를 지정하여 합계를 반환한다.")
    void customSeparator_shouldReturnSum() {
        // given (준비)
        String input = "//;\\n1;2;3"; // 요구 사항 예시

        // when (실행)
        int result = calculator.calculate(input);

        // then (검증)
        assertThat(result).isEqualTo(6);
    }

    @Test
    @DisplayName("다른 문자(%)를 커스텀 구분자로 지정하여 합계를 반환한다.")
    void customSeparator_withDifferentSeparator_shouldReturnSum() {
        // given (준비)
        String input = "//%\n1%2%3";

        // when (실행)
        int result = calculator.calculate(input);

        // then (검증)
        assertThat(result).isEqualTo(6);
    }

    @Test
    @DisplayName("음수가 포함된 문자열을 입력하면 IllegalArgumentException을 발생시킨다.")
    void negativeNumber_shouldThrowException() {
        // given (준비)
        String input = "1,-2,3";


        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(input);
        });
    }

    @Test
    @DisplayName("숫자 이외의 문자가 포함된 문자열을 입력하면 IllegalArgumentException을 발생시킨다.") // <- 여기도 수정
    void invalidCharacter_shouldThrowException() {
        // given (준비)
        String input = "1,a,3"; // 'a'는 숫자가 아님

        // when & then (실행 및 검증)
        // 기대하는 예외를 NumberFormatException -> IllegalArgumentException.class로 변경
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculate(input);
        });
    }
}
