package gamenim.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 *
 * @author iqbal
 */
public class BoardModel {
    private boolean playerMove = true;
    private int playerWin = 0;
    private ArrayList<Integer> stickNim = new ArrayList<>();
    private int depth = 0;
    

    //setter & getter
    public void setStickNim(ArrayList<Integer> stickNim) { this.stickNim = stickNim; }
    public void setPlayerMove(boolean playerMove) {this.playerMove = playerMove;}
    public void setPlayerWin(int playerWin) {this.playerWin = playerWin;}
    public ArrayList<Integer> getStickNim() {return stickNim;}
    public int getPlayerWin() {return playerWin;}
    public boolean isPlayerMove(){  return playerMove;}
    //end of setter & getter
    
    
    //myfucntion
    public void startNewGame() {
        resetStick();
    }
    
    public void incrementDepth(){
        depth++;
    }
    
    public void resetStick(){
        stickNim = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            stickNim.add(i, 0);
        }
        stickNim.add(0, 7);
        playerMove = true;
        playerWin = 0;
        depth = 0;
        
    }
    //end of myfunction
    
    //player function
    public void makeMove(int index, int stick){
        if(playerMove && stickNim.get(index) != 1){
            if(stick < stickNim.get(index)){
                stickNim.set(index, stickNim.get(index)-stick);
                stickNim.add(index+1, stick);
                incrementDepth();
                performWinCheck(index);
                playerMove = false;
            }
        }
        
        System.out.println(stickNim);
    }
    
    public void performWinCheck(int index){
        if(stickNim.get(index) == stickNim.get(index+1) && stickNim.get(index)!=0){
            if(playerMove)
                playerWin = -1;
            else
                playerWin = 1;
        }
    }
    //end of player function
    
    //ai function
    public boolean isMoveLeft(){ 
        for (int i = 0; i < stickNim.size(); i++) {
            if (stickNim.get(i) > 1) {
                return true;
            }
        }
        return false;
    }
    
    public int evaluate(ArrayList<Integer> list){
        int angka2 = 0;
        int angka1 = 0;
        
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == 1) {
                angka1++;
            } 
            if (list.get(i) == 2) {
                angka2++;
            }   
        }
        
        if(angka2 == 2 && angka1 == 3)
            return 1;
        
        if(angka2 == 1 && angka1 == 5)
            return -1;
        
        return 0;
        
    }
    public int minimax(ArrayList<Integer> list, int depth ,boolean isMax ){
        int score = evaluate(list);
        if(score == 1 || score == -1)
            return score;
        
        if(isMoveLeft() == false)
            return 0;
        
        if(isMax){
            int best = -999;
            for (int i = 0; i < 7; i++) {
                if(list.get(i) > 1){
                    for (int j = 1; j < list.get(i); j++) {
                        ArrayList<Integer> temp = new ArrayList<>(list);
                        list.set(i, list.get(i)-j);
                        list.add(i+1,j);
                        best = Math.max(best, minimax(list, depth + 1, !isMax ));
                        list = new ArrayList<>(temp);
                    }
                }
            }
            return best;
        }else{
            int best = 999;
            for (int i = 0; i < 7; i++) {
                if(list.get(i) > 1){
                    for (int j = 1; j < list.get(i); j++) {
                        ArrayList<Integer> temp = new ArrayList<>(list);
                        list.set(i, list.get(i)-j);
                        list.add(i+1,j);
                        best = Math.min(best, minimax(list, depth + 1, !isMax ));
                        list = new ArrayList<>(temp);
                    }
                }
            }
            return best;
        }
    }
    public void finBestMove(){
        if(playerWin == 0){
            int bestVal = -1000;
            int index = 0;
            int stick = 0;
            for (int i = 0; i < 7; i++) {
                if(stickNim.get(i) > 1){
                    for (int j = 1; j < stickNim.get(i); j++) {
                        ArrayList<Integer> temp = new ArrayList<>(stickNim);
                        stickNim.set(i, stickNim.get(i)-j);
                        stickNim.add(i+1, j);
                        int moveVal = minimax(stickNim, depth, false);
                        stickNim = new ArrayList<>(temp);
                        System.out.println(moveVal);
                        if(moveVal >= bestVal){
                            
                            System.out.println("gamenim.model.BoardModel.finBestMove()"+i);
                            System.out.println("gamenim.model.BoardModel.finBestMove()"+j);
                            index = i;
                            stick = j;
                            bestVal = moveVal;
                        }
                    }
                }
            }

            stickNim.set(index, stickNim.get(index)-stick);
            stickNim.add(index+1, stick);
            performWinCheck(index);
            playerMove  = true;
        }
        System.out.println(stickNim);
    }
    //end of ai function
}
