package Model;

public class Card {
    //각 카드의 장수
    int hammer = 0;
    int saw = 0;
    int philipsDriver = 0;
    int bridge = 0;

    public void addHammer(){
        hammer++;
    }
    public void addSaw(){
        saw++;
    }
    public void addPhilipsDriver(){
        philipsDriver++;
    }
    public void addBridge(){
        bridge++;
    }
    public int getHammer(){
        return hammer;
    }
    public int getSaw(){
        return saw;
    }
    public int getPhilipsDriver(){
        return philipsDriver;
    }
    public int getBridge(){
        return bridge;
    }
    public void useBridgeCard(){
        bridge--;
    }
}
