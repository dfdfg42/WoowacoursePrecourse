package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호의 숫자는 1~45 사이여야 합니다 (45초과)")
    @Test
    void 로또_번호가_45_를_벗어나면_예외가_발생한다(){
        //given 준비
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 46);

        //when then
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호의 숫자는 1~45 사이여야 합니다 (0인경우")
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
        Lotto lotto = new Lotto(List.of(6, 5, 4, 3, 2, 1));

        List<Integer> numbers = lotto.getNumbers();

        assertThat(numbers).containsExactly(1, 2, 3, 4, 5, 6);

    }

    @DisplayName("당첨 번호와 일치하는 개수를 반환")
    @Test
    void 로또_번호와_당첨번호의_일치하는개수_반환(){
        //given
        Lotto lotto = new Lotto(List.of(6, 5, 4, 3, 2, 1));
        Lotto winningLotto = new Lotto(List.of(4, 5, 6, 7, 8, 9));

        //when 실행

        int matchCount = lotto.matchNumber(winningLotto);

        assertThat(matchCount).isEqualTo(3);
    }

    @DisplayName("보너스 번호 포함 여부 확인(있는경우)")
    @Test
    void 로또가_보너스_번호를_포함하는지_확인(){

        //given
        Lotto lotto = new Lotto(List.of(6, 5, 4, 3, 2, 1));
        int bonusNumber = 6;

        //when
        boolean hasBonusNumber = lotto.hasBonusNumber(6);

        assertThat(hasBonusNumber).isTrue();
    }

    @DisplayName("보너스 번호 포함 여부 (없는경우)")
    @Test
    void 로또가_보너스_번호가_없는지_확인(){
        //given
        Lotto lotto = new Lotto(List.of(6, 5, 4, 3, 2, 1));
        int bonusNumber = 10;

        //when
        boolean hasBonusNumber = lotto.hasBonusNumber(10);

        assertThat(hasBonusNumber).isFalse();
    }

}
