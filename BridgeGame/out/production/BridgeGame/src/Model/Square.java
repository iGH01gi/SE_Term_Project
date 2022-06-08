package Model;

public class Square {
    public String type="blank"; //<=== S,E,C,B,b,H,S,P,bridge,blank   이렇게 총 9개 올 수 있음.
                //blank는 아무 셀 종류도 아닌 그냥 빈 칸이라는 뜻. 즉 이동 불가능
    public char move1='x';// U,D,L,R 또는 u,d,l,r 또는 x올 수 있음
    public char move2='x';// U,D,L,R 또는 u,d,l,r 또는 x올 수 있음
}
