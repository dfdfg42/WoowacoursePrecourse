package pairmatching.domain;

import java.util.ArrayList;
import java.util.List;

public class Pair {

    private final List<String> crewNames = new ArrayList<>();

    public Pair(List<String> crewNames) {
        this.crewNames.addAll(crewNames);
    }

    public List<String> getCrewNames() {
        return crewNames;
    }

    public List<String> getMembers() {
        return crewNames;
    }

    public void add(String crewName) {
        crewNames.add(crewName);
    }

    @Override
    public String toString() {
        return String.join(" : ", crewNames);
    }
}
