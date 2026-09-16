package calculator;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {

        StringCalculator calculator = new StringCalculator();


        String input = Console.readLine();

        int result = calculator.calculate(input);

        System.out.println("결과 : " + result);

    }

}
