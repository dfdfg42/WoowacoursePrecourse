package planetlotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import planetlotto.domain.Lotto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class LottoTest {

    @Test
    void 로또_번호의_개수가_5개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호의 숫자는 1~30 사이여야 합니다 (30초과)")
    @Test
    void 로또_번호가_30_를_벗어나면_예외가_발생한다(){
        //given 준비
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 46);

        //when then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호의 숫자는 1~30 사이여야 합니다 (0인경우")
    @Test
    void 로또_번호가_0_를_벗어나면_예외가_발생한다(){
        //given 준비
        List<Integer> numbers = List.of(0, 2, 3, 4, 5, 6);

        //when then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호를 오름차순으로 반환")
    @Test
    void 로또_번호는_오름차순으로_반환합니다(){
        //given 준비
        Lotto lotto = new Lotto(List.of(5, 4, 3, 2, 1));

        List<Integer> numbers = lotto.getNumbers();

        assertThat(numbers).containsExactly(1, 2, 3, 4, 5);

    }
}
