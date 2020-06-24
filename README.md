# ThreeDigits_PuzzleSolver

This a ThreeDigit puzzle Solver that I have developed as per my course assessment for the COMP3308-Intro to Artificial Intelligence unit of study. 

The puzzle works as follows:

Given are two 3-digit numbers called 𝑆 (start) and 𝐺 (goal) and also a set of 3-digit numbers called
𝑓𝑜𝑟𝑏𝑖𝑑𝑑𝑒𝑛. To solve the puzzle, we want to get from 𝑆 to 𝐺 in the smallest number of moves. A move is a
transformation of one number into another number by adding or subtracting 1 to one of its digits. For
example, a move can take you from 123 to 124 by adding 1 to the last digit or from 953 to 853 by subtracting
1 from the first digit. Moves must satisfy the following constraints:
1. You cannot add to the digit 9 or subtract from the digit 0;
2. You cannot make a move that transforms the current number into one of the forbidden numbers;
3. You cannot change the same digit twice in two successive moves.
Note that since the numbers have 3 digits, at the beginning there are at most 6 possible moves from 𝑆. After
the first move, the branching factor is at most 4, due to the constraints on the moves and especially due to
constraint 3.

Numbers starting with 0, e.g. 018, are considered 3-digit numbers.

2. Tasks
A program to find a solution of the puzzle using the following 6 search strategies: BFS, DFS, IDS,
Greedy, A* and Hill-climbing. Use the Manhattan heuristic as a heuristic for A* and Greedy and also
as the evaluation function in Hill-climbing.
The Manhattan heuristic for a move between two numbers A and B is the sum of the absolute
differences of the corresponding digits of these numbers, e.g. ℎ(123, 492) = |1 − 4| + |2 − 9| +
|3 − 2| = 11.
2. Avoid cycles. When selecting a node from the fringe for expansion, if it hasn’t been expanded yet,
expand it, otherwise discard it. Hint: It is not just the three digits that you need to consider when
determining if you have expanded a node before. Two nodes are the same if a) they have the same 3
digits; and b) they have the same ‘child’ nodes.
3. Generate the children in the following order:
a. 1 is subtracted from the first digit
b. 1 is added to the first digit
c. 1 is subtracted from the second digit
d. 1 is added to the second digit
e. 1 is subtracted from the third digit
f. 1 is added to the third digit
Example: the order of the children of node 678 coming from parent 668 is 578, 778, 677, 679. Note
that there are no children for the second digit as it already has been changed in the previous move
(constraint 3).
4. For the heuristic search strategies: if there are nodes with the same priority for expansion, expand
the last added node.
5. Set a limit of 1000 expanded nodes maximum, and when it is reached, stop the search and print a
message that the limit has been reached (see section “Input and Output” for the exact message
required).
