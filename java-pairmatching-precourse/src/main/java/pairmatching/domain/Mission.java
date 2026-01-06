package pairmatching.domain;

import java.util.List;

public class Mission {
    private final String name;
    private final Level level;

    public Mission(String name, Level level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public Level getLevel() {
        return level;
    }

    public List<Pair> pairList;

    public List<Pair> getPairList() {
        return pairList;
    }

    public void setPairList(List<Pair> pairList) {
        this.pairList = pairList;
    }
}
