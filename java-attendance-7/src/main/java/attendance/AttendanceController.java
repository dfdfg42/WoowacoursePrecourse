package attendance;

import attendance.domain.Crew;
import attendance.domain.CrewRepository;
import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {

    private final Input inputView;
    private final CrewRepository crewRepository;

    private static final LocalTime MON_START = LocalTime.of(13, 0);
    private static final LocalTime OTHER_START = LocalTime.of(10, 0);

    public AttendanceController(Input inputView, CrewRepository crewRepository) {
        this.inputView = inputView;
        this.crewRepository = crewRepository;
    }

    public void run() {
        while (true) {
            try {
                inputView.mainView();
                String input = Console.readLine();

                if (input.equals("Q")) break;
                if (input.equals("1")) checkAttendance();
                else if (input.equals("2")) updateAttendance();
                else if (input.equals("3")) showCrewLog();
                else if (input.equals("4")) showExpulsionCandidates();
                else System.out.println("잘못된 입력입니다.");

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                // [중요] 테스트 환경에서 예외를 던져줘야 할 경우 주석 해제
                throw e;
            }
        }
    }

    // 1. 출석 확인
    private void checkAttendance() {
        validateWeekend(); // 주말 체크

        // 1. 닉네임 입력
        String name = inputView.checkAttendanceView();

        // [수정 포인트] 시간을 입력받기 전에 닉네임이 존재하는지 '먼저' 검증해야 합니다.
        // 여기서 예외가 발생하면 아래 시간 입력 코드는 실행되지 않고 catch 블록으로 넘어갑니다.
        Crew crew = getCrewOrThrow(name);

        // 2. 시간 입력 (닉네임이 검증된 후에 실행됨)
        LocalDateTime attendanceDate = inputView.attendancetimeInputView();

        // 중복 체크
        for (LocalDateTime time : crew.attendances) {
            if (time.toLocalDate().equals(attendanceDate.toLocalDate())) {
                System.out.println("이미 출석 기록이 존재합니다.");
                return;
            }
        }

        crew.attendances.add(attendanceDate);
        printAttendanceResult(attendanceDate);
    }

    // 2. 출석 수정
    private void updateAttendance() {
        validateWeekend();

        System.out.println("출석을 수락하려는 크루의 닉네임을 입력해 주세요.");
        String name = Console.readLine();
        Crew crew = getCrewOrThrow(name);

        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        int day = parseInteger(Console.readLine());

        LocalDateTime targetRecord = findRecordByDay(crew, day);
        if (targetRecord == null) {
            throw new IllegalArgumentException("[ERROR] 해당 날짜의 출석 기록이 없습니다.");
        }

        System.out.println("언제로 변경하겠습니까?");
        LocalTime newTime = parseTime(Console.readLine());
        LocalDateTime newRecord = LocalDateTime.of(targetRecord.toLocalDate(), newTime);

        crew.attendances.remove(targetRecord);
        crew.attendances.add(newRecord);

        printUpdateResult(targetRecord, newRecord);
    }

    // 3. 기록 확인
    private void showCrewLog() {
        System.out.println("닉네임을 입력해 주세요.");
        String name = Console.readLine();
        Crew crew = getCrewOrThrow(name);

        System.out.println("이번 달 " + crew.getName() + "의 출석 기록입니다.");

        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate yesterday = DateTimes.now().toLocalDate().minusDays(1);

        int[] counts = new int[3]; // 0:출석, 1:지각, 2:결석

        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        for (LocalDate date = startDate; !date.isAfter(yesterday); date = date.plusDays(1)) {
            if (isWeekend(date)) continue;

            LocalDateTime record = crew.getAttendanceAt(date);

            if (record != null) {
                String status = getAttendanceStatus(record);
                System.out.println(date.format(dateFmt) + " " + record.format(timeFmt) + " (" + status + ")");
                updateCounts(counts, status);
            } else {
                System.out.println(date.format(dateFmt) + " --:-- (결석)");
                counts[2]++;
            }
        }

        System.out.printf("\n출석: %d회\n지각: %d회\n결석: %d회\n", counts[0], counts[1], counts[2]);
        printRiskMessage(counts[2], counts[1]);
    }

    // 4. 제적 위험자 확인
    private void showExpulsionCandidates() {
        System.out.println("제적 위험자 조회 결과");
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate yesterday = DateTimes.now().toLocalDate().minusDays(1);

        List<CrewStat> stats = new ArrayList<>();

        for (Crew crew : crewRepository.findAll()) {
            int late = 0, absent = 0;

            for (LocalDate date = startDate; !date.isAfter(yesterday); date = date.plusDays(1)) {
                if (isWeekend(date)) continue;

                LocalDateTime record = crew.getAttendanceAt(date);
                if (record != null) {
                    String status = getAttendanceStatus(record);
                    if ("지각".equals(status)) late++;
                    else if ("결석".equals(status)) absent++;
                } else {
                    absent++;
                }
            }

            if (absent + (late / 3) >= 2) {
                stats.add(new CrewStat(crew.getName(), late, absent));
            }
        }

        stats.sort((s1, s2) -> {
            int score1 = s1.absent + (s1.late / 3);
            int score2 = s2.absent + (s2.late / 3);
            return score1 != score2 ? score2 - score1 : s1.name.compareTo(s2.name);
        });

        for (CrewStat s : stats) {
            String status = getRiskStatus(s.absent, s.late);
            System.out.printf("- %s: 결석 %d회, 지각 %d회 %s\n", s.name, s.absent, s.late, status);
        }
    }

    // --- Helper Methods ---

    private void validateWeekend() {
        LocalDateTime now = DateTimes.now();
        if (isWeekend(now.toLocalDate())) {
            DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
            throw new IllegalArgumentException("[ERROR] " + now.format(dateFmt) + "은 등교일이 아닙니다.");
        }
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    private Crew getCrewOrThrow(String name) {
        Crew crew = crewRepository.findByName(name);
        if (crew == null) throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        return crew;
    }

    private LocalDateTime findRecordByDay(Crew crew, int day) {
        for (LocalDateTime time : crew.attendances) {
            if (time.getDayOfMonth() == day) return time;
        }
        return null;
    }

    private String getAttendanceStatus(LocalDateTime time) {
        LocalTime start = (time.getDayOfWeek() == DayOfWeek.MONDAY) ? MON_START : OTHER_START;
        LocalTime access = time.toLocalTime();

        if (access.isAfter(start.plusMinutes(30))) return "결석";
        if (access.isAfter(start.plusMinutes(5))) return "지각";
        return "출석";
    }

    private void updateCounts(int[] counts, String status) {
        if ("출석".equals(status)) counts[0]++;
        else if ("지각".equals(status)) counts[1]++;
        else counts[2]++;
    }

    // [수정된 부분] 점수에 따라 정확한 안내 멘트 출력
    private void printRiskMessage(int absent, int late) {
        int score = absent + (late / 3);

        if (score >= 5) {
            System.out.println("제적 대상자입니다.");
        } else if (score >= 3) {
            System.out.println("면담 대상자입니다.");
        } else if (score >= 2) {
            System.out.println("경고 대상자입니다.");
        } else {
            System.out.println("면담 대상자가 아닙니다.");
        }
    }

    // Function 4번용 (괄호 형식 반환)
    private String getRiskStatus(int absent, int late) {
        int score = absent + (late / 3);
        if (score >= 5) return "(제적)";
        if (score >= 3) return "(면담)";
        if (score >= 2) return "(경고)";
        return "";
    }

    private void printAttendanceResult(LocalDateTime time) {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
        System.out.println(time.format(dateFmt) + " " + time.format(timeFmt) + " (" + getAttendanceStatus(time) + ")");
    }

    private void printUpdateResult(LocalDateTime oldTime, LocalDateTime newTime) {
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("MM월 dd일 E요일");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!\n",
                oldTime.format(dateFmt), oldTime.format(timeFmt), getAttendanceStatus(oldTime),
                newTime.format(timeFmt), getAttendanceStatus(newTime));
    }

    private int parseInteger(String input) {
        try { return Integer.parseInt(input); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("[ERROR] 숫자만 입력해 주세요."); }
    }

    private LocalTime parseTime(String input) {
        try { return LocalTime.parse(input, DateTimeFormatter.ofPattern("HH:mm")); }
        catch (Exception e) { throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다."); }
    }

    static class CrewStat {
        String name; int late; int absent;
        public CrewStat(String name, int late, int absent) {
            this.name = name; this.late = late; this.absent = absent;
        }
    }
}