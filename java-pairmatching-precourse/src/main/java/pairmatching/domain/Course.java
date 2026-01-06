package pairmatching.domain;

public enum Course {
    BACKEND("백엔드"),
    FRONTEND("프론트엔드");

    private String name;

    Course(String name) {
        this.name = name;
    }

    public static Course fromString(String courseStr) {
        if (courseStr.equals("백엔드")) {
            return BACKEND;
        }
        if (courseStr.equals("프론트엔드")) {
            return FRONTEND;
        }
        throw new IllegalArgumentException("잘못된 과정입니다.");
    }
}
