package racingcar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return Collections.unmodifiableList(carList);
    }

    public List<String> getWinnerNames() {
        int maxPosition = findMaxPosition();

        return carList.stream() // carList를 스트림으로 변환
                .filter(car -> car.getPosition() == maxPosition) // 최대 위치와 같은 Car만 필터링
                .map(Car::getName) // Car 객체를 이름(String)으로 변환
                .collect(Collectors.toList()); // 리스트로 수집
    }


    private int findMaxPosition() {
        int maxPosition = 0;
        for (Car car : carList) {
            if (car.getPosition() > maxPosition) {
                maxPosition = car.getPosition();
            }
        }
        return maxPosition;
    }
}