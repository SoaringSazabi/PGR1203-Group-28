import java.util.Random; 
import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class game{
    
    final static int currentType = 1;
    final static int trapType = 2;
    final static int riverLength = 100;
    final static int playerLength = 2;
    final static String trapDisplay = "T";
    final static String currentDisplay = "C";
    final static int maxCurrent = 10;
    final static int maxTrap = 10;

    private Scanner input = new Scanner(System.in);
    private int turn;
    private cell[] River = new cell[100];
    private player[] Players = new player[2];
    private Random random = new Random(); //random object used in rollDice and to generate cell types.
    private File file = new File("highScores.txt");

    public game(String pn1, String pn2){
       turn = 1;
       player p1 = new player(pn1);
       player p2 = new player(pn2);
       Players[0] = p1;
       Players[1] = p2;

       River = createRiver();
    }
    
    public int getTurn(){
        return turn;
    }

    public void nextTurn() {
        turn += 1;
    }

    public player[] getPlayers(){
        return Players;
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

    /*
     * Used to create cell list that will act as the river 
     * Number of currents and traps are determined by random fucntion with a max of 15 and minimum of 5 for each
     * Uses for loops to create blank,current and trap cells
     */
    public cell[] createRiver(){
        int C = random.nextInt(maxCurrent)+5;
        int T = random.nextInt(maxTrap)+5;

        //cell[] to be returned later
        cell[] tempRiver = new cell[riverLength];


        //Initialises entire river with blank cells
        for(int l = 0; l < riverLength; l++){
            tempRiver[l] = new cell(0, 0);
        }

        //Sets all current cells
        for(int i = 0; i < C; i++){
            tempRiver[i].setType(currentType);
            tempRiver[i].setEffect(random.nextInt(9)+1);
        }

        //Sets all trap cells
        for(int j = C; j-C < T; j++){
            tempRiver[j].setType(trapType);
            tempRiver[j].setEffect(random.nextInt(9)+1);
        }
        
        Collections.shuffle(Arrays.asList(tempRiver));//Shuffle all cells

        return tempRiver;
    }

    /*
     * Adjusts chosen player's position 
     * If the input distance moves the player past cell 100 the players position will be set to 100
     */
    public void movePlayer(int distance){
        int currPos = getCurrentPlayer().getPos();
        if(currPos + distance > 99){
            getCurrentPlayer().setPos(99);
        }
        else if(currPos + distance < 0){
            getCurrentPlayer().setPos(0);
        }
        else{
            getCurrentPlayer().setPos(currPos + distance);
        }
    }

    /*
     * Checks the cell player has landed on and adjusts their position if 
     */
    public void checkCell(){
        if(River[getCurrentPlayer().getPos()].getType() == currentType){
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
            System.out.printf("\nPlayer %s has landed on a Current, you have been moved %d spaces forward\n",getCurrentPlayer().getName(),River[getCurrentPlayer().getPos()].getEffect());
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
            movePlayer(River[getCurrentPlayer().getPos()].getEffect());
        }
        else if(River[getCurrentPlayer().getPos()].getType() == trapType){
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            System.out.printf("\nPlayer %s has landed on a Trap, you have been moved %d spaces backwards\n",getCurrentPlayer().getName(),River[getCurrentPlayer().getPos()].getEffect());
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            movePlayer(0 - River[getCurrentPlayer().getPos()].getEffect());
        }
    }

    /*
     * Generates random number to be used as dice rolling feature
     */
    public int rollDice(){
        int dice = random.nextInt(5) + 1;
        System.out.printf("\nYou rolled a %d\n",dice);
        return dice;
    }

    public void displayRiver(){
        System.out.printf("\n Turn: %d",turn);
        System.out.printf("\nPlayer 1's position: %d\n",Players[0].getPos()+1);
        System.out.printf("Player 2's position: %d\n",Players[1].getPos()+1);
        System.out.println("====================================================================================================");
        for(int i = 0; i<99;i++){
            if(Players[0].getPos() == i ){
                System.out.print("1");
            }
            else if(Players[1].getPos() == i){
                System.out.print("2");
            }
            else{
                if(River[i].getType() == currentType){
                    System.out.print(currentDisplay);  
                }
                else if(River[i].getType() == trapType){
                    System.out.print(trapDisplay);  
                }
                else{
                    System.out.print(" ");
                }
            }  
        }
        System.out.println("");
        System.out.println("====================================================================================================");
        System.out.printf("\nPlayer %s please press enter to roll the dice.\n",getCurrentPlayer().getName());
    }

    public void displayEndScreen(){
        System.out.println(" ");
        System.out.println("|||||||||||||||||||||||||");
        System.out.println("Thanks for playing!");
        System.out.printf("\nThe winner is %s with a score of: %d!!\n",Players[checkWinner()].getName(),Players[checkWinner()].getScore());
        System.out.println("|||||||||||||||||||||||||");
        System.out.println(" ");

    }
   
    /*
     * Checks if either player has reached the finish line(position 100)
     * Return:
     * -1 if no players have won yet
     * 0 if player 1 has won
     * 1 if player 2 has won
     */
    public int checkWinner(){
        if(Players[0].getPos() == 99){
            return 0;
        }
        else if(Players[1].getPos() == 99){
            return 1;
        }
        else{
            return -1;
        }
    }

    /*
     * Sets all players scores at the end of the game
     * Score is calculated:
     * turn < 10 = 1000pts
     * turn 10 - 20 = 1000pts - (100pts for every 2 turns after the 10th turn)
     * turn 20+ = 100pts
     */
    public void setScores(){ 
        int score;
        int pTurn;
        if(turn % 2 == 0){
            pTurn = turn / 2;
        }
        else{
            pTurn = (turn-1)/ 2;
        } 

        if(pTurn <= 15){
            score = 1000;
        }
        else if(pTurn > 15 && pTurn <25 ){
            score = 500;
        }
        else if(pTurn > 25 && pTurn <50){
            score = 250;
        }
        else{
            score = 100;
        }
        Players[checkWinner()].setScore(score);
    }

    public void checkScores(){
        if(checkWinner() >= 0){
            writeScore(Players[checkWinner()]);
        }
    }

    public void writeScore(player Player){
        try{
            FileWriter fw = new FileWriter(file,true);
            PrintWriter pw = new PrintWriter(fw);

            pw.printf("\n%s  :  %d\n",Player.getName(),Player.getScore());
            pw.close();
        } catch(IOException e){
            System.out.println("Error: writeScore");
        }
    }

}