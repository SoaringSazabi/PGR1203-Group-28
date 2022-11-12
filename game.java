import java.util.Random;
public class game{
    
    final static int currentType = 1;
    final static int trapType = 2;
    final static int riverLength = 100;
    final static int playerLength = 2;
    final static char trapDisplay = "#";
    final static char currentDisplay = "C";

    private int turn;
    private cell[] River = new cell[100];
    private player[] Players = new player[2];
    private Random dice = new Random(); //random object used in rollDice and to generate cell types.

    public game(String pn1, String pn2){
       turn = 0;
       
       player p1 = new player(pn1);
       player p2 = new player(pn2);
       Players[0] = p1;
       Players[1] = p2;

       for(int i = 0; i<100; i++){
        River[i] = new cell();
       }
    }
    
    public int getTurn(){
        return turn;
    }

    public void setTurn(int t) {
        turn = t;
    }

    public int rollDice(){
        return dice.nextInt(5) + 1;
    }

    public void displayRiver(){

    }

    public void displayEndScreen(){

    }

    public player[] getPlayers(){
        return Players;
    }
   
    /*
     * Checks if either player has reached the finish line(position 100)
     * Return:
     * 0 if no players have won yet
     * 1 if player 1 has won
     * 2 if player 2 has won
     */
    public int checkWinner(){
        if(Players[0].getPos() == 100){
            return 1;
        }
        else if(Players[1].getPos() == 100){
            return 2;
        }
        else{
            return 0;
        }
    }



}