package pairmatching.service;

import camp.nextstep.edu.missionutils.Randoms;
import pairmatching.domain.*;

import java.util.ArrayList;
import java.util.List;

public class PairMatch {

    private final MissionRepository missionRepository;

    public PairMatch(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public List<Pair> matchPairs(List<Crew> crews, Level level , Mission targetMission) {
            // 크루 이름 목록 준비
            List<String> crewNames = new ArrayList<>();
            for (Crew crew : crews) {
                crewNames.add(crew.getName());
            }

            // 최대 3회 재시도
            for (int attempt = 0; attempt < 3; attempt++) {
                // 랜덤으로 섞기
                List<String> shuffledCrew = Randoms.shuffle(crewNames);

                // 앞에서부터 두명씩 페어 매칭
                List<Pair> tempPairs = new ArrayList<>();

                for (int i = 0; i < shuffledCrew.size(); i += 2) {
                    if (i + 1 < shuffledCrew.size()) {
                        // 2명 페어
                        List<String> pairMembers = new ArrayList<>();
                        pairMembers.add(shuffledCrew.get(i));
                        pairMembers.add(shuffledCrew.get(i + 1));
                        tempPairs.add(new Pair(pairMembers));
                    } else {
                        // 홀수인 경우 마지막 사람을 이전 페어에 추가
                        Pair lastPair = tempPairs.get(tempPairs.size() - 1);
                        lastPair.add(shuffledCrew.get(i));
                    }
                }

                boolean isMatch = false;
                List<Mission> Missions = missionRepository.getMissions();
                // 전달받은 미션과 같은 레벨에 미션에 같은 페어가 있는지 확인해야함
                for(Mission mission : Missions){
                    if(mission.getLevel() == level && mission.getPairList() != null && !mission.getPairList().isEmpty()){
                        //레벨같은 미션의 페어들 추출
                        List<Pair> compPairs = mission.getPairList();
                        if(comparePairs(compPairs, tempPairs)){
                            isMatch = true;
                            break;
                        }
                    }

                }
                // 없다면 페어 매칭완료
                if(!isMatch){
                    return tempPairs;
                }

                // 일치하는 페어가 있다면 다음 시도로
            }

            // 3회 시도 후에도 실패
            throw new IllegalArgumentException("매칭에 실패했습니다.");
    }

    public void deletePair(){
        List<Mission> Missions = missionRepository.getMissions();
        for(Mission mission : Missions){
            if(mission.getPairList() != null) {
                mission.getPairList().clear();
            }
        }
    }

    public boolean comparePairs(List<Pair> pairs, List<Pair> pairs2) {

        for (Pair pair : pairs) {
            if (!pairs2.contains(pair)) {
                return false;
            }
        }
        return true;
    }


}

