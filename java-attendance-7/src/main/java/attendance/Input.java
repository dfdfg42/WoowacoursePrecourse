package attendance;

import attendance.domain.CrewRepository;
import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Input {

    // 리팩토링: 저장소를 주입받아 사용 (검증 로직 등이 필요할 경우 활용)
    private final CrewRepository crewRepository;

    public Input(CrewRepository crewRepository) {
        this.crewRepository = crewRepository;
    }

    // [사라졌던 mainView 메서드 복구]
    public void mainView() {
        LocalDateTime now = DateTimes.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 E요일");

        System.out.println("오늘은 " + now.format(formatter) + "입니다.");
        System.out.println(); // 빈 줄
        System.out.println("기능을 선택해 주세요.");
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.print("Q. ");
    }

    // 1번 기능: 닉네임 입력 (검증 로직은 Controller로 이임하거나 여기서 수행)
    public String checkAttendanceView() {
        System.out.println("닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    // 시간 입력
    public LocalDateTime attendancetimeInputView() {
        System.out.println("등교 시간을 입력해 주세요 (HH:mm)");
        String input = Console.readLine();

        try {
            // 1. 입력값을 시간으로 변환 시도
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime inputTime = LocalTime.parse(input, formatter);

            // 2. 오늘 날짜와 합치기
            LocalDate today = DateTimes.now().toLocalDate();
            return LocalDateTime.of(today, inputTime);

        } catch (DateTimeParseException e) {
            // [중요] 테스트 통과를 위한 에러 메시지
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }
    }
}