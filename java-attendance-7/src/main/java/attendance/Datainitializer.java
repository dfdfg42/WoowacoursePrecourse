package attendance;

import attendance.domain.Crew;
import attendance.domain.CrewRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // 필수 import
import java.util.ArrayList;
import java.util.List;

public class Datainitializer {

    private final CrewRepository crewRepository;

    public Datainitializer(CrewRepository crewRepository) {
        this.crewRepository = crewRepository;
    }

    public void loadFile() {
        try {
            File file = new File("C:\\Users\\SSAFY\\Desktop\\temp\\java-attendance-7\\src\\main\\resources\\attendances.csv");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            br.readLine();

            // 1. 날짜 포맷 지정 (CSV 파일의 시간 형식에 맞춰야 함)
            // 예: "2024-12-14 10:30" 형식이라면 아래와 같이 작성
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                String name = data[0];
                String timeString = data[1];

                // 2. 로직 수정: 이름이 없으면(!) 추가
                // isCrewsHave()에 인자(name)를 넘겨줘야 합니다.
                if (!crewRepository.isCrewsHave(name) ) {
                    // String만 넣는게 아니라 Crew 객체를 만들어서 저장해야 합니다.
                    crewRepository.add(name);
                }

                // 3. 크루 찾기
                Crew tempCrew = crewRepository.findByName(name);

                // 4. String -> LocalDateTime 변환 후 추가
                LocalDateTime attendanceTime = LocalDateTime.parse(timeString, formatter);
                tempCrew.attendances.add(attendanceTime);
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}