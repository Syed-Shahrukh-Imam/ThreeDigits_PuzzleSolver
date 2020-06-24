import java.util.ArrayList;

public class Node {

    private Node parent;
    private Digit digit;
    private ArrayList<Node> children;
    private int heuristic;
    private int gValue;
    private int fValue;


    Node(String number){
        this.digit = new Digit(number);
        this.children = new ArrayList<Node>();

    }

    void setParent(Node node) {
        parent = node;
    }

    Node getParent(){
        return this.parent;
    }

    public ArrayList<Node> getChildren(){
        return this.children;
    }

    Digit getDigit(){
        return this.digit;
    }

    void setHeuristic(int num){
        this.heuristic = num;
    }
    int getHeuristic(){
        return this.heuristic;
    }

    void setgValue(int num){
        this.gValue = num;
    }

    int getgValue(){
        return this.gValue;
    }

    void setfValue(int num){
        this.fValue = num;
    }
    int getfValue(){
        return this.fValue;
    }

}