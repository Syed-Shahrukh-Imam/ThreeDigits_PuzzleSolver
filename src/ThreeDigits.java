import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ThreeDigits {

    private Node startNode;
    private Node goalNode;
    private ArrayList<String> forbidden = new ArrayList<String>();
    private Queue<Node> expandedNode = new LinkedList<Node>();
    private ArrayList<String> temporaryNodeHolder = new ArrayList<>();


    private void solutionPath(Node n){
        /*
        Here the function tries to get the solution path.
        Since the Nodes are connected. We take the goal node and
        use the method getParent() until we arrive at the startNode.
        The path is printed to output.
         */
        ArrayList<String> num = new ArrayList<>();
        num.add(n.getDigit().getDigitString());
        Node node = n.getParent();

        while(true){

            if(node == startNode){
                num.add(node.getDigit().getDigitString());
                break;
            }else{
                num.add(node.getDigit().getDigitString());
            }

            node = node.getParent();
        }

        int size = num.size();
        int index = size-1;

        for(int i=0; i<size; i++){
            if(index == 0){
                System.out.println(num.get(index));
                break;
            }
            System.out.print(num.get(index)+",");
            index--;
        }
    }



    private void printExpanded(Queue<Node> expanded){

        /*
        Here we take the expanded nodes and print it.
         */

        ArrayList<String> nodes = new ArrayList<>();

        for(Node s : expanded){
            if(s != null){
                nodes.add(s.getDigit().getDigitString());
            }
        }


        if (forbidden.size() == 0){

            int size = nodes.size();
            int index = 0;

            for(int m=0; m<size; m++){
                if(index == size - 1){

                    System.out.print(nodes.get(index));
                    break;
                }

                System.out.print(nodes.get(index)+",");
                index++;
            }
        }else{

            nodes.removeAll(forbidden);
            int size = nodes.size();
            int index = 0;

            for(int m=0; m<size; m++){
                if(index == size - 1){

                    System.out.print(nodes.get(index));
                    break;
                }

                System.out.print(nodes.get(index)+",");
                index++;
            }
        }
    }


    private ArrayList<String> makeChildren(Node current) {

        /*
        Here we temporarily make the children. No parent-child connections
        are made here. This is a helper function to cycleCheck
         */

        ArrayList<String> current_children = new ArrayList<>();

        if (current != null && current.getDigit().last_changed != 0) {
            //-1 child first digit
            if ((Integer.parseInt(current.getDigit().getFirst_digit()) - 1 >= 0)) {
                String child_node = current.getDigit().decreaseFirstDigit();
                current_children.add(child_node);
            }

            //+1 child first digit
            if ((Integer.parseInt(current.getDigit().getFirst_digit()) + 1 <= 9)) {
                String child_node = current.getDigit().increaseFirstDigit();
                current_children.add(child_node);
            }
        }

        if (current != null && current.getDigit().last_changed != 1) {
            //-1 child
            if ((Integer.parseInt(current.getDigit().getSecond_digit()) - 1 >= 0)) {
                String child_node = current.getDigit().decreaseSecondDigit();
                current_children.add(child_node);
            }

            //+1 child
            if ((Integer.parseInt(current.getDigit().getSecond_digit()) + 1 <= 9)) {
                String child_node = current.getDigit().increaseSecondDigit();
                current_children.add(child_node);
            }
        }

        if (current != null && current.getDigit().last_changed != 2) {
            //-1 child
            if ((Integer.parseInt(current.getDigit().getThird_digit()) - 1 >= 0)) {
                String child_node = current.getDigit().decreaseThirdDigit();
                current_children.add(child_node);
            }
            //+1 child
            if ((Integer.parseInt(current.getDigit().getThird_digit()) + 1 <= 9)) {
                String child_node = current.getDigit().increaseThirdDigit();
                current_children.add(child_node);
            }
        }
        return current_children;
    }


    public boolean cycleCheck( Node current, Queue<Node> expanded){

        /*
        Here I check for cycles.
         */

        if( expanded.size() == 0){
            return false; //Because current is root
        }

        for(Node nodeInExpanded : expanded){

            if(current!=null && current.getDigit().getDigitString().equals(nodeInExpanded.getDigit().getDigitString())){
                //the digits are same so we check children.

                ArrayList<String> currentChildren = makeChildren(current);
                ArrayList<String> childrenOfExpanded = makeChildren(nodeInExpanded);

                int size;

                if(currentChildren.size() > childrenOfExpanded.size() ){
                    size = childrenOfExpanded.size();
                }else if(currentChildren.size() < childrenOfExpanded.size()){
                    size = currentChildren.size();
                }else{
                    size = childrenOfExpanded.size();
                }
                /*
                I use the modulus operator so that I dont get a ArrayIndexOut of Bounds
                 */

                if(  currentChildren.size() == childrenOfExpanded.size()
                        && currentChildren.get(0%size).equals(childrenOfExpanded.get(0%size))
                        && currentChildren.get(1%size).equals(childrenOfExpanded.get(1%size))
                        && currentChildren.get(2%size).equals(childrenOfExpanded.get(2%size))
                        && currentChildren.get(3%size).equals(childrenOfExpanded.get(3%size))
                ){
                    return true;
                }
            }
        }

        return false;
    }

    public void BreadFirstSearch(){


        Queue<Node> expanded = new LinkedList<Node>();
        Queue<Node> fringe = new LinkedList<Node>();


        fringe.add(startNode);
        Node current = null;



        while (expanded.size()<1000){

            //cycle check

            if (fringe.peek() != null &&
                    fringe.peek().getDigit().getDigitString().equals(goalNode.getDigit().getDigitString())) {
                expanded.add(fringe.peek());
                //goal node reached.
                solutionPath(fringe.peek());
                printExpanded(expanded);
               System.exit(0);
            }
            current = fringe.peek();

            boolean b = cycleCheck(current,expanded);

            if(!b){
                expanded.add(fringe.poll());
            }else{
                fringe.remove();
                //break;
            }


            if (!b && current!=null) {


                if (current != null && current.getDigit().last_changed != 0) {
                    //-1 child first digit
                    if ((Integer.parseInt(current.getDigit().getFirst_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseFirstDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(0);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }

                    //+1 child first digit
                    if ((Integer.parseInt(current.getDigit().getFirst_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseFirstDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(0);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }
                }

                //Second Digit
                if (current != null && current.getDigit().last_changed != 1) {

                    //-1 child
                    if ((Integer.parseInt(current.getDigit().getSecond_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseSecondDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(1);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }

                    //+1 child
                    if ((Integer.parseInt(current.getDigit().getSecond_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseSecondDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(1);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }
                }


                if (current != null && current.getDigit().last_changed != 2) {
                    //-1 child
                    if ((Integer.parseInt(current.getDigit().getThird_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseThirdDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(2);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }
                    //+1 child
                    if ((Integer.parseInt(current.getDigit().getThird_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseThirdDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(2);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }
                }

            }
        }
        //While loop ends
        System.out.println("No solution found.");
        printExpanded(expanded);
    }


    private boolean helperDFS(Node current){

        /*
        This function performs the main functionality
        for the DFS.
         */

        if(expandedNode.size() == 999){
            //limit has been reached. jump out of recursion.
            expandedNode.add(current);
            System.out.println("No solution found.");
            printExpanded(expandedNode);
            System.exit(0);
            return false;
        }

        boolean b = cycleCheck(current,expandedNode);

        if(!b){
            expandedNode.add(current);
        }else{
            return false;
        }

        if(current.getDigit().getDigitString().equals(goalNode.getDigit().getDigitString())){
            //goal reached.
            //expandedNode.add(current);
            solutionPath(current);
            printExpanded(expandedNode);
            System.exit(0);
        }

        //Now make the children.

        if(!forbidden.contains(current.getDigit().getDigitString())){

            if(current.getDigit().last_changed != 0){

                if ((Integer.parseInt(current.getDigit().getFirst_digit()) - 1 >= 0)) {
                    Node child_node = new Node(current.getDigit().decreaseFirstDigit());
                    child_node.setParent(current);
                    child_node.getDigit().setLastChanged(0);
                    if(!forbidden.contains(child_node.getDigit().getDigitString())  &&   helperDFS(child_node)){
                        return true;
                    }
                }

                //+1 child first digit
                if ((Integer.parseInt(current.getDigit().getFirst_digit()) + 1 <= 9)) {
                    Node child_node = new Node(current.getDigit().increaseFirstDigit());
                    child_node.setParent(current);
                    child_node.getDigit().setLastChanged(0);
                    if(!forbidden.contains(child_node.getDigit().getDigitString())  &&   helperDFS(child_node)){
                        return true;
                    }
                }
            }

            if(current.getDigit().last_changed != 1){
                if ((Integer.parseInt(current.getDigit().getSecond_digit()) - 1 >= 0)) {
                    Node child_node = new Node(current.getDigit().decreaseSecondDigit());
                    child_node.setParent(current);
                    child_node.getDigit().setLastChanged(1);
                    if(!forbidden.contains(child_node.getDigit().getDigitString())  &&   helperDFS(child_node)){
                        return true;
                    }
                }

                //+1 child
                if ((Integer.parseInt(current.getDigit().getSecond_digit()) + 1 <= 9)) {
                    Node child_node = new Node(current.getDigit().increaseSecondDigit());
                    child_node.setParent(current);
                    child_node.getDigit().setLastChanged(1);
                    if(!forbidden.contains(child_node.getDigit().getDigitString())  &&   helperDFS(child_node)){
                        return true;
                    }
                }
            }

            if(current.getDigit().last_changed != 2){
                if ((Integer.parseInt(current.getDigit().getThird_digit()) - 1 >= 0)) {
                    Node child_node = new Node(current.getDigit().decreaseThirdDigit());
                    child_node.setParent(current);
                    child_node.getDigit().setLastChanged(2);
                    if(!forbidden.contains(child_node.getDigit().getDigitString())  &&   helperDFS(child_node)){
                        return true;
                    }
                }
                //+1 child
                if ((Integer.parseInt(current.getDigit().getThird_digit()) + 1 <= 9)) {
                    Node child_node = new Node(current.getDigit().increaseThirdDigit());
                    child_node.setParent(current);
                    child_node.getDigit().setLastChanged(2);
                    if(!forbidden.contains(child_node.getDigit().getDigitString())  &&   helperDFS(child_node)){
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private void DepthFirstSearch(Node current){

        //need a function that returns. So that some states can cancel out and thus no stackoverflow.
        helperDFS( current );
    }

    public boolean DepthLimitedSearch( Node current, int depthOfTree){

        if(depthOfTree >= 0){
            temporaryNodeHolder.removeAll(forbidden);
            if(temporaryNodeHolder.size() == 1000){
                System.out.println("No solution found.");
                printTemporaryExpanded(temporaryNodeHolder);
                System.exit(0);
                return false;
            }

            //Checking if the current node is a goal or not.
            if(current.getDigit().getDigitString().equals(goalNode.getDigit().getDigitString())){
                expandedNode.add(current);
                temporaryNodeHolder.add(current.getDigit().getDigitString());
                solutionPath(current);
                printTemporaryExpanded(temporaryNodeHolder);
                System.exit(0);
            }

            //Check for the cycle.
            boolean b = cycleCheck(current,expandedNode);

            if(!b){
                expandedNode.add(current);
                temporaryNodeHolder.add(current.getDigit().getDigitString());
            }else{
                return false;
            }

            //Make the children and recur.
            if(!forbidden.contains(current.getDigit().getDigitString())){

                if(current.getDigit().last_changed != 0){

                    if ((Integer.parseInt(current.getDigit().getFirst_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseFirstDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(0);
                        if(DepthLimitedSearch(child_node,depthOfTree-1)){
                            return true;
                        }
                    }

                    //+1 child first digit
                    if ((Integer.parseInt(current.getDigit().getFirst_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseFirstDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(0);
                        if(DepthLimitedSearch(child_node,depthOfTree-1)){
                            return true;
                        }
                    }
                }

                if(current.getDigit().last_changed != 1){

                    if ((Integer.parseInt(current.getDigit().getSecond_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseSecondDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(1);
                        if(DepthLimitedSearch(child_node,depthOfTree-1)){
                            return true;
                        }
                    }

                    //+1 child
                    if ((Integer.parseInt(current.getDigit().getSecond_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseSecondDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(1);
                        if(DepthLimitedSearch(child_node,depthOfTree-1)){
                            return true;
                        }
                    }
                }

                if(current.getDigit().last_changed != 2){
                    if ((Integer.parseInt(current.getDigit().getThird_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseThirdDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(2);
                        if(DepthLimitedSearch(child_node,depthOfTree-1)){
                            return true;
                        }
                    }
                    //+1 child
                    if ((Integer.parseInt(current.getDigit().getThird_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseThirdDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(2);
                        if(DepthLimitedSearch(child_node,depthOfTree-1)){
                            return true;
                        }
                    }
                }
            }

        }

        return false;

    }

    public void printTemporaryExpanded( ArrayList<String> nodes ){

        nodes.removeAll(forbidden);


       int size = nodes.size();
        for(int i=0;i<size;i++){
            if(i==size-1){
                System.out.print(nodes.get(i));
            }else{
                System.out.print(nodes.get(i)+",");
            }
        }
    }


    public void IDS(Node current){

        double infinity = Double.POSITIVE_INFINITY;


        for(int depth=0; depth<infinity; depth++){
            DepthLimitedSearch(current, depth );

            expandedNode.clear();
        }
    }

    private void heuristicSetter(Node node){
        //sets the heuristic of the node passed.
        int goalNodeFirstDigit = Integer.parseInt(goalNode.getDigit().getFirst_digit());
        int goalNodeSecondDigit = Integer.parseInt(goalNode.getDigit().getSecond_digit());
        int goalNodeThirdDigit = Integer.parseInt(goalNode.getDigit().getThird_digit());

        int nodeFirstDigit = Integer.parseInt(node.getDigit().getFirst_digit());
        int nodeSecondDigit = Integer.parseInt(node.getDigit().getSecond_digit());
        int nodeThirdDigit = Integer.parseInt(node.getDigit().getThird_digit());

        int heuristic = Math.abs(goalNodeFirstDigit - nodeFirstDigit)+
                        Math.abs(goalNodeSecondDigit - nodeSecondDigit)+
                        Math.abs(goalNodeThirdDigit - nodeThirdDigit);

        node.setHeuristic(heuristic);
    }

    private void GreedySearch(){

        /*
        The greedy algorithm selects the best out of the children.
        There should be a loop.
        During each iteration, take the node make its children.
        Perfrom goal check, cycle check
         */

        Queue<Node> expanded = new LinkedList<>();
        ArrayList<Node> fringe = new ArrayList<>();
        ArrayList<Node> fringeTemp = new ArrayList<>();


        Node current = startNode;

        while(expanded.size() < 1000){

            if(current.getDigit().getDigitString().equals(goalNode.getDigit().getDigitString())){
                //goal is reached.
                solutionPath(current);
                expanded.add(current);
                printExpanded(expanded);
                System.exit(0);
            }


            boolean b = cycleCheck(current,expanded);

            if(!b) {
                expanded.add(current);
            }

            if(!b){

                if(current.getDigit().last_changed != 0){

                    if ((Integer.parseInt(current.getDigit().getFirst_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseFirstDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(0);

                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }

                    //+1 child first digit
                    if ((Integer.parseInt(current.getDigit().getFirst_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseFirstDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(0);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }

                    }
                }

                if(current.getDigit().last_changed != 1){

                    if ((Integer.parseInt(current.getDigit().getSecond_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseSecondDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(1);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }

                    //+1 child
                    if ((Integer.parseInt(current.getDigit().getSecond_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseSecondDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(1);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }
                }

                if(current.getDigit().last_changed != 2){
                    if ((Integer.parseInt(current.getDigit().getThird_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseThirdDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(2);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }
                    //+1 child
                    if ((Integer.parseInt(current.getDigit().getThird_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseThirdDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(2);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }
                }

            }



            for(Node n : fringe){
                heuristicSetter(n);

            }

            fringeTemp.addAll(fringe);
            //now all the nodes in fringe have the heuristic value.
            //We can get the last added minm
            Node minm;
            if(fringeTemp.size() != 0){
                minm = fringeTemp.get(0);
            }else{
                break;
            }
            for(int i = 1; i<fringeTemp.size(); i++){
                if(fringeTemp.get(i).getHeuristic() <= minm.getHeuristic()){
                    minm = fringeTemp.get(i);
                }
            }

            //now we have the minm for the next stage.
            current = minm;
            fringeTemp.remove(minm);
            fringe.clear();
        }

        //While loop ends
        System.out.println("No solution found.");
        printExpanded(expanded);



    }

    private void hillClimbing(){

        Queue<Node> expanded = new LinkedList<>();
        ArrayList<Node> fringe = new ArrayList<>();
        ArrayList<Node> fringeTemp = new ArrayList<>();

        Node current = startNode;
        heuristicSetter(current);

        while( expandedNode.size() < 1000){

            //goal check

            if(current!= null && current.getDigit().getDigitString().equals(goalNode.getDigit().getDigitString())){
                solutionPath(current);
                expanded.add(current);
                printExpanded(expanded);
                System.exit(0);
            }

            boolean b = cycleCheck(current,expanded);

            if(!b) {
                expanded.add(current);
            }

            if(!b){

                if (current != null && current.getDigit().last_changed != 0) {

                    if ((Integer.parseInt(current.getDigit().getFirst_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseFirstDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(0);

                        if (!forbidden.contains(child_node.getDigit().getDigitString())) {
                            fringe.add(child_node);
                        }
                    }

                    //+1 child first digit
                    if ((Integer.parseInt(current.getDigit().getFirst_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseFirstDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(0);
                        if (!forbidden.contains(child_node.getDigit().getDigitString())) {
                            fringe.add(child_node);
                        }

                    }
                }

                if (current != null && current.getDigit().last_changed != 1) {

                    if ((Integer.parseInt(current.getDigit().getSecond_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseSecondDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(1);
                        if (!forbidden.contains(child_node.getDigit().getDigitString())) {
                            fringe.add(child_node);
                        }
                    }

                    //+1 child
                    if ((Integer.parseInt(current.getDigit().getSecond_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseSecondDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(1);
                        if (!forbidden.contains(child_node.getDigit().getDigitString())) {
                            fringe.add(child_node);
                        }
                    }
                }

                if (current != null && current.getDigit().last_changed != 2) {
                    if ((Integer.parseInt(current.getDigit().getThird_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseThirdDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(2);
                        if (!forbidden.contains(child_node.getDigit().getDigitString())) {
                            fringe.add(child_node);
                        }
                    }
                    //+1 child
                    if ((Integer.parseInt(current.getDigit().getThird_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseThirdDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(2);
                        if (!forbidden.contains(child_node.getDigit().getDigitString())) {
                            fringe.add(child_node);
                        }
                    }
                }
            }

            for(Node n: fringe){

                heuristicSetter(n);
            }
            //what if all the heristic are same?
            boolean allHeuristicSame = false;
            int size = fringe.size();


            if(fringe.size() != 0 && current != null
                    && current.getHeuristic() <= fringe.get(0 % size).getHeuristic()
                    && current.getHeuristic() <= fringe.get(1 % size).getHeuristic()
                    && current.getHeuristic() <= fringe.get(2 % size).getHeuristic()
                    && current.getHeuristic() <= fringe.get(3 % size).getHeuristic()
                    && current.getHeuristic() <= fringe.get(4 % size).getHeuristic()
                    && current.getHeuristic() <= fringe.get(5 % size).getHeuristic()){

                allHeuristicSame = true;

            }


           if(allHeuristicSame){
               System.out.println("No solution found.");
               //expanded.add(current);
               printExpanded(expanded);
               System.exit(0);
           }

            /*
            In hill climbing we do a local greedy search
            by only selecting the bestest soln.
             */
            Node lastAdded = null;
            if(fringe.size() != 0 ){
                lastAdded = fringe.get(0);
            }

            for(int i = 1; i<fringe.size(); i++){
                if(fringe.get(i).getHeuristic() <= lastAdded.getHeuristic()){
                    lastAdded = fringe.get(i);
                }
            }

            current = lastAdded;
            fringe.clear();
        }


        //While loop ends
        System.out.println("No solution found.");
        printExpanded(expanded);
    }


    private void gSetter(Node n){


        if(n != null){

            Node node = n.getParent();
            int counter = 0;

            while(true){

                if(node == startNode){
                    counter += 1;
                    break;
                }else{
                    counter += 1;
                }
                node = node.getParent();
            }

            //counter is the g value;

            n.setgValue(counter);
        }

    }

    private void fSetter(Node node){

        int hValue = node.getHeuristic();
        int gValue = node.getgValue();

        int fValue = gValue+hValue;

        node.setfValue(fValue);
    }




    private void AStar(){

        Queue<Node> expanded = new LinkedList<>();
        ArrayList<Node> fringe = new ArrayList<>();
        ArrayList<Node> fringeTemp = new ArrayList<>();


        Node current = startNode;

        while(expanded.size() < 1000){

            if(current.getDigit().getDigitString().equals(goalNode.getDigit().getDigitString())){
                //goal is reached.
                solutionPath(current);
                expanded.add(current);
                printExpanded(expanded);
                System.exit(0);
            }


            boolean b = cycleCheck(current,expanded);

            if(!b) {
                expanded.add(current);
            }

            if(!b){

                if(current.getDigit().last_changed != 0){

                    if ((Integer.parseInt(current.getDigit().getFirst_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseFirstDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(0);

                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }

                    //+1 child first digit
                    if ((Integer.parseInt(current.getDigit().getFirst_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseFirstDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(0);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }

                    }
                }

                if(current.getDigit().last_changed != 1){

                    if ((Integer.parseInt(current.getDigit().getSecond_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseSecondDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(1);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }

                    //+1 child
                    if ((Integer.parseInt(current.getDigit().getSecond_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseSecondDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(1);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }
                }

                if(current.getDigit().last_changed != 2){
                    if ((Integer.parseInt(current.getDigit().getThird_digit()) - 1 >= 0)) {
                        Node child_node = new Node(current.getDigit().decreaseThirdDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(2);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }
                    //+1 child
                    if ((Integer.parseInt(current.getDigit().getThird_digit()) + 1 <= 9)) {
                        Node child_node = new Node(current.getDigit().increaseThirdDigit());
                        child_node.setParent(current);
                        child_node.getDigit().setLastChanged(2);
                        if(!forbidden.contains(child_node.getDigit().getDigitString())){
                            fringe.add(child_node);
                        }
                    }
                }

            }


            for(Node n : fringe){
                heuristicSetter(n);
            }

            for(Node n : fringe){
                gSetter(n);
            }

            for(Node n : fringe){
                fSetter(n);
            }


            fringeTemp.addAll(fringe);
            //now all the nodes in fringe have the heuristic value.
            //We can get the last added minm
            Node minm;
            if(fringeTemp.size() != 0){
                minm = fringeTemp.get(0);
            }else{
                System.out.println("FringeTemp is size zero");
                break;
            }


            for(int i = 1; i<fringeTemp.size(); i++){
                if(fringeTemp.get(i).getfValue() <= minm.getfValue()){
                    minm = fringeTemp.get(i);
                }
            }

            //now we have the minm for the next stage.
            current = minm;
            fringeTemp.remove(minm);
            fringe.clear();
        }

        //While loop ends
        System.out.println("No solution found.");
        printExpanded(expanded);

    }



    public void performSearch( String strategy){
        switch (strategy) {
            case "B":
                BreadFirstSearch();
                break;
            case "D":
                DepthFirstSearch(startNode);
                break;
            case "I":
                IDS(startNode);
                break;
            case "G":
                GreedySearch();
                break;

            case "H" :
                hillClimbing();
                break;

            case "A":
                AStar();
                break;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        //read the file and take information about start,goal and forbidden
        ThreeDigits threeDigits = new ThreeDigits();
        String filename = args[1];

        File myFile = new File(filename);
        Scanner reader = new Scanner(myFile);

        threeDigits.startNode = new Node(reader.nextLine());
        threeDigits.goalNode = new Node(reader.nextLine());

        if (reader.hasNextLine()) {

            String num = reader.nextLine();
            threeDigits.forbidden.addAll(Arrays.asList(num.split(",")));

        }

        if(threeDigits.startNode.getDigit().getDigitString().equals(threeDigits.goalNode.getDigit().getDigitString())){
            System.out.println("No solution found");
            System.out.print(threeDigits.startNode.getDigit().getDigitString());
            System.exit(0);
        }

        ArrayList<String> childrenOfStart = new ArrayList<>();
        childrenOfStart = threeDigits.makeChildren(threeDigits.startNode);

        childrenOfStart.removeAll(threeDigits.forbidden);

        if(childrenOfStart.size() == 0){
            System.out.println("No solution found.");
            System.out.println(threeDigits.startNode.getDigit().getDigitString());
            System.exit(0);
        }else{
            threeDigits.performSearch(args[0]);
        }


    }
}