public class player{
    private int score;
    private String name;
    private int pos;

    public player(String n){
        name = n;
        score = 0;
        pos = 1;
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
}