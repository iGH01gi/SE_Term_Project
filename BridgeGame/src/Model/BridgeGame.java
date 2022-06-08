package Model;

import java.util.ArrayList;

public class BridgeGame {
    ArrayList<Player> playerList=new ArrayList<>();
    Map map=null;
    Dice dice=new Dice();


    //Player명수 설정
    public void choosePlayerNum(int peopleNum){
        for(int i=0;i<peopleNum;i++){
            playerList.add(new Player(i+1));
        }
    }
    //맵 선택
    public void chooseMap(String mapName){
        map=new Map(mapName);
    }

    //map인스턴스 리턴
    public Map getMap(){
        return map;
    }

    //playerList인스턴스 리턴
    public ArrayList<Player> getPlayerList(){return playerList;}
}
