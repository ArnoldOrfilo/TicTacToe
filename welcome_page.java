import javax.swing.*;
import java.awt.*;

// Below are just define and initialise GUI
public class welcome_page extends JPanel {

    protected String name="welcome page";
    protected std_label welcome_message;
    protected std_button AI_button;
    protected std_button YOU_button;
    welcome_page(GameUI game){

        this.setBackground(Color.white);
        this.setLayout(new GridBagLayout());
        GridBagConstraints grid=new GridBagConstraints();

        welcome_message=new std_label("Choose who makes first move:",15);
        grid.gridx=0;
        grid.gridy=0;
        grid.insets=new Insets(0,0,20,0);
        this.add(welcome_message,grid);

        AI_button=new std_button("AI first",Color.white,100,45,15);
        AI_button.addActionListener(game.handler);
        grid.gridx=0;
        grid.gridy=1;
        grid.insets=new Insets(0,0,10,0);
        this.add(AI_button,grid);

        YOU_button=new std_button("You first",Color.white,100,45,15);
        YOU_button.addActionListener(game.handler);
        grid.gridx=0;
        grid.gridy=2;
        grid.insets=new Insets(0,0,0,0);
        this.add(YOU_button,grid);


    }
}
