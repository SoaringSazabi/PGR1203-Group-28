import java.util.Scanner;

public class TestGame{
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean main = true; //main menu loop
        boolean gameLoop = false; //game loop
        int menuChoice; // stores main menu choice
        String p1name;
        String p2name;

        while(main){
            System.out.println("WELCOME TO THE RIVER");
            System.out.println("Please select an option: ");
            System.out.println("[1] Start Game");
            System.out.println("[2] View High Scores");
            System.out.println("[3] Exit");
            menuChoice = input.nextInt();

            if(menuChoice == 3){
                main = false;
                System.out.println("Thanks for playing!");
                break;
            }
            else if(menuChoice == 2){
                System.out.println("High Scores");
            }
            else if(menuChoice == 1){
                System.out.println("Please enter player 1's name: ");
                input.nextLine();
                p1name = input.nextLine();
                System.out.println("Please enter player 2's name: ");
                p2name = input.nextLine();
                game game = new game(p1name, p2name);
                gameLoop = true;

                while(gameLoop){
                    game.setTurn(game.getTurn() + 1);
                    game.displayRiver();
                    input.nextLine();
                    game.movePlayer(game.rollDice());
                }
            }
        }
        input.close();
    }
}