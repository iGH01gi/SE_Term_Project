package Model;

import java.util.Stack;

public class Piece {
    int row;
    int col;

    public Stack<String> squareStack=new Stack<>(); //다리건너기 확인용 스택 (다 안건너고 왔다갔다 하는거 방지)

    Piece(){
        row=0;
        col=0;
    }

    public void setLocation(int a, int b){
        row=a;
        col=b;
    }

    public void move(int a, int b){
        row+=a;
        col+=b;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }
}
