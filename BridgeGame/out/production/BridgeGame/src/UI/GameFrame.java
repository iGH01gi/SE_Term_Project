package UI;

import Model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GameFrame extends JFrame {
    int turn=1; //몇번째 플레이어의 턴 인지
    String[] endPlay=new String[]{"false","false","false","false"};
    boolean rolldice=false; // false이면 해당 플레이어는 아직 주사위를 안굴림. true면 이미 굴린상태

    int ranking=0; //END들어가는 순으로 플레이어에게 부여용 변수

    /******모델 클래스 인스턴스 부분*****/
    BridgeGame bridgeGame;
    ArrayList<Player> playerList;
    Map map;
    Square[][] squares;
    Dice dice=new Dice();
    /******************************/

    GameFrame(BridgeGame bg){
        bridgeGame=bg;
        map= bridgeGame.getMap();
        squares=map.getSquares();
        playerList=bridgeGame.getPlayerList();
        makeList(squares);
        //플레이어들의 piece 위치 시작점으로 옮겨줌
        for(int j=0;j<playerList.size();j++){
            playerList.get(j).piece.setLocation(map.startRow,0);
        }
    }

    /***********************************턴, 주사위, 움직임 입력 부분**************************************/
    JPanel topPanel=new JPanel();//아래 모든걸 담을 패널
    JLabel turnLabel=new JLabel(turn+" PLAYER TURN"); // TURN 표시용 라벨

    ImageIcon diceIcon=new ImageIcon(getClass().getClassLoader().getResource("dice.png"));
    Image diceImg=diceIcon.getImage();
    Image temp=diceImg.getScaledInstance(30,30,Image.SCALE_SMOOTH);
    ImageIcon changeIcon=new ImageIcon(temp);
    JLabel diceImgLabel=new JLabel(changeIcon); //주사위 사진 삽입용 라벨

    int x1,x2,x3,x4,y1,y2,y3,y4;
    JLabel piece1=new JLabel(new ImageIcon(getClass().getClassLoader().getResource("player1.png"))); //1번말
    JLabel piece2=new JLabel(new ImageIcon(getClass().getClassLoader().getResource("player2.png"))); //2번말
    JLabel piece3=new JLabel(new ImageIcon(getClass().getClassLoader().getResource("player3.png"))); //3번말
    JLabel piece4=new JLabel(new ImageIcon(getClass().getClassLoader().getResource("player4.png"))); //4번말

    JButton rollButton=new JButton("ROLL"); //주사위 굴리는 버튼
    JLabel diceValueLabel=new JLabel("주사위를 굴리세요"); //굴려서 나온 주사위 값 버튼
    JTextField inputField=new JTextField(6); // 주사위 값 숫자로 조합해서 입력하는 필드
    JButton moveButton=new JButton("move");//  움직이기용 버튼
    JButton noMoveButton=new JButton("no move");//움직이지 않기 선택 버튼
    JButton infoButton=new JButton("info"); //스코어, 보유중인 카드를 보여주는 버튼

    /***********************************턴, 주사위, 움직임 입력 부분**************************************/



    /***********************************맵 동적으로 생성하는 부분**************************************/
    // 1. 라벨, 박스, 패널 등을 private으로 선언
    private JPanel innerPanel;
    private List<?>[] list;
    private int maxSize;

    // 2. 초기 설정을 생성자 또는 적당한 위치에서 선언
    public void init(List<?>... list)    {
        turnLabel.setForeground(Color.pink);
        this.setLayout(null);
        topPanel.add(turnLabel);
        topPanel.add(diceImgLabel);
        topPanel.add(rollButton);
        topPanel.add(diceValueLabel);
        topPanel.add(inputField);
        topPanel.add(moveButton);
        topPanel.add(noMoveButton);
        topPanel.add(infoButton);
        topPanel.setLayout(new FlowLayout());
        this.add(topPanel);

        maxSize = list[0].size();
        //piece맵 위에 생성
        piece1.setBounds((50 * maxSize+400)/2-(50 * maxSize)/2+10,40+15+map.startRow*50,50,50);
        piece2.setBounds((50 * maxSize+400)/2-(50 * maxSize)/2+10,40+15+map.startRow*50,50,50);
        piece3.setBounds((50 * maxSize+400)/2-(50 * maxSize)/2+10,40+15+map.startRow*50,50,50);
        piece4.setBounds((50 * maxSize+400)/2-(50 * maxSize)/2+10,40+15+map.startRow*50,50,50);
        x1=(50 * maxSize+400)/2-(50 * maxSize)/2+10;x2=(50 * maxSize+400)/2-(50 * maxSize)/2+10;x3=(50 * maxSize+400)/2-(50 * maxSize)/2+10;x4=(50 * maxSize+400)/2-(50 * maxSize)/2+10;
        y1=40+15+map.startRow*50;y2=40+15+map.startRow*50;y3=40+15+map.startRow*50;y4=40+15+map.startRow*50;
        if(playerList.size()==2){
            this.add(piece1);
            this.add(piece2);
        }else if(playerList.size()==3){
            this.add(piece1);
            this.add(piece2);
            this.add(piece3);
        }else if(playerList.size()==4){
            this.add(piece1);
            this.add(piece2);
            this.add(piece3);
            this.add(piece4);
        }

        // 1칸당 50px
        System.out.println(list.length);
        for(List<?> i : list) {
            if(i.size() > maxSize)  maxSize = i.size();
        }
        this.setSize(50 * maxSize+400, 40 + 50 * list.length+60);
        topPanel.setPreferredSize(new Dimension(50*maxSize+400,40));
        topPanel.setBounds(0,0,50*maxSize+400,40);
        this.setResizable(false);
        this.setLocationRelativeTo(null);  // 정중앙 위치에 배치
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(list.length, 3));// (행, 열): 행의 수가 중요
        innerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setBounds((50 * maxSize+400)/2-(50 * maxSize)/2,40,50 * maxSize,40 + 50 * list.length); //왼쪽위 꼭짓점x좌표: (전체창/2)-(맵가로/2)
        this.add(innerPanel);
        this.list = list;
        this.initComp();


        this.setVisible(true);  // setVisible을 이곳에서 실행

        playGame();
    }

    public void makeList(Square[][] sq) {
        List<?>[] tempArr = new List<?>[sq.length];
        for(int i = 0; i < tempArr.length; i++) {
            tempArr[i] = Arrays.stream(sq[i]).collect( Collectors.toList() );
        }
        init(tempArr);
    }

    // 3. 실제 요소들을 배치하는 메소드를 작성
    public void initComp() {
        for(int i = 0; i < list.length; i++) {
            List<?> tempList = (List<?>) list[i];
            /* 요소들을 for문으로 배치하기 */
            for(int j = 0; j < maxSize; j++) {
                try {
                    JLabel lbl=null;
                    // 0인 경우엔 공백 처리. 아니면 색칠
                    Square temp=(Square)tempList.get(j);
                    if(temp.type=="blank") {
                        //빈칸이면 배경색 흰색으로
                        lbl = new JLabel("");
                        lbl.setOpaque(true);  // 이 코드가 있어야 레이블에 색 서식 적용됨
                        lbl.setBackground(Color.WHITE);

                    } else {
                        //빈칸이 아니라면 해당 셀 타입에 맞는 이미지 넣기;
                        if(temp.type.equals("S")&&temp.move2=='x')
                        {
                            ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("startCell.png"));
                            Image img=icon.getImage();
                            img=img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
                            ImageIcon change=new ImageIcon(img);
                            lbl = new JLabel(change);
                            lbl.setOpaque(true);  // 이 코드가 있어야 레이블에 색 서식 적용됨
                            lbl.setBackground(Color.BLACK);
                        }
                        else if(temp.type.equals("E")){//S,E,C,B,b,H,S,P,bridge,blank
                            ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("endCell.png"));
                            Image img=icon.getImage();
                            img=img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
                            ImageIcon change=new ImageIcon(img);
                            lbl = new JLabel(change);
                            lbl.setOpaque(true);  // 이 코드가 있어야 레이블에 색 서식 적용됨
                            lbl.setBackground(Color.BLACK);
                        }
                        else if(temp.type.equals("C")||temp.type.equals("b")){
                            ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("cell.png"));
                            Image img=icon.getImage();
                            img=img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
                            ImageIcon change=new ImageIcon(img);
                            lbl = new JLabel(change);
                            lbl.setOpaque(true);  // 이 코드가 있어야 레이블에 색 서식 적용됨
                            lbl.setBackground(Color.BLACK);
                        }
                        else if(temp.type.equals("B")){
                            ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("bridgeStartCell.png"));
                            Image img=icon.getImage();
                            img=img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
                            ImageIcon change=new ImageIcon(img);
                            lbl = new JLabel(change);
                            lbl.setOpaque(true);  // 이 코드가 있어야 레이블에 색 서식 적용됨
                            lbl.setBackground(Color.BLACK);
                        }
                        else if(temp.type.equals("H")){
                            ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("hammerCell.png"));
                            Image img=icon.getImage();
                            img=img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
                            ImageIcon change=new ImageIcon(img);
                            lbl = new JLabel(change);
                            lbl.setOpaque(true);  // 이 코드가 있어야 레이블에 색 서식 적용됨
                            lbl.setBackground(Color.BLACK);
                        }
                        else if(temp.type.equals("S")){
                            ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("sawCell.png"));
                            Image img=icon.getImage();
                            img=img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
                            ImageIcon change=new ImageIcon(img);
                            lbl = new JLabel(change);
                            lbl.setOpaque(true);  // 이 코드가 있어야 레이블에 색 서식 적용됨
                            lbl.setBackground(Color.BLACK);
                        }
                        else if(temp.type.equals("P")){
                            ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("philipsDriverCell.png"));
                            Image img=icon.getImage();
                            img=img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
                            ImageIcon change=new ImageIcon(img);
                            lbl = new JLabel(change);
                            lbl.setOpaque(true);  // 이 코드가 있어야 레이블에 색 서식 적용됨
                            lbl.setBackground(Color.BLACK);
                        }
                        else if(temp.type.equals("bridge")){
                            ImageIcon icon=new ImageIcon(getClass().getClassLoader().getResource("bridgeCell.png"));
                            Image img=icon.getImage();
                            img=img.getScaledInstance(50,50,Image.SCALE_SMOOTH);
                            ImageIcon change=new ImageIcon(img);
                            lbl = new JLabel(change);
                            lbl.setOpaque(true);  // 이 코드가 있어야 레이블에 색 서식 적용됨
                            lbl.setBackground(Color.BLACK);
                        }
                    }
                    // 가운데 정렬
                    lbl.setHorizontalAlignment(JLabel.CENTER);
                    innerPanel.add(lbl);
                } catch(Exception e) {
                     //이 메소드는 다차원 배열의 각 행의
                     //길이가 뒤죽박죽일 때 예외가 나는 점을 이용해
                     //예외가 났다면 빈 공간으로 간주하고 빈칸을 그리는 부분
                    JLabel lbl=new JLabel(" ");
                    lbl.setOpaque(true);
                    lbl.setBackground(Color.WHITE);
                    innerPanel.add(lbl);
                    continue;
                }
            }
        }
    }
    /***********************************맵 동적으로로 생성는 부분**************************************/

    boolean checkPath(String input,Piece piece){//입력받은 input문자열이 piece의 현재 위치로부터 이동가능한 경로인지 체크
        int pRow=piece.getRow();
        int pCol= piece.getCol();
        for(int i=0;i<input.length();i++){
            if(input.charAt(i)=='U'||input.charAt(i)=='u'){
                if(pRow<0 || pRow>=map.row || pCol<0 || pCol>=map.col){//범위 넘어가면 안됨
                    return false;
                }
                else if(squares[pRow][pCol].type.equals("E")){
                    System.out.println(turn+"번말 END도착");
                    endPlay[turn-1]="true";
                    return true;
                }
                else if(squares[pRow][pCol].move1=='U'||squares[pRow][pCol].move1=='u'||squares[pRow][pCol].move2=='U'||squares[pRow][pCol].move2=='u'){
                    pRow--;
                }
                else {
                    return false;
                }
            }
            if(input.charAt(i)=='D'||input.charAt(i)=='d'){
                if(pRow<0 || pRow>=map.row || pCol<0 || pCol>=map.col){//범위 넘어가면 안됨
                    System.out.println("범위 벗어남");
                    return false;
                }
                else if(squares[pRow][pCol].type.equals("E")){
                    System.out.println(turn+"번말 END도착");
                    endPlay[turn-1]="true";
                    return true;
                }
                else if(squares[pRow][pCol].move1=='D'||squares[pRow][pCol].move1=='d'||squares[pRow][pCol].move2=='D'||squares[pRow][pCol].move2=='d'){
                    pRow++;
                }
                else {
                    return false;
                }
            }
            if(input.charAt(i)=='L'||input.charAt(i)=='l'){
                if(pRow<0 || pRow>=map.row || pCol<0 || pCol>=map.col){//범위 넘어가면 안됨
                    return false;
                }
                else if(squares[pRow][pCol].type.equals("bridge")){
                    System.out.println("다리->다리왼쪽으로");
                    pCol--;
                }
                else if(squares[pRow][pCol].type.equals("b")){
                    System.out.println("다리 오른쪽->다리");
                    pCol--;
                }
                else if(squares[pRow][pCol].type.equals("E")){
                    System.out.println(turn+"번말 END도착");
                    endPlay[turn-1]="true";
                    return true;
                }
                else if(squares[pRow][pCol].move1=='L'||squares[pRow][pCol].move1=='l'||squares[pRow][pCol].move2=='L'||squares[pRow][pCol].move2=='l'){
                    pCol--;
                }
                else {
                    return false;
                }
            }
            if(input.charAt(i)=='R'||input.charAt(i)=='r'){

                if(pRow<0 || pRow>=map.row || pCol<0 || pCol>=map.col){//범위 넘어가면 안됨
                    return false;
                }
                else if(squares[pRow][pCol].type.equals("B")){
                    System.out.println("다리 왼쪽->다리");
                    pCol++;
                }
                else if (squares[pRow][pCol].type.equals("bridge")){
                    System.out.println("다리->오른쪽으로");
                    pCol++;
                }
                else if(squares[pRow][pCol].type.equals("E")){
                    System.out.println(turn+"번말 END도착");
                    endPlay[turn-1]="true";
                    return true;
                }
                else if(squares[pRow][pCol].move1=='R'||squares[pRow][pCol].move1=='r'||squares[pRow][pCol].move2=='R'||squares[pRow][pCol].move2=='r'){
                    pCol++;
                }
                else {
                    return false;
                }
            }
            if(squares[pRow][pCol].type.equals("E")){
                System.out.println(turn+"번말 END도착");
                ranking++;
                playerList.get(turn-1).ranking=ranking;
                endPlay[turn-1]="true";
                System.out.println("endplay"+(turn-1)+"의 불린은="+endPlay[turn-1]);
                System.out.println("Collections.frequency(Arrays.asList(endPlay),true)값은="+Collections.frequency(Arrays.asList(endPlay),"true"));
                //만약 플레이어-1명이 end상태라면 게임 종료
                if(Collections.frequency(Arrays.asList(endPlay),"true")==playerList.size()-1){
                    //info창 띄우고 현재 프레임은 꺼버리기*****************************************************************************
                    //마지막 남은 말 꼴등처리
                    ranking++;
                    playerList.get(Arrays.asList(endPlay).indexOf("false")).ranking=ranking;
                    InfoFrame infr=new InfoFrame(playerList);
                    infr.setVisible(true);
                    infr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    dispose();
                }
                return true;
            }
        }
        return true;
    }

    void bridgeCardGetCheck(Piece piece){
        Card cardInventory=playerList.get(turn-1).cardInventory;
        if(piece.squareStack.size()>=3){
            if(piece.squareStack.elementAt(piece.squareStack.size()-1).equals("B") && piece.squareStack.elementAt(piece.squareStack.size()-2).equals("bridge") && piece.squareStack.elementAt(piece.squareStack.size()-3).equals("b")){
                cardInventory.addBridge();
            }else if(piece.squareStack.elementAt(piece.squareStack.size()-1).equals("b") && piece.squareStack.elementAt(piece.squareStack.size()-2).equals("bridge") && piece.squareStack.elementAt(piece.squareStack.size()-3).equals("B")){
                cardInventory.addBridge();
            }
        }
    }
    void movePiece(String input,Piece piece){
        for(int i=0;i<input.length();i++){
            if(input.charAt(i)=='U'||input.charAt(i)=='u'){
                piece.move(-1,0);
                piece.squareStack.push(squares[piece.getRow()][piece.getCol()].type);
                bridgeCardGetCheck(piece);
                if(turn==1) {piece1.setBounds(x1,y1-50,50,50); y1-=50; }
                else if(turn==2)  {piece2.setBounds(x2,y2-50,50,50); y2-=50; }
                else if(turn==3)  {piece3.setBounds(x3,y3-50,50,50); y3-=50; }
                else if(turn==4)  {piece4.setBounds(x4,y4-50,50,50); y4-=50; }
            }else if(input.charAt(i)=='D'||input.charAt(i)=='d'){
                piece.move(1,0);
                piece.squareStack.push(squares[piece.getRow()][piece.getCol()].type);
                bridgeCardGetCheck(piece);
                if(turn==1) {piece1.setBounds(x1,y1+51,50,50); y1+=51; }
                else if(turn==2)  {piece2.setBounds(x2,y2+51,50,50); y2+=51; }
                else if(turn==3)  {piece3.setBounds(x3,y3+51,50,50); y3+=51; }
                else if(turn==4)  {piece4.setBounds(x4,y4+51,50,50); y4+=51; }
            }else if(input.charAt(i)=='L'||input.charAt(i)=='l'){
                piece.move(0,-1);
                piece.squareStack.push(squares[piece.getRow()][piece.getCol()].type);
                bridgeCardGetCheck(piece);
                if(turn==1) {piece1.setBounds(x1-48,y1,50,50); x1-=48; }
                else if(turn==2)  {piece2.setBounds(x2-48,y2,50,50); x2-=48; }
                else if(turn==3)  {piece3.setBounds(x3-48,y3,50,50); x3-=48; }
                else if(turn==4)  {piece4.setBounds(x4-48,y4,50,50); x4-=48; }
            }else if(input.charAt(i)=='R'||input.charAt(i)=='r'){
                piece.move(0,1);
                piece.squareStack.push(squares[piece.getRow()][piece.getCol()].type);
                bridgeCardGetCheck(piece);
                if(turn==1) {piece1.setBounds(x1+48,y1,50,50); x1+=48; }
                else if(turn==2)  {piece2.setBounds(x2+48,y2,50,50); x2+=48; }
                else if(turn==3)  {piece3.setBounds(x3+48,y3,50,50); x3+=48; }
                else if(turn==4)  {piece4.setBounds(x4+48,y4,50,50); x4+=48; }
            }
        }
    }

    void getCard(Card cardInventory,Piece piece){//말이 다 움직인 후 최종 위치의 카드를 얻음(움직이지 않을시에는 얻지 않음). 다리카드의 경우 움직임 처리할때 같이 처리(스택 이용)
        if(squares[piece.getRow()][piece.getCol()].type.equals("H"))
                cardInventory.addHammer();
        else if (squares[piece.getRow()][piece.getCol()].type.equals("S"))
                cardInventory.addSaw();
        else if(squares[piece.getRow()][piece.getCol()].type.equals("P"))
                cardInventory.addPhilipsDriver();
    }


    void nextTurn(){
        do{
            turn++;
            if(turn>playerList.size()){//인원수보다 turn이 커지면 1로 돌아감
                turn=1;
            }
        }while(endPlay[turn-1]=="true");


        rolldice=false;
        diceValueLabel.setText("주사위를 굴리세요");
        turnLabel.setText(turn+" PLAYER TURN");
        if(turn==1)
            turnLabel.setForeground(Color.pink);
        else if(turn==2)
            turnLabel.setForeground(Color.green);
        else if(turn==3)
            turnLabel.setForeground(Color.blue);
        else if(turn==4)
            turnLabel.setForeground(Color.orange);
        inputField.setText("");
    }

    void playGame(){
        //주사위 굴리기 버튼
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rolldice==false){
                    diceValueLabel.setText(Integer.toString(dice.roll()));
                    rolldice=true;
                }
                else if(rolldice==true){ //이미 굴렸을때 에러메시지
                    JOptionPane.showMessageDialog(null, "이미 주사위를 굴렸습니다!", "Message",JOptionPane.ERROR_MESSAGE );
                }
            }
        });


        //움직이기 버튼
        moveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rolldice==false){
                    JOptionPane.showMessageDialog(null, "주사위를 먼저 굴려 주세요", "Message",JOptionPane.ERROR_MESSAGE );
                }
                else if(inputField.getText().length()!=dice.value){
                    JOptionPane.showMessageDialog(null, "주사위 결과에 맞는 길이를 입력해주세요", "Message",JOptionPane.ERROR_MESSAGE );
                }
                else{//U,D,L,R, u,d,l,r이외에 다른 문자 입력됐는지 먼저 체크
                    String input=inputField.getText();
                    for(int i=0;i<input.length();i++){
                        if(input.charAt(i)!='U'&&input.charAt(i)!='D'&&input.charAt(i)!='L'&&input.charAt(i)!='R'&&input.charAt(i)!='u'&&input.charAt(i)!='d'&&input.charAt(i)!='l'&&input.charAt(i)!='r'){
                            JOptionPane.showMessageDialog(null, "U,D,L,R, u,d,l,r이외에 다른 문자가 입력되었습니다", "Message",JOptionPane.ERROR_MESSAGE );
                            break;
                        }
                        if(i==input.length()-1){
                            //입력은 정상, 이제 해당 움직임이 정상적인 루트인지 체크해야함
                            if(checkPath(input,playerList.get(turn-1).piece)==true){//입력받은 경로가 이동가능하므로 이동. 턴 넘어가고 다음 플레이어를 위한 초기화 작업 진행됨

                                if(endPlay[turn-1]=="true"){//end지점에 도달했으니 맵 위에 있는 말 없애주고 턴 안오게 함.
                                    if(turn==1)
                                    {piece1.setVisible(false);remove(piece1);}
                                    else if(turn==2)
                                    {piece2.setVisible(false);remove(piece2);}
                                    else if(turn==3)
                                    {piece3.setVisible(false);remove(piece3);}
                                    else if(turn==4)
                                    {piece4.setVisible(false);remove(piece4);}
                                    nextTurn();
                                }
                                else{
                                    movePiece(input,playerList.get(turn-1).piece);
                                    getCard(playerList.get(turn-1).cardInventory,playerList.get(turn-1).piece);
                                    System.out.println(turn+"번 플레이어의 카드갯수(망치,톱,드라이버,다리 순서)"+playerList.get(turn-1).cardInventory.getHammer()+" "+playerList.get(turn-1).cardInventory.getSaw()+" "+playerList.get(turn-1).cardInventory.getPhilipsDriver()+" "+playerList.get(turn-1).cardInventory.getBridge());
                                    nextTurn();
                                }
                            }
                            else{//이동 불가능한 경로이므로 다시 입력하라 해야함
                                JOptionPane.showMessageDialog(null, "이동 불가능한 움직임입니다. 다시 입력해주세요", "Message",JOptionPane.ERROR_MESSAGE );
                                System.out.println("이동불가능");
                                inputField.setText("");
                            }
                        }
                    }
                }
            }
        });

        noMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {//주사위 초기화해줘야함
                if(playerList.get(turn-1).cardInventory.getBridge()>0)
                {
                    playerList.get(turn-1).cardInventory.useBridgeCard();
                    System.out.println(turn+"번 플레이어의 카드갯수(망치,톱,드라이버,다리 순서)"+playerList.get(turn-1).cardInventory.getHammer()+" "+playerList.get(turn-1).cardInventory.getSaw()+" "+playerList.get(turn-1).cardInventory.getPhilipsDriver()+" "+playerList.get(turn-1).cardInventory.getBridge());
                    turn++;
                    if(turn>playerList.size()){//인원수보다 turn이 커지면 1로 돌아감
                        turn=1;
                    }
                    rolldice=false;
                    diceValueLabel.setText("주사위를 굴리세요");
                    turnLabel.setText(turn+" PLAYER TURN");
                    inputField.setText("");
                }
                else{
                    JOptionPane.showMessageDialog(null, "bridge카드가 있어야지 no move를 선택할 수 있습니다.", "Message",JOptionPane.ERROR_MESSAGE );
                    System.out.println("bridge카드 없는데 no move누름");
                    inputField.setText("");
                }
            }
        });

        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoFrame infr=new InfoFrame(playerList);
            }
        });
    }


}
