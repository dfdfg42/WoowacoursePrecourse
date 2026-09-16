package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {

    public String name;
    public int lateCount = 0;
    public int outCount = 0; //결석

    public List<LocalDateTime> attendances = new ArrayList<>();

    public Crew(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addAttendance(LocalDateTime localDateTime){
        attendances.add(localDateTime);
    }

    public LocalDateTime getAttendanceAt(LocalDate date) {
        for (LocalDateTime time : attendances) {
            if (time.toLocalDate().equals(date)) {
                return time;
            }
        }
        return null; // 기록 없음 (결석 추정)
    }


}
