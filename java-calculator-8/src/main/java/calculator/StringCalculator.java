package calculator;

// 4단계 기능을 위해 정규표현식(Regex) 관련 클래스를 import 합니다.
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    private final Pattern customPattern = Pattern.compile("//(.)\\\\n(.*)");

    public int calculate(String input) {
        // 1단계 기능: 빈 문자열 또는 null
        if (input == null || input.isEmpty()) {
            return 0;
        }

        // 4단계: 커스텀 구분자 형식인지 확인
        Matcher matcher = customPattern.matcher(input);
        if (matcher.find()) {
            String customSeparator = matcher.group(1); // 그룹 1: 구분자 (";")
            String numberString = matcher.group(2);   // 그룹 2: 숫자 ("1;2;3")


            return sum(numberString.split(Pattern.quote(customSeparator)));
        }


        String[] numbers = input.split(",|:");
        return sum(numbers);
    }

    private int sum(String[] numbers) {
        int total = 0;
        for (String number : numbers) {
            try {
                // 1. 문자열을 숫자로 변환
                int parsedNumber = Integer.parseInt(number);

                // 2. 음수인지 검사
                if (parsedNumber < 0) {
                    // 음수이면 즉시 예외 발생
                    throw new IllegalArgumentException("음수는 입력할 수 없습니다.");
                }

                // 3. 합계에 더하기
                total += parsedNumber;

            } catch (NumberFormatException e) {
                // 4. Integer.parseInt()가 실패한 경우 (숫자 이외의 값)
                // NumberFormatException을 IllegalArgumentException으로 변환하여 발생
                throw new IllegalArgumentException("유효하지 않은 숫자 형식입니다.", e);
            }
        }
        return total;
    }
}