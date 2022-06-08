package Model;

import java.util.Random;

public class Dice {
    public int value; //1~6사이의 값 저장

    public int roll(){
        Random rnd=new Random();
        value=rnd.nextInt(6)+1;
        return value;
    }
}
