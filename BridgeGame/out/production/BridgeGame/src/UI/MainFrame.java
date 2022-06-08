package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    private JPanel mainPanel;
    private JButton startButton;
    private JLabel titleLabel;
    private JLabel backgif;


    public MainFrame(){

        mainPanel=new JPanel();

        ImageIcon buttonIcon=new ImageIcon(getClass().getClassLoader().getResource("startbutton.png"));
        startButton=new JButton(buttonIcon);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);;

        titleLabel=new JLabel("Bridge Game");
        titleLabel.setFont(new Font("Bridge Game", Font.BOLD,50));

        ImageIcon backIcon=new ImageIcon(getClass().getClassLoader().getResource("backgif.gif"));
        backgif=new JLabel(backIcon);
        setContentPane(mainPanel);


        setTitle("Bridge Game");
        setSize(1600,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);  // 정중앙 위치에 배치
        mainPanel.add(titleLabel);
        mainPanel.add(backgif);
        mainPanel.add(startButton);


        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectFrame sf=new SelectFrame();
                sf.setVisible(true);

                dispose();
            }
        });
    }

}
