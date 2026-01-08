package oncall;

import camp.nextstep.edu.missionutils.Console;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class Input {

    public String[] monthDayInput() {
        System.out.print("비상 근무를 배정할 월과 시작 요일을 입력하세요> ");

        String input = Console.readLine();
        String[] parts = input.split(",");

        if (parts.length != 2) {
            throw new IllegalArgumentException("[ERROR] 입력 형식이 올바르지 않습니다. (예: 5,월)");
        }

        String month = parts[0].trim();
        String dayOfWeek = parts[1].trim();

        validationMonth(month);
        validationDayOfWeek(dayOfWeek);

        return new String[]{month, dayOfWeek};
    }

    // [수정] String[] 배열 반환
    public String[] inputWeekDaySchedule() {
        System.out.print("평일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");

        String str = Console.readLine();
        String[] workerArray = str.split(",");

        // 공백 제거 (예: "준팍, 도밥" -> "준팍", "도밥")
        // 배열을 직접 수정합니다.
        for (int i = 0; i < workerArray.length; i++) {
            workerArray[i] = workerArray[i].trim();
        }

        validationWorker(workerArray);

        return workerArray; // 배열 그대로 반환
    }

    // [수정] String[] 배열 반환
    public String[] inputHoliDaySchedule() {
        System.out.print("휴일 비상 근무 순번대로 사원 닉네임을 입력하세요> ");

        String str = Console.readLine();
        String[] workerArray = str.split(",");

        for (int i = 0; i < workerArray.length; i++) {
            workerArray[i] = workerArray[i].trim();
        }

        validationWorker(workerArray);

        return workerArray; // 배열 그대로 반환
    }

    private void validationMonth(String monthInput) {
        try {
            int month = Integer.parseInt(monthInput);
            if (month < 1 || month > 12) {
                throw new IllegalArgumentException("[ERROR] 월은 1에서 12 사이의 숫자여야 합니다.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 월은 숫자로 입력해야 합니다.");
        }
    }

    private void validationDayOfWeek(String dayOfWeek) {
        List<String> days = List.of("일", "월", "화", "수", "목", "금", "토");

        if (!days.contains(dayOfWeek)) {
            throw new IllegalArgumentException("[ERROR] 요일은 '일, 월, 화, 수, 목, 금, 토' 중에서 입력해야 합니다.");
        }
    }

    private void validationWorker(String[] workers) {

        // 1. 최대 인원 수 체크
        if (workers.length > 35) {
            throw new IllegalArgumentException("[ERROR] 사원수가 35명을 넘으면 안됩니다");
        }

        // 2. 닉네임 길이 및 중복 체크
        // Set을 사용하여 중복을 확인합니다.
        Set<String> uniqueWorkers = new HashSet<>();

        for (String worker : workers) {
            // 길이 체크
            if (worker.length() > 5) {
                throw new IllegalArgumentException("[ERROR] 이름 길이가 5자를 넘으면 안됩니다.");
            }
            // 중복 체크 (Set에 넣을 때 이미 있으면 false 반환)
            if (!uniqueWorkers.add(worker)) {
                throw new IllegalArgumentException("[ERROR] 비상 근무자에 중복된 닉네임이 있습니다.");
            }
        }
    }
}