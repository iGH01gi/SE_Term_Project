package Model;

import java.io.IOException;
import java.io.InputStream;

public class Map {
    String mapName;
    public int row;//맵의 행의 갯수
    public int col;//맵의 열의 갯수
    public int startRow; //시작점 인덱스는[startRow][0]임.
    String mapText;
    Square[][] squares;


    //.map파일 읽어서 생성할 맵의 가로(row),세로(col)값을 얻어냄
    void calMapSize(String mapName){
        try{
            InputStream in=getClass().getClassLoader().getResource(mapName).openStream();
            byte[] buffer=new byte[128]; //버퍼를 활용해서 파일 텍스트 읽을예정
            int readCount=0;
            StringBuilder result=new StringBuilder();

            while((readCount=in.read(buffer))!=-1){
                String part=new String(buffer,0,readCount);
                result.append(part);
            }
            mapText= result.toString();
            System.out.println(mapName+"이 정상적으로 읽혔습니다.");
        }catch (IOException e){
            System.out.println(mapName+"을 읽는데 실패하였습니다.");
        }

        //읽은 맵 파일(문자열)을 파싱해서 row,col에 적절한 값 넣어줌
        int maxGaro=0;
        int maxSeroUp=0;//시작점으로부터 위로 몇칸인지.
        int maxSeroDown=0; //시작점으로부터 아래로 몇칸인지
        int seroNowPlace=0; //현재 세로위치
        String[] tempStr=mapText.split("\\n");
        for(String s: tempStr){
            if((s.charAt(0))=='E')
                break;

            if((s.charAt(s.length()-2))=='R'){
                maxGaro++;
            }

            if((s.charAt(s.length()-2))=='D'){
                seroNowPlace--;
                if(maxSeroDown>seroNowPlace){
                    maxSeroDown=seroNowPlace;
                }
            }

            if((s.charAt(s.length()-2))=='U'){
                seroNowPlace++;
                if(maxSeroUp<seroNowPlace){
                    maxSeroUp=seroNowPlace;
                }
            }
            System.out.println("정상작동");
        }

        col=maxGaro+1;
        row=maxSeroUp-maxSeroDown+1;
        startRow=maxSeroUp;
        System.out.println("현재 맵의 row: "+row+"  현재 맵의 col:"+col);
        System.out.println("현재 맵의 시작 위치는:["+startRow+"][0]");
    }

    void createSquares(){
        squares=new Square[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                squares[i][j]=new Square();
            }
        }
        int irow=startRow;
        int icol=0;
        String[] tempStr=mapText.split("\\n");
        for(String s: tempStr){
            squares[irow][icol].type=s.substring(0,1);
            if(s.length()>=3)
                squares[irow][icol].move1=s.charAt(2);
            if(s.length()>=5)
                squares[irow][icol].move2=s.charAt(4);

            //다리 셀만 따로 처리
            if(squares[irow][icol].type.equals("B")){
                squares[irow][icol+1].type="bridge";
                squares[irow][icol+1].move1='L';
                squares[irow][icol+1].move2='R';
            }

            if(s.charAt(0)=='E')
                break;
            if(s.charAt(s.length()-2)=='R')
                icol++;
            else if(s.charAt(s.length()-2)=='U')
                irow--;
            else if(s.charAt(s.length()-2)=='D')
                irow++;
        }
    }

    //squares[][]인스턴스 반환
    public Square[][] getSquares(){
        return squares;
    }
    Map(String map){
        mapName=map;
        calMapSize(mapName);
        createSquares();
        System.out.println(mapName+" 맵 생성 완료");
    }
}

