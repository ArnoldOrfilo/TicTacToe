import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameUI extends JFrame { // This is the display frame of the game. !!! However, the System out content of the score of each possible point will still be in the terminal!!!


    protected AIplayer player; // AI player
    protected CardLayout pages; // used for switch page
    protected JPanel pageFrame; // the panel that contains the card layout
    protected welcome_page welcome; // first page of the game, user chooses if the AI goes first or not
    protected GamePlay_page play; // the page displays the game board and offers entrance for users to place their moves
    protected ButtonHandler handler; // just for code readability
    GameUI(){ // constructor that initialises everything


        player=new AIplayer();
        pages=new CardLayout();
        pageFrame=new JPanel(pages);
        handler=new ButtonHandler(this);
        welcome=new welcome_page(this);
        play=new GamePlay_page(this);

        pageFrame.add(welcome,welcome.name);
        pageFrame.add(play,play.name);

        this.setSize(500,500);
        this.add(pageFrame);
        this.setVisible(true);

    }
    public void switch_page(String name){ this.pages.show(pageFrame,name); } // method switches pages

    public static void main(String[] args) { GameUI game=new GameUI(); } // runs the program


}
