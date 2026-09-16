package racingcar;

import camp.nextstep.edu.missionutils.Randoms;

public class Car {


    public static final int MOVE_THRESHOLD = 4;
    private static final int NAME_MAX_LENGTH = 5;

    private final String name;
    private int position;

    public Car(String name) {
        validateNameLength(name);
        this.name = name;
        this.position = 0;
    }

    public void validateNameLength(String name){
        if(name.length() > NAME_MAX_LENGTH){
            throw new IllegalArgumentException("자동차 이름은 5글자 이하만 가능합니다.");
        }
    }

    public String getName() {
        return this.name;
    }

    public int getPosition() {
        return this.position;
    }

    public void move(){
        int randomNumber = Randoms.pickNumberInRange(0,9);

        if(randomNumber >= MOVE_THRESHOLD){
            this.position++;
        }
    }
}
