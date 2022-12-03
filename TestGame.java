import java.util.Scanner;

public class TestGame{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean main = true; //main menu loop
        boolean start = false;
        boolean menuCheck = true;
        int menuChoice = -1; // stores main menu choice
        String p1name = "";
        String p2name = "";

        while(main){
            System.out.println("WELCOME TO THE RIVER");
            System.out.println("Please select an option: ");
            System.out.println("[1] Start Game");
            System.out.println("[2] View High Scores");
            System.out.println("[3] Exit");
        
            //Loop checks if user input a valid menu choice
            //Will continually ask user to input a valid choice if they have not
            while(menuCheck){
                if(menuChoice <= 3 && menuChoice >= 1){
                    menuCheck = false;
                    break;
                }
                else if(menuChoice == -1){
                    try{
                        menuChoice = Integer.parseInt(input.nextLine()) ;
                    }
                    catch(NumberFormatException exception){
                        System.out.println("Please enter a NUMBER");
                    }                }
                else{
                    System.out.println("Please enter a valid option");
                    try{
                        menuChoice = Integer.parseInt(input.nextLine()) ;
                    }
                    catch(NumberFormatException exception){
                        System.out.println("Please enter a NUMBER");
                    }
                }
            }

            //switch for menu choices
            switch(menuChoice){
                case 3:
                    main = false;
                    System.out.println("Thanks for playing!");
                    break;
                case 2:
                    System.out.println("High Scores");
                    break;
                case 1:
                    start = true;
                    System.out.println("Please enter player 1's name: ");
                    p1name = input.nextLine();
                    System.out.println("Please enter player 2's name: ");
                    p2name = input.nextLine();
                    break;
            }
            
            //Creates and starts game if option 1 was choosen by user
            if(start){
                game game = new game(p1name, p2name);
                while(game.checkWinner() == -1){
                    game.displayRiver();
                    input.nextLine();
                    game.movePlayer(game.rollDice());
                    game.checkCell();
                    game.nextTurn();
                }
                game.setScores();
                game.displayEndScreen();
                game.checkScores();
                start = false;
                menuCheck = true;
                menuChoice = -1;
            }
        }
        input.close();
    }
}