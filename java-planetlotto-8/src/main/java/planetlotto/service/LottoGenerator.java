package planetlotto.service;

import camp.nextstep.edu.missionutils.Randoms;
import planetlotto.Purchase;
import planetlotto.domain.Lotto;

import java.util.ArrayList;
import java.util.List;

public class LottoGenerator {

    private static final int LOTTO_MIN_NUMBER = 1;
    private static final int LOTTO_MAX_NUMBER = 30;
    private static final int LOTTO_NUMBER_COUNT = 5;

    public List<Lotto> purchaseLottos(Purchase amount) {
        int lottoCount = amount.getLottoCount();

        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < lottoCount; i++) {
            lottos.add(generateLotto());
        }

        return lottos;
    }


    private Lotto generateLotto() {

        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                LOTTO_MIN_NUMBER,
                LOTTO_MAX_NUMBER,
                LOTTO_NUMBER_COUNT
        );


        return new Lotto(numbers);
    }
}
