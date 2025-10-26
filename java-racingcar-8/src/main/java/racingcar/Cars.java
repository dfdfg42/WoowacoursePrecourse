package racingcar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cars {

    // 1. Car 객체 목록을 저장할 final List
    private final List<Car> carList;


    public Cars(String inputNames) {
        // 2. 쉼표(,)를 기준으로 이름을 분리
        String[] names = inputNames.split(",");

        this.carList = new ArrayList<>();

        // 4. 이름 배열을 순회하며 Car 객체 생성
        for (String name : names) {
            validateName(name);
            carList.add(new Car(name));
        }
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            // "pobi,,jun" 또는 "pobi, ,jun" 같은 경우를 여기서 차단
            throw new IllegalArgumentException("자동차 이름은 비어있거나 공백일 수 없습니다.");
        }
    }

    public void playRound() {
        for (Car car : carList) {
            car.move();
        }
    }

    public List<Car> getCarList() {
        // 5. 외부에서 리스트를 수정할 수 없도록 불변 리스트(unmodifiable)로 반환
        return Collections.unmodifiableList(carList);
    }
}