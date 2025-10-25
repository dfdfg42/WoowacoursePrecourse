package racingcar;

import camp.nextstep.edu.missionutils.Randoms;

public class Car {


    public static final int MOVE_THRESHOLD = 4;

    private final String name;
    private int position;

    public Car(String name) {
        this.name = name;
        this.position = 0;
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
