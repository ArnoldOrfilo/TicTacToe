//You are supposed to add your comments

import java.util.*;

class Point { // class which records the x and y coordinates
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "[" + (x+1) + ", " + (y+1) + "]";
    }
}

class PointsAndScores { // class which records the point and its corresponding score
    int score;
    Point point;

    PointsAndScores(int score, Point point) {
        this.score = score;
        this.point = point;
    }
}

public class Board { // the game field
    List<Point> availablePoints; // this list stores all available points

    char[][] board = new char[5][5]; // the board that actually user and AI making moves. I changed it to char list because I consider it is unnecessary to store 1 and 2 and then convert to x and o


    public Board() { // initialise an empty board

        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                board[i][j]='-';
            }
        }

    }

    public boolean isGameOver() {
        return (hasXWon() || hasOWon() || getAvailablePoints().isEmpty());
    } // identify if the game ends

    public boolean hasXWon() { // identify if AI wins
        // the two diagonal lines
        if ((board[0][0]=='x' && board[1][1]=='x' && board[2][2]=='x' && board[3][3]=='x' && board[4][4]=='x') || ( board[0][4]=='x' && board[1][3]=='x' && board[2][2]=='x' && board[3][1]=='x' && board[4][0]=='x')) {
            return true;
        }
        for (int i = 0; i < 5; ++i) { // the horizontal and vertical lines
            if (((board[i][0]=='x' && board[i][1]=='x' && board[i][2]=='x' && board[i][3]=='x' && board[i][4]=='x')
                    || (board[0][i]=='x' && board[1][i]=='x' &&board[2][i]=='x' &&board[3][i]=='x' &&board[4][i]=='x'))) {
                return true;
            }
        }
        return false;

    }

    public boolean hasOWon() { // identify if user wins
        // the two diagonal lines
        if ((board[0][0]=='o' && board[1][1]=='o' && board[2][2]=='o' && board[3][3]=='o' && board[4][4]=='o') || ( board[0][4]=='o' && board[1][3]=='o' && board[2][2]=='o' && board[3][1]=='o' && board[4][0]=='o')) {
            return true;
        }
        for (int i = 0; i < 5; ++i) { // the horizontal and vertical lines
            if (((board[i][0]=='o' && board[i][1]=='o' && board[i][2]=='o' && board[i][3]=='o' && board[i][4]=='o')
                    || (board[0][i]=='o' &&board[1][i]=='o' &&board[2][i]=='o' &&board[3][i]=='o' &&board[4][i]=='o' ))) {
                return true;
            }
        }
        return false;
    }

    public List<Point> getAvailablePoints() { // get available (unoccupied) points to the list
        availablePoints = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if (board[i][j] == '-') {
                    availablePoints.add(new Point(i, j));
                }
            }
        }
        return availablePoints;
    }


    public void placeAMove(Point point, char player) {
        board[point.x][point.y] = player;   
    } // change the corresponding position in board to 'x' or 'o'


}
