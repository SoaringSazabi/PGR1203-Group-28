public class cell{
    private int type;   //0 = blank cell 1 = current 2 = Trap
    private int effect; //Represents how many spaces the player will move if this cell is a trap or current

    public cell(){
        type = 0;
        effect = 0;
    }

    public cell(int t,int e){
        type = t;
        effect = e;
    }
    
    public void setType(int t) {
        type = t;
    }

    public void setEffect(int e){
        effect = e;
    }

    public int getType(){
        return type;
    }

    public int getEffect(){
        return effect;
    }
}