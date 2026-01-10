package planetlotto;

public class Purchase {
    private static final int LOTTO_PRICE = 500;

    private final int amount;


    public Purchase(String input) {
        int parsedAmount = validateNumber(input);
        validateAmount(parsedAmount);

        this.amount = parsedAmount;
    }


    private int validateNumber(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 숫자여야 합니다.");
        }
    }

    private void validateAmount(int amount) {
        if (amount < LOTTO_PRICE) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 500원 이상이어야 합니다.");
        }
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 500원 단위여야 합니다.");
        }
    }


    public int getAmountAsInt() {
        return this.amount;
    }

    public int getLottoCount() {
        return this.amount / LOTTO_PRICE;
    }

}
