package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
}
