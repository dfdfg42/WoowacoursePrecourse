package lotto;

import java.util.ArrayList;
import java.util.List;

public class WinningLotto {

    private final Lotto lotto;


    public WinningLotto(String input) {
        List<Integer> numbers = parseNumbers(input);

        this.lotto = new Lotto(numbers);
    }


    private List<Integer> parseNumbers(String input) {
        String[] parts = input.split(",");
        List<Integer> numbers = new ArrayList<>();

        for (String part : parts) {
            numbers.add(parseSingleNumber(part));
        }
        return numbers;
    }

    private int parseSingleNumber(String part) {
        try {
            return Integer.parseInt(part.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 숫자여야 합니다.");
        }
    }

    public Lotto getLotto() {
        return this.lotto;
    }
}