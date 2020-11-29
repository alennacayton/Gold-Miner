import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class GameFrame extends JFrame {

    GameFrame()
    {
        /*
        BufferedImage img = ImageIO.read(new URL("apple.jpg"));
        ImageIcon icon = new ImageIcon(img);
        JLabel label = new JLabel(icon);
*/

        this.add(new GamePanel());
        this.setTitle("Gold Miner");
       // this.add(label);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
