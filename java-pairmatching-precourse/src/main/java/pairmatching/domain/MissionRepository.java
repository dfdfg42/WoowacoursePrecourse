package pairmatching.domain;

import java.util.ArrayList;
import java.util.List;

public class MissionRepository {

    private final List<Mission> Missions = new ArrayList<>();

    public void add(Mission mission) {
        Missions.add(mission);
    }

    public List<Mission> getMissions() {
        return Missions;
    }

    public Mission findByMissionName(String missionName) {
        for (Mission mission : Missions) {
            if(mission.getName().equals(missionName)){
                return mission;
            }
        }
        return null;
    }
}
