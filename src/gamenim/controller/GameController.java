package gamenim.controller;

import gamenim.model.BoardModel;
import gamenim.model.CustomJButton;
import gamenim.view.BoardView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author iqbal
 */
public class GameController {
    private BoardView view;
    private BoardModel model;
    
    public GameController(){
        init();
        iniListener();
    }
    
    //myfunction
   public void init(){
        this.view = new BoardView();
        this.model = new BoardModel();
        model.resetStick();
        view.resetButtonList();
        view.addNewStickButton("|||||||",0);
        //convertIntStickToButtonList();
        view.updateUIStick();
        view.setVisible(true);
   }
   
   public void convertIntStickToButtonList(){
       ArrayList<Integer> temp = model.getStickNim();
       ArrayList<CustomJButton> tempButton = new ArrayList<>();
       for (int i = 0; i < 7 ; i++) {
           if(temp.get(i) > 0){
                String convertedInt = convertIntToStickString(temp.get(i));
                tempButton.add(view.createNewButton(convertedInt, i));
                tempButton.get(i).addActionListener(new StickButtonListener());
           }
       }
       view.resetButtonList();
       view.setStickButton(tempButton);
   }
   
   public String convertIntToStickString(Integer i){
       switch(i){
           case 1:
               return "|";
           case 2:
               return "||";
           case 3:
               return "|||";
           case 4:
               return "||||";
           case 5:
               return "|||||";
           case 6:
               return "|||||||";
           case 7:
               return "|||||||";
           default:
               return Integer.toString(i);
       }
   }
   
   public void iniListener(){
       view.addResetButtonListener(new ResetButonListener());
       view.addStickButtonListener(new StickButtonListener());
       view.addMinMaxButtonListener(new MinMaxButtonListener());
   }
    //end of myfunction
   
   
   //listener
   private class ResetButonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
            model.resetStick();
            view.resetButtonList();
            view.addNewStickButton("|||||||",0);
            //convertIntStickToButtonList();
            view.updateUIStick();
            iniListener();
            view.setCustomLabelText(" ");
        }
   }
   
   private class StickButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ae) {
           if(model.isPlayerMove()){
                Integer i =  Integer.parseInt(JOptionPane.showInputDialog("berapa batang yang ingin diambil"));
                int index = ((CustomJButton)ae.getSource()).getIndex();
                model.makeMove(index, i);
                convertIntStickToButtonList();
                view.updateUIStick();
           }
           
           if(model.getPlayerWin() == -1)
               view.setCustomLabelText("You Lose");
           if(model.getPlayerWin() == 1)
               view.setCustomLabelText("You Win");
        }
   }
   private class MinMaxButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
          if(model.getPlayerWin() == 0){
               model.finBestMove();
               convertIntStickToButtonList();
               view.updateUIStick();
           }
           
           if(model.getPlayerWin() == -1)
               view.setCustomLabelText("You Lose");
           if(model.getPlayerWin() == 1)
               view.setCustomLabelText("You Win");
        }
       
   }
   //end of listener
}
