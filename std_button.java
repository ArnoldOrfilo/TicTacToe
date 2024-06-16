import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class std_button extends JButton implements MouseListener {

    public std_button(String name,Color background,int width,int height,int font_size){

        super(name);
        this.setBackground(background);
        this.setFont(new Font("Avenir",Font.PLAIN,font_size));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        this.setPreferredSize(new Dimension(width,height));

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
