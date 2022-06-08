package UI;

import Model.BridgeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class SelectFrame extends JFrame {
    /******모델 클래스 인스턴스 부분*****/
    BridgeGame bridgeGame=new BridgeGame();
    /******************************/
    private JPanel mainPanel;
    private JComboBox comboBox1;
    private JLabel playercountLabel;
    private JLabel charimgLabel;
    private JLabel mapimgLabel;
    private JLabel mapselectLabel;
    private JComboBox comboBox2;
    private JButton okButton;
    private JPanel topPanel;
    private JPanel midPanel;
    private JPanel botPanel;

    public SelectFrame(){
        mainPanel=new JPanel();
        mainPanel.setBackground(new Color(204,153,255));

        ImageIcon charIcon=new ImageIcon(getClass().getClassLoader().getResource("playercount.png"));
        topPanel=new JPanel();
        topPanel.setBackground(new Color(0,0,0,0));
        charimgLabel=new JLabel(charIcon);
        playercountLabel=new JLabel("플레이어 수:");
        comboBox1=new JComboBox();
        topPanel.add(charimgLabel);
        topPanel.add(charimgLabel);
        topPanel.add(playercountLabel);
        topPanel.add(comboBox1);
        topPanel.setPreferredSize(new Dimension(1600,330));
        mainPanel.add(topPanel);

        ImageIcon mapIcon=new ImageIcon(getClass().getClassLoader().getResource("mapselect.png"));
        midPanel=new JPanel();
        midPanel.setBackground(new Color(0,0,0,0));
        mapimgLabel=new JLabel(mapIcon);
        mapselectLabel=new JLabel("맵을 골라 주세요:");
        comboBox2=new JComboBox();
        midPanel.add(mapimgLabel);
        midPanel.add(mapselectLabel);
        midPanel.add(comboBox2);
        midPanel.setPreferredSize(new Dimension(1600,330));
        mainPanel.add(midPanel);

        ImageIcon okIcon=new ImageIcon(getClass().getClassLoader().getResource("okButton.png"));
        botPanel=new JPanel();
        botPanel.setBackground(new Color(0,0,0,0));
        okButton=new JButton(okIcon);
        okButton.setContentAreaFilled(false);
        okButton.setBorderPainted(false);;
        botPanel.add(okButton);
        botPanel.setPreferredSize(new Dimension(1600,330));
        mainPanel.add(botPanel);

        setContentPane(mainPanel);
        playercountLabel.setFont(new Font("", Font.BOLD,50));
        mapselectLabel.setFont(new Font("", Font.BOLD,50));

        /***플레이어수 선택 목록 초기화(2~4)**/
        for(int i=2;i<=4;i++){
            comboBox1.addItem(i);
        }
        /*******************************/

        /**맵 목록 초기화**/
            String MAP_DIRECTORY="./map/";
            File dir=new File(MAP_DIRECTORY);
            String[] filenames=dir.list();
            for(String filename:filenames){
                comboBox2.addItem(filename);
            }
        /***************/

        setSize(1600,1100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);  // 정중앙 위치에 배치
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //플레이어 생성
                int peopleNum=Integer.parseInt(comboBox1.getSelectedItem().toString());
                bridgeGame.choosePlayerNum(peopleNum);

                //맵 생성
                String mapName=comboBox2.getSelectedItem().toString();
                bridgeGame.chooseMap(mapName);

                //게임창으로 이동
                GameFrame gf=new GameFrame(bridgeGame);
                gf.setVisible(true);
                dispose();
            }
        });
    }
}
