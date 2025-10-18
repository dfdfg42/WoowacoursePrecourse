package calculator;

public class StringCalculator {

    public int calculate(String input) {
        // 1단계 요구사항: 빈 문자열 또는 null이면 0을 반환한다.
        if (input == null || input.isEmpty()) {
            return 0;
        }

        //2단계 기능: 쉼표(,)를 기준으로 분리 후 합산
        String[] numbers = input.split(",");

        int sum = 0;
        for (String number : numbers) {

            sum += Integer.parseInt(number);
        }


        return sum;
    }
}
