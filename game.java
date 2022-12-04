import java.util.Random; 
import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class game{
    
    private final static int currentType = 1;
    private final static int trapType = 2;
    private final static int riverLength = 100;
    private final static int playerLength = 2;
    private final static String trapDisplay = "T";
    private final static String currentDisplay = "C";
    private final static int maxCurrent = 10;
    private final static int maxTrap = 10;
    private final static int[] milestones = {24,49,74,99};

    private Scanner input = new Scanner(System.in);
    private int turn;
    private cell[] River = new cell[100];
    private player[] Players = new player[playerLength];
    private int[] bets = new int[4];
    private Random random = new Random(); //random object used in rollDice and to generate cell types.
    private File file = new File("highScores.txt");

    public game(String pn1, String pn2){
       turn = 1;
       player p1 = new player(pn1);
       player p2 = new player(pn2);
       Players[0] = p1;
       Players[1] = p2;
       
       for(int i = 0;i<bets.length;i++){
        bets[i] = 20;
       }

       River = createRiver();
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

        int ms = -1;

        for(int i = 0;i<milestones.length;i++){
            if(getCurrentPlayer().getPos() == milestones[i]){
                ms = i;
                break;
            }
        }
        if(ms != -1){
            System.out.println("$$$$$==========================================================$$$$$");
            if(bets[ms] == 0){
                System.out.printf("\nSorry %s you landed on a milestone that has 0 coins on it\n",getCurrentPlayer().getName());
            }
            else{
                System.out.printf("\nCongratulations %s you have landed on a milestone, you've earned %d coins\n\n",getCurrentPlayer().getName(),bets[ms]);
                getCurrentPlayer().setCoins(bets[ms]); 
                System.out.printf("\nYour new total is: %d\n",getCurrentPlayer().getCoins());
                bets[ms] = 0;
            }
            System.out.println("$$$$$==========================================================$$$$$");
        }
    }

    public void checkScores(){
        if(checkWinner() >= 0){
            writeScore(Players[checkWinner()]);
        }
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

    public void displayEndScreen(){
        System.out.println(" ");
        System.out.println("============================================================");
        System.out.println("Thanks for playing!");
        System.out.printf("\n The winner is %s!!\nYou won the game in %d moves with: %d coins\nYour final score is: %d pts\n",Players[checkWinner()].getName()
                                                                                                                                    ,Players[checkWinner()].getMoves()
                                                                                                                                    ,Players[checkWinner()].getCoins()
                                                                                                                                    ,Players[checkWinner()].getScore());
        System.out.println("============================================================");
        System.out.println(" ");

        String score = "";
        try{
            Scanner scan = new Scanner(file);
            System.out.println("^^^=========================^^^");
            System.out.println("        Game High Scores    ");
            int rank = 1;
            for(int i = 0; i < 10;i++){
                if(scan.hasNextLine()){
                    score = scan.nextLine();
                    if(!score.isBlank()){
                        System.out.printf("#%d  %s\n",rank,score);
                        rank++;
                    }
                }
            }
            System.out.println("^^^=========================^^^");
            
            scan.close();
        } catch(FileNotFoundException exception){
            System.out.println("File not found displayHighScore");
        }

    }

    public void displayRiver(){
        for(int i = 0; i < Players.length;i++){
            System.out.printf("------------------------------------\nPlayer %d has %d coins and has made %d moves\n",i+1,Players[i].getCoins(),Players[i].getMoves());
            System.out.printf("\nPlayer %d's position: %d\n---------------------------\n",i+1,Players[i].getPos()+1);
        }
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

    public void nextTurn() {
        turn += 1;
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
        getCurrentPlayer().setMoves();
    }
 
    /*
     * Generates random number to be used as dice rolling feature
     */
    public int rollDice(){
        int dice = random.nextInt(5) + 1;
        System.out.printf("\nYou rolled a %d\n",dice);
        return dice;
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

    public void setBets(){
        for(int i = 0;i<bets.length;i++){
            for(int j = 0; j <Players.length;j++){
                int num = -1;
                Boolean betCheck = true;
                System.out.printf("\nPlayer %s you have %d coins how many would you like to bet on milestone %d\n!You may not bet more than the amount of coins you have!\n",Players[j].getName(),Players[j].getCoins(),i+1);
                while(betCheck){
                    
                    if(num >= 0 && num <= Players[j].getCoins()){
                        betCheck = false;
                        bets[i] += Math.round(num*1.25);
                        Players[j].setCoins(-num);
                        break;
                    }
                    else if(num == -1){
                        try{
                            num = Integer.parseInt(input.nextLine()) ;
                        }
                        catch(NumberFormatException exception){
                            System.out.println("Please enter a NUMBER");
                        }                
                    }
                    else{
                        System.out.println("Please enter a valid amount");
                        try{
                            num = Integer.parseInt(input.nextLine()) ;
                        }
                        catch(NumberFormatException exception){
                            System.out.println("Please enter a NUMBER");
                        }
                    }
                }
            }
        }
        for(int i = 0;i<bets.length;i++){
            System.out.printf("\nMilestone %d has a total bet of %d\n",i+1,bets[i]);
        }
        System.out.println(" ");
        System.out.println("The game will now begin! Have fun!");
    }

    /*
     * Sets all players scores at the end of the game
     * Score is calculated:
     * turn < 10 = 1000pts
     * turn 10 - 20 = 1000pts - (100pts for every 2 turns after the 10th turn)
     * turn 20+ = 100pts
     * The total coins the player ends the game with is also added to their final score
     */
    public void setScores(){ 
        int score;
        int pTurn = Players[checkWinner()].getMoves();

        if(pTurn <= 25){
            score = 1000;
        }
        else if(pTurn > 25 && pTurn <40 ){
            score = 750;
        }
        else if(pTurn > 40 && pTurn <60){
            score = 500;
        }
        else{
            score = 250;
        }
        Players[checkWinner()].setScore(score + Players[checkWinner()].getCoins());
    }

    /*
     * Writes winning players score in the text document highScores.txt
     * Formats to "PlayerName  :  PlayerScore"
     */
    public void writeScore(player Player){


        try{
            FileWriter fw = new FileWriter(file,true);
            PrintWriter pw = new PrintWriter(fw);

            pw.printf("\n%s  :  %d",Player.getName(),Player.getScore());
            pw.close();
        } catch(IOException e){
            System.out.println("File not found writeScore");
        }
    }

    public void getHighScores(){
        
    }
}