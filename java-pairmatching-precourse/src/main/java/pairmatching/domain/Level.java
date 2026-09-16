package pairmatching.domain;

public enum Level {
    LEVEL1("레벨1"),
    LEVEL2("레벨2"),
    LEVEL3("레벨3"),
    LEVEL4("레벨4"),
    LEVEL5("레벨5");

    private String name;

    Level(String name) {
        this.name = name;
    }

    public static Level fromString(String levelStr) {
        if (levelStr.equals("레벨1")) {
            return LEVEL1;
        }
        if (levelStr.equals("레벨2")) {
            return LEVEL2;
        }
        if (levelStr.equals("레벨3")) {
            return LEVEL3;
        }
        if (levelStr.equals("레벨4")) {
            return LEVEL4;
        }
        if (levelStr.equals("레벨5")) {
            return LEVEL5;
        }
        throw new IllegalArgumentException("잘못된 레벨입니다.");
    }
}