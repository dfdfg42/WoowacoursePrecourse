package racingcar;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CarTest {

    @Test
    @DisplayName("자동차를 생성하면 이름과 쵝위치를 가진다")
    void testCar() {
        //given
        String carName = "pobi";

        //when
        Car car = new Car(carName);

        //then
        assertThat(car.getName()).isEqualTo(carName);
        assertThat(car.getPosition()).isEqualTo(0);
    }
}
