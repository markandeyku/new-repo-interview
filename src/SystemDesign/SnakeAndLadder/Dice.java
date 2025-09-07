package SystemDesign.SnakeAndLadder;

import java.util.Random;

public class Dice {

    int size;


    Dice(int size){
        this.size = size;
    }


    public int rollDice() {
        return new Random().nextInt(6) + size;
    }
}
