package lotto;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {

    private static final int MIN_NUMBER = 1;
    private static final int MAX_NUMBER = 45;

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }

        Set<Integer> numbersSet = new HashSet<>(numbers);
        if(numbersSet.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호에 중복된 숫자가 있을 수 없습니다.");
        }

        for (int number : numbersSet) {
            validateRange(number);
        }
    }


    private void validateRange(int number) {
        if (number < MIN_NUMBER || number > MAX_NUMBER) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는" + MIN_NUMBER + "부터 " + MAX_NUMBER + "사이의 숫자여야 합니다");
        }
    }
}
