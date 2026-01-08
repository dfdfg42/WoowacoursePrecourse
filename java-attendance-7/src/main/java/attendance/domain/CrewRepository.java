package attendance.domain;

import java.util.ArrayList;
import java.util.List;

public class CrewRepository {

    // [변경] static 제거 -> private final 인스턴스 변수
    private final List<Crew> crews = new ArrayList<>();

    public boolean isCrewsHave(String name){
        for(Crew crew : crews){
            if(name.equals(crew.getName())){
                return true;
            }
        }
        return false;
    }

    public void add(String name){
        crews.add(new Crew(name));
    }

    public Crew findByName(String name){
        for(Crew crew : crews){
            if(crew.getName().equals(name)){
                return crew;
            }
        }
        return null;
    }

    // [추가] 전체 목록 조회용 (제적 확인 등에서 사용)
    public List<Crew> findAll() {
        return new ArrayList<>(crews); // 원본 보호를 위해 복사본 반환 추천
    }
}