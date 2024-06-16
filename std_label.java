import javax.swing.*;
import java.awt.*;

public class std_label extends JLabel {

    public std_label(String name,int Font_size){
        super(name);
        this.setFont(new Font("Avenir",Font.PLAIN,Font_size));
    }

}