package UI;

import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InfoFrame extends JFrame {
    private JPanel panel1;
    private JPanel player1Panel;
    private JPanel player2Panel;
    private JPanel player3Panel;
    private JPanel player4Panel;
    private JLabel player1Label;
    private JLabel player1ScoreLabel;
    private JLabel player1CardLabel;
    private JLabel player1StatusLabel;
    private JLabel player2Label;
    private JLabel player2ScoreLabel;
    private JLabel player2CardLabel;
    private JLabel player2StatusLabel;
    private JLabel player3Label;
    private JLabel player3ScoreLabel;
    private JLabel player3CardLabel;
    private JLabel player3StatusLabel;
    private JLabel player4Label;
    private JLabel player4ScoreLabel;
    private JLabel player4CardLabel;
    private JLabel player4StatusLabel;
    ArrayList<Player> playerList;
    String getScore(int index){
        String score="총 점수:";
        int rank=playerList.get(index).ranking;
        int total=0;
        if(rank==1)
            total=7;
        else if(rank==2)
            total=3;
        else if(rank==3)
            total=1;
        else
            total=0;

        total+=2*playerList.get(index).cardInventory.getHammer();
        total+=3*playerList.get(index).cardInventory.getSaw();
        total+=1*playerList.get(index).cardInventory.getPhilipsDriver();

        score+=Integer.toString(total);
        return score;
    }

    String getCardList(int index){
        String cardList;
        cardList="        망치:"+playerList.get(index).cardInventory.getHammer()+"개, ";
        cardList+="톱:"+playerList.get(index).cardInventory.getSaw()+"개, ";
        cardList+="필립드라이버:"+playerList.get(index).cardInventory.getPhilipsDriver()+"개 ";
        cardList+="다리카드:"+playerList.get(index).cardInventory.getBridge()+"개        ";
        return cardList;
    }
    InfoFrame(ArrayList<Player> player){
        panel1=new JPanel();

        player1Panel=new JPanel();
        player1Label=new JLabel();
        player1ScoreLabel=new JLabel();
        player1CardLabel=new JLabel();
        player1StatusLabel=new JLabel();
        player1Panel.add(player1Label);
        player1Panel.add(player1ScoreLabel);
        player1Panel.add(player1CardLabel);
        player1Panel.add(player1StatusLabel);
        panel1.add(player1Panel);

        player2Panel=new JPanel();
        player2Label=new JLabel();
        player2ScoreLabel=new JLabel();
        player2CardLabel=new JLabel();
        player2StatusLabel=new JLabel();
        player2Panel.add(player2Label);
        player2Panel.add(player2ScoreLabel);
        player2Panel.add(player2CardLabel);
        player2Panel.add(player2StatusLabel);
        panel1.add(player2Panel);

        player3Panel=new JPanel();
        player3Label=new JLabel();
        player3ScoreLabel=new JLabel();
        player3CardLabel=new JLabel();
        player3StatusLabel=new JLabel();
        player3Panel.add(player3Label);
        player3Panel.add(player3ScoreLabel);
        player3Panel.add(player3CardLabel);
        player3Panel.add(player3StatusLabel);
        panel1.add(player3Panel);

        player4Panel=new JPanel();
        player4Label=new JLabel();
        player4ScoreLabel=new JLabel();
        player4CardLabel=new JLabel();
        player4StatusLabel=new JLabel();
        player4Panel.add(player4Label);
        player4Panel.add(player4ScoreLabel);
        player4Panel.add(player4CardLabel);
        player4Panel.add(player4StatusLabel);
        panel1.add(player4Panel);




        this.setResizable(false);
        this.setLocationRelativeTo(null);  // 정중앙 위치에 배치
        playerList=player;
        this.setLayout(new FlowLayout());
        this.setSize(1000,100*playerList.size());
        //player1 세팅
        player1Label.setText("Player1"); player1Label.setForeground(Color.pink);
        player1ScoreLabel.setText(getScore(0));
        player1CardLabel.setText(getCardList(0));
        String status1="게임중";
        if(playerList.get(0).ranking!=0)
            status1="end지점 탈출";
        player1StatusLabel.setText(status1);
        player1Panel.add(player1Label); player1Panel.add(player1ScoreLabel); player1Panel.add(player1CardLabel); player1Panel.add(player1StatusLabel);
        player1Panel.setPreferredSize(new Dimension(1000,100));
        add(player1Panel);

        //player2 세팅
        player2Label.setText("Player2"); player2Label.setForeground(Color.green);
        player2ScoreLabel.setText(getScore(1));
        player2CardLabel.setText(getCardList(1));
        String status2="게임중";
        if(playerList.get(1).ranking!=0)
            status2="end지점 탈출";
        player2StatusLabel.setText(status2);
        player2Panel.add(player2Label); player2Panel.add(player2ScoreLabel); player2Panel.add(player2CardLabel); player2Panel.add(player2StatusLabel);
        player2Panel.setPreferredSize(new Dimension(1000,100));
        add(player2Panel);

        if(playerList.size()>=3){
            //player3 세팅
            player3Label.setText("Player3"); player3Label.setForeground(Color.blue);
            player3ScoreLabel.setText(getScore(2));
            player3CardLabel.setText(getCardList(2));
            String status3="게임중";
            if(playerList.get(2).ranking!=0)
                status3="end지점 탈출";
            player3StatusLabel.setText(status3);
            player3Panel.add(player3Label); player3Panel.add(player3ScoreLabel); player3Panel.add(player3CardLabel); player3Panel.add(player3StatusLabel);
            player3Panel.setPreferredSize(new Dimension(1000,100));
            add(player3Panel);

            if(playerList.size()==4){
                //player4 세팅
                player4Label.setText("Player4"); player4Label.setForeground(Color.orange);
                player4ScoreLabel.setText(getScore(3));
                player4CardLabel.setText(getCardList(3));
                String status4="게임중";
                if(playerList.get(3).ranking!=0)
                    status4="end지점 탈출";
                player4StatusLabel.setText(status4);
                player4Panel.add(player4Label); player4Panel.add(player4ScoreLabel); player4Panel.add(player4CardLabel); player4Panel.add(player4StatusLabel);
                player4Panel.setPreferredSize(new Dimension(1000,100));
                add(player4Panel);
            }
            this.add(panel1);
        }

        setVisible(true);
    }
}
