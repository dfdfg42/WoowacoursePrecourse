package racingcar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// 1. Assertions에서 assertRandoms를 import 합니다.
import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
// 2. AssertJ의 assertThat을 import 합니다.
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// 3. extends NsTest를 쓰지 않습니다. (중요)
public class CarTest {

    @Test
    @DisplayName("자동차를 생성하면 이름과 초기 위치(0)를 가진다.")
    void createCar() {
        // given
        String carName = "pobi";

        // when
        Car car = new Car(carName);

        // then
        assertThat(car.getName()).isEqualTo(carName);
        assertThat(car.getPosition()).isEqualTo(0);
    }

    @Test
    @DisplayName("무작위 값이 4 이상일 경우 전진한다.")
    void moveForward() {
        // given
        Car car = new Car("pobi");

        // when
        assertRandomNumberInRangeTest(() -> {
            car.move();
        }, 4); // 랜덤 값을 4로 고정

        // then
        assertThat(car.getPosition()).isEqualTo(1);
    }

    @Test
    @DisplayName("무작위 값이 3 이하일 경우 멈춘다.")
    void stop() {
        // given
        Car car = new Car("pobi");

        // when
        // 4. 알려주신 함수를 사용
        assertRandomNumberInRangeTest(() -> {
            car.move();
        }, 3); // 랜덤 값을 3 (정지)로 고정

        // then
        assertThat(car.getPosition()).isEqualTo(0);
    }

    @Test
    @DisplayName("자동차 이름이 5자를 초과하면 예외 발생")
    void carName_OverFive(){
        String longName = "pobipobi";

        assertThatThrownBy(() -> new Car(longName))
                .isInstanceOf(IllegalArgumentException.class);
    }

}