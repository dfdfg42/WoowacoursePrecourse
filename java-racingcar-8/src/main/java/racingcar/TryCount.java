package racingcar;

public class TryCount {

    private final int count;

    public TryCount(String input) {
        int parsedNumber = validateNumber(input);

        validateRange(parsedNumber);

        this.count = parsedNumber;
    }


    private int validateNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // "a", " " 등이 들어오면 예외 발생 (TryCountTest의 2번 테스트 통과)
            throw new IllegalArgumentException("시도 횟수는 숫자여야 합니다.");
        }
    }


    private void validateRange(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("시도 횟수는 1 이상이어야 합니다.");
        }
    }


    public int getCount() {
        return this.count; // (TryCountTest의 1번 테스트 통과)
    }
}