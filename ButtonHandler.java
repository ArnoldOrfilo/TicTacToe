import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonHandler implements ActionListener {
    private GameUI game;
    ButtonHandler(GameUI g){ this.game=g;}

    public void actionPerformed(ActionEvent e){

        if (e.getSource()==game.welcome.AI_button || e.getSource()==game.welcome.YOU_button){ // the corresponding action taken if user chooses AI first or user first at the beginning

            if (e.getSource()==game.welcome.AI_button){ // if user chooses AI first

                game.switch_page("Game Play Page"); // switch to gameplay page
                game.player.callMinimax(0,1); // invoke minimax
                game.play.refresh_board(); // refresh displayed board with the one in the board class for displaying

                for (PointsAndScores pas : game.player.rootsChildrenScores) { // this was in the original provided code, I guess this is for evaluation purpose
                    System.out.println("Point: " + pas.point + " Score: " + pas.score);
                }

                System.out.println("\n\n");
            }

            else {
                game.switch_page("Game Play Page"); // if user chooses user first, switch page and wait for user place a movement
                game.play.refresh_board();
            }

        }


        else if (e.getSource()==game.play.play_again){ // if user chooses play again after the game ends
            game.play.restart();
        }

        else if (e.getSource()==game.play.exit){ // if user chooses to exit after the game ends
            System.exit(0);
        }

    }


}
