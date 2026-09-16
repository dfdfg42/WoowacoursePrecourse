package oncall;

public class WorkDay {
    private final int month;
    private final int day;
    private final String dayOfWeek;
    private final boolean isLegalHoliday; // 법정공휴일 여부
    private final String worker;

    public WorkDay(int month, int day, String dayOfWeek, boolean isLegalHoliday, String worker) {
        this.month = month;
        this.day = day;
        this.dayOfWeek = dayOfWeek;
        this.isLegalHoliday = isLegalHoliday;
        this.worker = worker;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean isLegalHoliday() {
        return isLegalHoliday;
    }

    public String getWorker() {
        return worker;
    }
}