import javax.swing.*;
import java.util.*;

public class AIplayer {

    List<PointsAndScores> rootsChildrenScores; // stores every possible point and their corresponding score after all the recursions
    Board b; // game board


    public AIplayer() {
        b=new Board(); // initialise board
    }

    public Point returnBestMove() { // chooses the point with the highest score from the list

        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScores.size(); ++i) {
            if (MAX < rootsChildrenScores.get(i).score) {
                MAX = rootsChildrenScores.get(i).score;
                best = i; // records the index of point that having the highest score at this moment
            }
        }

        return rootsChildrenScores.get(best).point; // returns the corresponding point
    }

    public void callMinimax(int depth, int turn){ // invoke minimax method

        rootsChildrenScores = new ArrayList<>(); // Renew the list, as it's a new turn
        minimax(depth, turn, b,Integer.MIN_VALUE,Integer.MAX_VALUE); // call minimax
        b.placeAMove(this.returnBestMove(),'x'); // place move at the position (point) that having the highest score

    }

    public int[] evaluate_rows(int attack_index, int defense_index){ // evaluate every row by attack index and defense index depends on different game stages and sum the scores

        int score=0; // initialise score
        int thread=0; // initialise thread (a thread is 4 'o' in a line and there's no 'x', to prevent double threads occur in the game)
        int xthread=0; // initialise xthread (a xthread is 4 'x' in a line and there's no 'o', to encourage double threads of 'x' occur in the game)

        for (char[] i: b.board){ // find each row

            int x_counter=0; // initialise and record the occurrence of x
            int o_counter=0; // initialise and record the occurrence of o

            for (char j : i ){ // traverse through the row

                if (j=='x'){
                    x_counter++;
                }
                else if (j=='o'){
                    o_counter++;
                }

            }

            score+=this.score_manager(x_counter, o_counter,attack_index, defense_index); //score this specific row and add it to the sum

            if (o_counter==4 && x_counter==0){ // detect threads
                thread++;
            }
            if (x_counter==4 && o_counter==0){// detect xthreads
                xthread++;
            }

            // goes to the next row

        }
        int [] result=new int[3]; // put outputs inside and return
        result[0]=score;
        result[1]=thread;
        result[2]=xthread;

        return result;
    }

    public int[] evaluate_columns(int attack_index, int defense_index){ // evaluate every column by attack index and defense index depends on different game stages and sum the scores

        int score=0; // initialise score
        int thread=0; // initialise thread (a thread is 4 'o' in a line and there's no 'x', to prevent double threads occur in the game)
        int xthread=0; // initialise xthread (a xthread is 4 'x' in a line and there's no 'o', to encourage double threads of 'x' occur in the game)

        for (int i=0;i<5;i++){ // find each column

            int x_counter=0; // initialise and record the occurrence of x
            int o_counter=0; // initialise and record the occurrence of o


            for (char[]j : b.board){ // traverse through each column

                if (j[i]=='x'){
                    x_counter++;

                }
                else if (j[i]=='o'){
                    o_counter++;
                }

            }

            score+=this.score_manager(x_counter,o_counter,attack_index,defense_index); //score this specific column and add it to the sum

            if (o_counter==4&&x_counter==0){ // detect threads
                thread++;
            }
            if (x_counter==4&&o_counter==0){ // detect xthreads
                xthread++;
            }

            // goes to the next column

        }

        int [] result=new int[3];
        result[0]=score;
        result[1]=thread;
        result[2]=xthread;
        return result; // put outputs inside and return
    }

    public int[] evaluate_diagonals(int attack_index, int defense_index){ // evaluate every diagonal by attack index and defense index depends on different game stages and sum the scores

        // initialise everything like the above methods
        int score=0;
        int index=0; // the index of each row in order to form a diagonal
        int x_counter=0;
        int o_counter=0;
        int thread=0;
        int xthread=0;


        for (char[]i:b.board){ // traverse through the first diagonal (from [0][0] to [4][4])

            if (i[index]=='x'){
                x_counter++;
            }
            else if (i[index]=='o'){
                o_counter++;
            }

            index++;
        }

        score+=score_manager(x_counter,o_counter,attack_index,defense_index); // score this diagonal and add to sum

        if (o_counter==4 && x_counter==0){ // detect thread
            thread++;
        }
        if (x_counter==4 && o_counter==0){// detect xthread
            xthread++;
        }


        // re-initialise the counters and index
        x_counter=0;
        o_counter=0;
        index=4;

        for (char[]i:b.board){  // traverse through the second diagonal (from [0][4] to [4][0])

            if (i[index]=='x'){
                x_counter++;
            }
            else if (i[index]=='o'){
                o_counter++;
            }

            index--;
        }
        score+=score_manager(x_counter,o_counter,attack_index,defense_index); // score this diagonal and add to sum

        if (o_counter==4 && x_counter==0){ // detect thread
            thread++;
        }
        if (x_counter==4 && o_counter==0){ // detect xthread
            xthread++;
        }

        int [] result=new int[3];
        result[0]=score;
        result[1]=thread;
        result[2]=xthread;

        return result;

    }

    public int score_manager (int x_counter, int o_counter, int attack_index, int defense_index){ // evaluate criterion of each line (row, column or diagonal)

        int score=0; // initialise score

        if (o_counter == 0 && x_counter == 1) score+=2*attack_index; // encourage AI to explore new lines
        if (x_counter == 0 && o_counter == 1) score-=2*defense_index; // avoid the game states that user explores some lines first

        if (o_counter == 0 && x_counter > 1) score += attack_index * x_counter; // add score according to number of x when still have win chance on that line
        if (x_counter == 0 && o_counter > 0) score -= defense_index * o_counter; // deduct score according to number of o and o still has win chance on that line

        //if (x_counter == 3 && o_counter == 1) score -= defense_index/2; // if a 3x in a line chance been destroyed //test
        //if (x_counter == 4 && o_counter == 1) score -= defense_index; // if a 4x in a line chance been destroyed //test
        //** the above code sometimes misleads the AI to play too defensively and misses some of the possible win chance, so I decided to remove it

        if (x_counter==1 && o_counter>=3) score+=defense_index * o_counter; // encourage AI to defense if user has 3 or more in a line happens

        return score;

    }



    public int evaluator(int attack_index, int defense_index){

        int score=0;
        int thread=0;
        int xthread=0;

        int[] temp;
        // codes below invoke the methods to evaluate the entire game state
        temp=evaluate_rows(attack_index,defense_index);
        score+=temp[0];
        thread+=temp[1];
        xthread+=temp[2];

        temp=evaluate_columns(attack_index,defense_index);
        score+=temp[0];
        thread+=temp[1];
        xthread+=temp[2];

        temp=evaluate_diagonals(attack_index,defense_index);
        score+=temp[0];
        thread+=temp[1];
        xthread+=temp[2];

        if (thread>=2) score-=1000;// detect total threads of this game state
        if (xthread>=2) score+=1200;// detect total threads of this game state

        return score;

    }

    public int heuristic (Board b, int stage){


        int score=0;


        // in minimax function, I invoke this method with the game stage. 0 means early stage, 1 means mid stage, 2 means end stage

        if (stage==0){ // in early stage

            int attack_index=6; // move aggressively, but still prevent user from getting better positions
            int defense_index=3;

            score=evaluator(attack_index,defense_index);

            if (b.board[2][2]=='x'){ // if AI takes the central point
                score+=2*attack_index;
            }

            if (b.board[2][2]=='o'){ // if User takes the central point
                score-=2*defense_index;
            }



            return score; // The minimax search stops when total amount of moves is larger than 6 at this stage, so there's no need to evaluate more

        }

        else if (stage==1){ // in mid stage, the longest stage

            int attack_index=6; // move aggressively
            int defense_index=2; // focus on attacking
            // at this stage, encourage AI to attack and move aggressively.

            score=evaluator(attack_index,defense_index);

            return score; // return the evalutation of this game state
        }

        else { // in ending stage

            int attack_index=6; // at this stage have to find the chance to win, ignore the user 3 in a line, because if user is going to win will deduct integer min_value anyway
            int defense_index=1;
            

            score=evaluator(attack_index,defense_index);


            return score;
        }

    }

    public int get_amount(){ // get the amount of moves made in total of both sides

        int counter=0;

        for (char[]i:b.board){
            for (char j:i){
                if (j!='-'){
                    counter++;
                }
            }
        }

        return counter;

    }

    public int minimax(int depth, int turn, Board b, int alpha, int beta) {

        if (b.hasXWon()&&depth==1) return Integer.MAX_VALUE; // if AI wins in the next move
        if (b.hasOWon()&&depth==2) return Integer.MIN_VALUE; // if User wins in the next move
        if (b.hasXWon()) return 12000; // if AI wins in simulation, means current point leads to a win
        if (b.hasOWon()) return -10000; // end state, user wins in simulation

        if (b.getAvailablePoints().isEmpty()) return 0; // Draw


        if (get_amount()<=6 && depth==6){ // which indicates this is early stage of the game, no need to get that deep
            return heuristic(b,0); // 0 indicates early stage
        }

        if ((get_amount()>6 && get_amount()<=18) && depth==6) { // which indicates this is mid stage of the game
            return heuristic(b,1); // return the evaluation of current state if reaches depth 6
        }

        if (get_amount()>18 && depth == 6) { // which indicates this is end stage of the game
            return heuristic(b,2); // return the evaluation of current state if reaches depth 6
        }


        List<Point> pointsAvailable = b.getAvailablePoints(); // get current layer available positions
        Collections.shuffle(pointsAvailable); // For a few turns, all the position scored the same, this will make the AI moves more unpredictable


        if (turn == 1) {  // AI's turn (Maximizer)

            for (Point point : pointsAvailable) {


                b.placeAMove(point, 'x'); // simulate move

                int currentScore = minimax(depth + 1, 2, b, alpha, beta); // continue simulation

                b.placeAMove(point, '-'); // undo simulate move

                alpha = Math.max(alpha,  currentScore); // update alpha

                if (depth==0){ // when the above recursion ends, add the current point score to the list
                    rootsChildrenScores.add(new PointsAndScores(currentScore,point));
                }

                if (alpha >= beta) {
                    break;
                } // Alpha-Beta Pruning
            }

            return alpha; // return max score
        }

        else { // User's turn (Minimizer)

            for (Point point : pointsAvailable) {


                b.placeAMove(point, 'o'); // simulate move

                int currentScore = minimax(depth + 1, 1, b, alpha, beta); // continue simulation
                b.placeAMove(point, '-'); // undo move

                beta = Math.min(beta, currentScore);

                if (alpha >= beta) {
                    break;
                }


            }
            return beta; // back up minimum value
        }
    }
}
