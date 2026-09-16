package pairmatching.service;

import pairmatching.domain.Level;
import pairmatching.domain.Mission;
import pairmatching.domain.MissionRepository;

public class MissionInitializer {

    private final MissionRepository missionRepository;

    public MissionInitializer(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public void initializeMissions() {
        // 레벨1 미션
        missionRepository.add(new Mission("자동차경주", Level.LEVEL1));
        missionRepository.add(new Mission("로또", Level.LEVEL1));
        missionRepository.add(new Mission("숫자야구게임", Level.LEVEL1));

        // 레벨2 미션
        missionRepository.add(new Mission("장바구니", Level.LEVEL2));
        missionRepository.add(new Mission("결제", Level.LEVEL2));
        missionRepository.add(new Mission("지하철노선도", Level.LEVEL2));

        // 레벨3 - 없음

        // 레벨4 미션
        missionRepository.add(new Mission("성능개선", Level.LEVEL4));
        missionRepository.add(new Mission("배포", Level.LEVEL4));

        // 레벨5 - 없음
    }
}
