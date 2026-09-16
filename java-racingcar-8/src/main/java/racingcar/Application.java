package racingcar;

import camp.nextstep.edu.missionutils.Console;

import java.util.List;

public class Application {
    public static void main(String[] args) {

        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        Cars cars = createCars();

        System.out.println("시도할 횟수는 몇 회인가요?");
        TryCount tryCount = createTryCount();

        System.out.println("\n실행 결과");

        for (int i = 0; i < tryCount.getCount(); i++) {
            cars.playRound(); // 1회 실행
            printRoundResult(cars.getCarList()); // 1회 결과 출력
        }

        printWinners(cars.getWinnerNames());
    }


    private static Cars createCars() {
        String inputNames = Console.readLine();
        return new Cars(inputNames);
    }


    private static TryCount createTryCount() {
        String inputCount = Console.readLine();
        return new TryCount(inputCount);
    }


    private static void printRoundResult(List<Car> carList) {
        for (Car car : carList) {
            String positionBar = "-".repeat(car.getPosition());
            System.out.println(car.getName() + " : " + positionBar);
        }
        System.out.println(); // 라운드 간 줄 바꿈
    }

    private static void printWinners(List<String> winnerNames) {
        // 쉼표(,)와 공백(" ")으로 이름들을 연결
        String winners = String.join(", ", winnerNames);
        System.out.println("최종 우승자 : " + winners);
    }
}
