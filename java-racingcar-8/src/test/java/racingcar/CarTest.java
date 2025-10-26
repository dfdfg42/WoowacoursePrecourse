package racingcar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomNumberInRangeTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    @DisplayName("쉼표로 구분된 문자열로 Car 리스트를 생성한다.")
    void createCars_FromNamesString() {
        // given (준비)
        String input = "pobi,woni,jun";

        // when (실행)
        // "pobi,woni,jun" 문자열을 생성자에 넘겨 Cars 객체를 생성
        Cars cars = new Cars(input);

        // then (검증)
        List<Car> carList = cars.getCarList();
        assertThat(carList).hasSize(3);
        assertThat(carList.get(0).getName()).isEqualTo("pobi");
        assertThat(carList.get(1).getName()).isEqualTo("woni");
        assertThat(carList.get(2).getName()).isEqualTo("jun");
    }

    @Test
    @DisplayName("입력된 이름 중 빈 값(공백)이 있으면 예외가 발생한다.")
    void createCars_WithBlankName_ThrowsException() {
        // given (준비)
        String input = "pobi,,jun"; // 쉼표 사이에 이름이 없음

        // when & then (실행 및 검증)
        assertThatThrownBy(() -> new Cars(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

}