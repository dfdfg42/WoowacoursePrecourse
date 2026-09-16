package oncall;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        Input inputView = new Input();
        Output outputView = new Output();
        MatchService matchService = new MatchService();

        // 1. 월, 요일 입력 (에러 나면 재시도)
        String[] monthDay;
        while (true) {
            try {
                monthDay = inputView.monthDayInput();
                break; // 성공하면 탈출
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        int month = Integer.parseInt(monthDay[0]);
        String startDay = monthDay[1];

        // 2. 평일 근무자 입력 (에러 나면 재시도)
        String[] weekWorkers;
        while (true) {
            try {
                weekWorkers = inputView.inputWeekDaySchedule();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // 3. 휴일 근무자 입력 (에러 나면 재시도)
        String[] holidayWorkers;
        while (true) {
            try {
                holidayWorkers = inputView.inputHoliDaySchedule();
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // 4. 비상 근무표 생성 (핵심 로직 연결)
        List<WorkDay> workSheet = matchService.makeMatch(month, startDay, weekWorkers, holidayWorkers);

        // 5. 결과 출력
        outputView.showResult(workSheet);
    }
}