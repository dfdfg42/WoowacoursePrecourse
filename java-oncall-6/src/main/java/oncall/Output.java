package oncall;

import java.util.List;

public class Output {

    public void showResult(List<WorkDay> workDays) {

        for (WorkDay workDay : workDays) {

            // 1. 데이터 가져오기
            int month = workDay.getMonth();
            int day = workDay.getDay();
            String dayOfWeek = workDay.getDayOfWeek();
            String worker = workDay.getWorker();

            // 2. (휴일) 표기 로직
            String holidayMark = "";
            if (workDay.isLegalHoliday() && !isWeekend(dayOfWeek)) {
                holidayMark = "(휴일)";
            }

            // 3. 출력 (수정됨)
            // \n 대신 %n을 사용해야 테스트 통과 (OS별 줄바꿈 자동 처리)
            System.out.printf("%d월 %d일 %s%s %s%n", month, day, dayOfWeek, holidayMark, worker);
        }
    }

    private boolean isWeekend(String dayOfWeek) {
        return dayOfWeek.equals("토") || dayOfWeek.equals("일");
    }
}