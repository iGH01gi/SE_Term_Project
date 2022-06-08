package Model;

import java.util.ArrayList;

public class Player {
    int playerIndex; //1번 플레이어는 1값이 저장되고 2번은 2...
    public Card cardInventory;
    public Piece piece;

    public int ranking=0;
    Player(int index){
        playerIndex=index;
        cardInventory=new Card();
        piece=new Piece();
        System.out.println("플레이어"+playerIndex+" 생성 완료");
    }
}
