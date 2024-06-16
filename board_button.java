import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class board_button extends JButton implements MouseListener {

    protected int index_x; // record the index so that when user clicks the program will add move to corresponding position
    protected int index_y; // record the index so that when user clicks the program will add move to corresponding position
    public board_button(String name){

        super(name);
        this.setBackground(Color.white);
        this.setFont(new Font("Avenir",Font.PLAIN,17));
        this.setBorder(BorderFactory.createLineBorder(Color.white,1));
        this.setPreferredSize(new Dimension(30,30));

    }

    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}


}
