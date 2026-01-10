package planetlotto.domain;

public class BonusNumber {
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 30;

    private final int number;


    public BonusNumber(String input, Lotto winningLotto) {

        int parsedNumber = validateNumber(input);

        validateRange(parsedNumber);

        validateDuplicate(parsedNumber, winningLotto);

        this.number = parsedNumber;
    }

    private int validateNumber(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 숫자여야 합니다.");
        }
    }

    private void validateRange(int number) {
        if (number < MIN_LOTTO_NUMBER || number > MAX_LOTTO_NUMBER) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 " + MIN_LOTTO_NUMBER +
                    "부터 " + MAX_LOTTO_NUMBER + " 사이의 숫자여야 합니다.");
        }
    }

    private void validateDuplicate(int number, Lotto winningLotto) {
        if (winningLotto.hasBonusNumber(number)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public int getNumber() {
        return this.number;
    }
}
