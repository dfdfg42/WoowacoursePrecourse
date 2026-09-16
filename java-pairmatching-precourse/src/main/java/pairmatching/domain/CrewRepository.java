package pairmatching.domain;

import java.util.ArrayList;
import java.util.List;

public class CrewRepository {
    private final List<Crew> crews = new ArrayList<>();

    public void add(Crew crew) {
        crews.add(crew);
    }

    public List<Crew> findByCourse(Course course) {
        List<Crew> result = new ArrayList<>();
        for (Crew crew : crews) {
            if (crew.getCourse() == course) {
                result.add(crew);
            }
        }
        return result;
    }

    public List<Crew> findAll() {
        return new ArrayList<>(crews);
    }

    public void clear() {
        crews.clear();
    }
}
