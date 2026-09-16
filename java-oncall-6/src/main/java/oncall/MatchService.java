package oncall;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MatchService {

    private static final int[] MAX_DAYS = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final List<String> DAYS = List.of("일","월","화","수","목","금","토");
    private static final Set<Integer> LEGAL_DAYS = Set.of(101,301,505,606,815,1003,1009,1225);

    public List<WorkDay> makeMatch(int month , String startDay , String[] weekDayWorker , String[] holidayWorker){
        List<WorkDay> workDays = new ArrayList<>();

        // 요일 인덱스
        int weekIdx = DAYS.indexOf(startDay);

        //이전 근무자
        String prevWorker = "";

        // 근무자 순번 인덱스
        int weekWorkerIndex = 0;
        int holiWorkerIndex = 0;


        for(int i=1; i<= MAX_DAYS[month]; i++){

            //평일인지 (주말 체크)
            boolean isWeekend = false;
            if(weekIdx == 0 || weekIdx == 6){
                isWeekend = true;
            }

            boolean isHoliday = isLegalDay(month,i);

            // 현재 배정될 근무자를 담을 임시 변수
            String currentWorkerName = "";

            //공휴일인 경우 , 휴일인 경우
            if(isWeekend || isHoliday){
                // 인덱스 범위 초과 방지 (% length 사용)
                int currentIdx = holiWorkerIndex % holidayWorker.length;
                int nextIdx = (holiWorkerIndex + 1) % holidayWorker.length;

                // 연속 근무 체크
                if(prevWorker.equals(holidayWorker[currentIdx])){
                    // [수정] swap 호출: 배열과 인덱스를 넘김
                    swap(holidayWorker, currentIdx, nextIdx);
                }

                currentWorkerName = holidayWorker[currentIdx];
                workDays.add(new WorkDay(month, i, DAYS.get(weekIdx), isHoliday, currentWorkerName));

                // [수정] 인덱스 증가 (배열 변수에 할당 X)
                holiWorkerIndex++;

            } else { // 평일인 경우
                int currentIdx = weekWorkerIndex % weekDayWorker.length;
                int nextIdx = (weekWorkerIndex + 1) % weekDayWorker.length;

                // [수정] 평일 근무자 배열 사용 (weekDayWorker)
                if(prevWorker.equals(weekDayWorker[currentIdx])){
                    // [수정] swap 호출
                    swap(weekDayWorker, currentIdx, nextIdx);
                }

                // [수정] 평일 근무자 추가 (weekDayWorker 사용)
                currentWorkerName = weekDayWorker[currentIdx];
                workDays.add(new WorkDay(month, i, DAYS.get(weekIdx), isHoliday, currentWorkerName));

                // [수정] 변수명 수정 (weekDayWorker -> weekWorkerIndex)
                weekWorkerIndex++;
            }

            // [추가] 다음 루프를 위해 현재 근무자를 기록
            prevWorker = currentWorkerName;

            //요일증가
            weekIdx = (weekIdx+1)%7;
        }

        return workDays;
    }

    public boolean isLegalDay(int month , int day){
        int temp = month * 100 + day;
        return LEGAL_DAYS.contains(temp);
    }

    private void swap(String[] workers, int i, int j) {
        String temp = workers[i];
        workers[i] = workers[j];
        workers[j] = temp;
    }
}