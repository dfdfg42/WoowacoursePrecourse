package calculator;

// 4단계 기능을 위해 정규표현식(Regex) 관련 클래스를 import 합니다.
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    private final Pattern customPattern = Pattern.compile("//(.)\n(.*)");

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
            total += Integer.parseInt(number);
        }
        return total;
    }
}