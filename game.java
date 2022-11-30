import java.util.Random;
public class game{
    
    final static int currentType = 1;
    final static int trapType = 2;
    final static int riverLength = 100;
    final static int playerLength = 2;
    final static String trapDisplay = "#";
    final static String currentDisplay = "C";

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
    
    /*
     * Returns player object of the player whose turn it is
    */
    public player getCurrentPlayer(){
        int currPlayer = 0; //indicates current player(1 or 2) for print statement below
        if(turn % 2 == 0){
            currPlayer = 1;
        }
        return Players[currPlayer];
    }
    
    public int getTurn(){
        return turn;
    }

    public void setTurn(int t) {
        turn = t;
    }

    /*
     * Adjusts chosen player's position 
     * If the input distance moves the player past cell 100 the players position will be set to 100
     */
    public void movePlayer(int distance){
        int currPos = getCurrentPlayer().getPos();
        if(currPos + distance > 100){
            getCurrentPlayer().setPos(100);
        }
        else{
            getCurrentPlayer().setPos(currPos + distance);
        }
    }

    /*
     * Generates random number to be used as dice rolling feature
     */
    public int rollDice(){
        return dice.nextInt(5) + 1;
    }

    public void displayRiver(){
        System.out.printf("\nPlayer 1's position: %d\n",Players[0].getPos());
        System.out.printf("Player 2's position: %d\n",Players[1].getPos());
        System.out.println("====================================================================================================");
        for(int i = 0; i<99;i++){
            if(River[i].getType() == 1){
                System.out.print("C");
            }
            else if(River[i].getType() == 2){
                System.out.print("#");  
            }
            else{
                System.out.print(" ");
            }
        }
        System.out.println("");
        System.out.println("====================================================================================================");
        System.out.printf("\nPlayer %s please press enter to roll the dice.\n",getCurrentPlayer().getName());
    }

    public void displayEndScreen(){
        System.out.println("Thanks for playing!");
        System.out.printf("The winner is %s!!\n",Players[checkWinner()-1].getName());
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
        if(Players[0].getPos() >= 100){
            return 1;
        }
        else if(Players[1].getPos() >= 100){
            return 2;
        }
        else{
            return 0;
        }
    }



}