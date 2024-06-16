import javax.swing.*;
import java.awt.*;


public class GamePlay_page extends JPanel {
    protected String name="Game Play Page";
    protected GameUI game;

    // Display the board by buttons
    protected board_button [][]GUI_board; // this stores the buttons that form the board, so that user can directly place move by clicking

    protected std_label title;
    protected std_label result_statement;
    protected std_button play_again;
    protected std_button exit;

    GamePlay_page(GameUI g){


        this.game=g;
        this.setBackground(Color.white);
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        GUI_board=new board_button[5][5];


        JPanel part1=new JPanel();
        part1.setBackground(Color.white);
        part1.setLayout(new GridBagLayout());

        JPanel part2=new JPanel();
        part2.setBackground(Color.white);
        part2.setLayout(new GridBagLayout());

        GridBagConstraints grid=new GridBagConstraints();


        // initialise 25 buttons and add them to both button 2d array and jpanel
        for (int i=0;i<5;i++){

            for (int j=0;j<5;j++){
                board_button temp=new board_button("");
                temp.index_x=i;
                temp.index_y=j;

                temp.addActionListener(e->this.place_by_button(temp.index_x, temp.index_y)); // bond placing method to each button
                grid.gridx=j;
                grid.gridy=i;
                grid.insets=new Insets(5,5,5,5);
                part1.add(temp,grid);
                this.GUI_board[i][j]=temp;
            }

        }

        title=new std_label("Place your move by clicking",15);
        grid.gridx=0;
        grid.gridy=0;
        grid.insets=new Insets(0,0,0,0);
        part2.add(title,grid);


        // the content below only displayed after the game ends
        play_again=new std_button("Try again",Color.white,100,35,15);
        play_again.addActionListener(game.handler);
        grid.gridx=0;
        grid.gridy=1;
        grid.insets=new Insets(0,0,0,20);
        part2.add(play_again,grid);

        exit=new std_button("Exit",Color.white,100,35,15);
        exit.addActionListener(game.handler);
        grid.gridx=1;
        grid.gridy=1;
        grid.insets=new Insets(0,0,0,0);
        part2.add(exit,grid);

        play_again.setVisible(false);
        exit.setVisible(false);


        part1.setPreferredSize(new Dimension(Integer.MAX_VALUE,300));
        part1.setMaximumSize(part1.getPreferredSize());
        part2.setMaximumSize(new Dimension(Integer.MAX_VALUE,100));

        result_statement=new std_label("",15);


        this.add(part1);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(part2);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(result_statement);


    }

    public void place_by_button(int x,int y){

        if (game.player.b.isGameOver()){
            JOptionPane.showMessageDialog(null,"This game has ended!",null,JOptionPane.ERROR_MESSAGE);
            return;
        }


        if (game.player.b.board[x][y]!='-'){ // check if the place is empty
            JOptionPane.showMessageDialog(null,"This point has been occupied!",null,JOptionPane.ERROR_MESSAGE);
            return;
        }
        game.player.b.placeAMove(new Point(x,y),'o'); // place the move if empty


        if (game.player.b.isGameOver()){ // check end game condition and execute corresponding actions
            if (game.player.b.hasOWon()){
                this.refresh_board();
                game.play.result_statement.setText("You won!");
            }
            else { //it's impossible that the AI wins here as this is right after user placing a move
                this.refresh_board();
                game.play.result_statement.setText("Its a draw!");
            }
            game.play.end_state();
            return;
        }

        game.player.callMinimax(0,1);
        this.refresh_board();

        for (PointsAndScores pas : game.player.rootsChildrenScores) { // this was in the original provided code, I guess this is for evaluation purpose
            System.out.println("Point: " + pas.point + " Score: " + pas.score);
        }
        System.out.println("\n\n");

        if (game.player.b.isGameOver()){ // check end game condition and execute corresponding actions
            if (game.player.b.hasXWon()){
                game.play.result_statement.setText("You lost!");
            }
            else { // right here there's impossible that the User can win as this is right after AI placing a move
                game.play.result_statement.setText("Its a draw!");
            }
            game.play.end_state();
        }

    }
    public void refresh_board(){ // refresh the displayed board according the board to display in the board class

        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                GUI_board[i][j].setText(String.valueOf(game.player.b.board[i][j]));
            }
        }

    }
    public void end_state(){ // shows the result of game and ask users if they want another round or exit

        this.title.setVisible(false);
        this.play_again.setVisible(true);
        this.exit.setVisible(true);

    }

    public void restart(){ // if user chooses to have another round, reset everything and switch to main page

        this.play_again.setVisible(false);
        this.exit.setVisible(false);
        this.title.setVisible(true);

        this.result_statement.setText("");
        game.player=new AIplayer();
        this.refresh_board();
        game.switch_page("welcome page");
    }



}
