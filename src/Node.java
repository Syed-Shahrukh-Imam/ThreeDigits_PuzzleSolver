import java.util.ArrayList;

public class Node {

    Node parent;
    Digit digit;
    ArrayList<Node> children;
    int heuristic;
    int gValue;
    int fValue;

    public Node( String number ){
        this.digit = new Digit(number);
        this.children = new ArrayList<Node>();

    }

    public void setParent(Node node) {
        parent = node;
    }

    public Node getParent(){
        return this.parent;
    }

    public ArrayList<Node> getChildren(){
        return this.children;
    }

    public Digit getDigit(){
        return this.digit;
    }

    public void setHeuristic( int num ){
        this.heuristic = num;
    }
    public int getHeuristic(){
        return this.heuristic;
    }

    public void setgValue(int num){
        this.gValue = num;
    }

    public int getgValue(){
        return this.gValue;
    }

    public void setfValue( int num){
        this.fValue = num;
    }
    public int getfValue(){
        return this.fValue;
    }

}
