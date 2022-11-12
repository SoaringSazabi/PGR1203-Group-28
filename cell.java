public class cell{
    private int type;
    private int effect;

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