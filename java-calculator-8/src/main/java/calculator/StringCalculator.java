package calculator;

public class StringCalculator {

    public int calculate(String input) {
        // 1단계 요구사항: 빈 문자열 또는 null이면 0을 반환한다.
        if (input == null || input.isEmpty()) {
            return 0;
        }

        // 3단계 기능: 쉼표(,) 또는 콜론(:)을 구분자로 사용
        // 정규표현식을 사용하여 "," 또는 ":"를 기준으로 문자열을 쪼갬
        String[] numbers = input.split(",|:");

        int sum = 0;
        for (String number : numbers) {

            sum += Integer.parseInt(number);
        }


        return sum;
    }
}
