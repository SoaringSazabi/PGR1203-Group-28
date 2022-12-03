public class player{
    private int score;
    private String name;
    private int pos;
    private int coins;
    private int moves;

    public player(String n){
        name = n;
        score = 0;
        pos = 0;
        coins = 100;
        moves = 0;
    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    public int getPos(){
        return pos;
    }

    public void setName(String n){
        name = n;
    }

    public void setScore(int s){
        score = s;
    }

    public void setPos(int p){
        pos = p;
    }

    public int getCoins(){
        return coins;
    }

    public void setCoins(int c){
        coins += c;
    }

    public int getMoves(){
        return moves;
    }

    public void setMoves(){
        moves += 1;
    }
}